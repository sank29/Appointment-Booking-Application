package com.sanket.service;

import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.EmailBody;
import com.sanket.entity.Patient;
import com.sanket.entity.Review;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.ReviewException;
import com.sanket.exception.TimeDateException;
import com.sanket.repository.SessionDao;

import jakarta.mail.MessagingException;

import com.sanket.repository.AppointmentDao;
import com.sanket.repository.DoctorDao;
import com.sanket.repository.PatientDao;
import com.sanket.repository.ReviewDao;

@Service
public class PatientServiceImpl implements PatientService, Runnable {
	
	public static Map<String, LocalDateTime> myTimeDate = new LinkedHashMap<>();
	
	public static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
	
	@Autowired
	PatientDao userDao;
	
	@Autowired
	SessionDao sessionDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	AppointmentDao appointmentDao;
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	Appointment savedAppointment;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	EmailBody emailBody;
	
	@Autowired
	ReviewDao reviewDao;
	
	public PatientServiceImpl(Appointment appointment, EmailSenderService emailSenderService, EmailBody emailBody) {
		
		this.savedAppointment = appointment;
		this.emailSenderService = emailSenderService;
		this.emailBody = emailBody;
	
	}

	@Override
	public Patient createPatient(Patient patient) throws PatientException {
		
		Patient databaseUser = userDao.findByMobileNo(patient.getMobileNo()); 
		
		if(databaseUser == null) { 
			
			// setting type patient because we have to check this is patient or doctor
			
			patient.setType("Patient");
			
			// encoding password
			
			patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
			
			userDao.save(patient);
			
			return patient; 
			
		
		}else {	
			
			throw new PatientException("Patient already register with this mobile no " + patient.getMobileNo());
			
		}
	}

	@Override
	public Patient updatePatient(Patient user, String key) throws PatientException {
		

		CurrentSession loggedInUser = sessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			
			throw new PatientException("Please provide the valid key to update the user");
		}
		
		if(user.getPatientId() == loggedInUser.getUserId()) {
			
			return userDao.save(user);
	
		}else {
			throw new PatientException("Invalid user details. Login first");
		}
	}

	@Override
	public Patient getPatientByUuid(String uuid) throws PatientException {
		
		CurrentSession currentPatient = sessionDao.findByUuid(uuid);
		
		Optional<Patient> patient = userDao.findById(currentPatient.getUserId());
		
		if(patient.isPresent()) {
			
			return patient.get();
		
		}else {
			
			throw new PatientException("Patient or Admin not present by this uuid " + uuid);
		}
	}

	@Override
	public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException {
		
		CurrentSession currentUserSession = sessionDao.findByUuid(uuid);
		
		if(currentUserSession != null) {
			
			return currentUserSession;
			
		}else {
			
			throw new LoginException("Please enter valid key");
		}
	}
	
	// we are refreshing the appointment dates when client is fetching the appointment or client clicking on refresh button
	// from and to will be 24 hours time
	
	public static void getAppointmentDates(Integer from, Integer to) throws IOException, TimeDateException{
		
		// empty the myTimeDate firstly before putting the new values
		
		myTimeDate.clear();
		
		// checking from and to is null or not
		
		if(from == null || to == null) {
			 
			throw new TimeDateException("Please enter valid doctor appointment From to To time");
		}
		
		FileReader reader = new FileReader("config.properties");  
	      
	    Properties p = new Properties();  
	    
	    p.load(reader); 
	    
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    
	    LocalDateTime tomorrowDateTime =  currentDateTime.plusDays(1);
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    
	    // puting todays dates
	    
	    for(int i= from; i <= to; i++) {
	    	
	    	String TodaytimeString = null;
	    	
	    	if(!( i >= 10)) {
	    		
	    		TodaytimeString = currentDateTime.toLocalDate() + " 0" + i + ":00";
	    		
	    	   
	    	}else {
	    		
	    		TodaytimeString = currentDateTime.toLocalDate() + " " + i + ":00";
	    		
	    	}
	    	
	    	LocalDateTime dateTime = LocalDateTime.parse(TodaytimeString, formatter);
	    	
	    	// we are checking if time is gone or not if time is gone then don't put in database
	    	
	    	// 2023-03-09 01:00
	    	
	    	if(currentDateTime.isBefore(dateTime)) {
	    		
	    		myTimeDate.put("today"+i, dateTime);
	    		
	    	}
	    	
	    }
	    
	    // puting tomorrow dates
	    
	    for(int i= from; i <= to; i++) {
	    	
	    	String tomorrowTimeString = null;
	    	
	    	if(!( i >= 10)) {
	    		
	    	   tomorrowTimeString = tomorrowDateTime.toLocalDate() + " 0" + i + ":00";
	    	   
	    	}else {
	    		
	    		tomorrowTimeString = tomorrowDateTime.toLocalDate() + " " + i + ":00";
	    		
	    	}
	    	
	    	LocalDateTime dateTime = LocalDateTime.parse(tomorrowTimeString, formatter);
	    	
	    	// we are checking if time is gone or not if time is gone then don't put in database
	    	if(currentDateTime.isBefore(dateTime)) {
	    		
	    		myTimeDate.put("tomorrow"+i, dateTime);
	    		
	    	}
	    	
	    }
	    
	    
	    
	}

	@Override
	public Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, DoctorException, IOException, TimeDateException, MessagingException {
		
		CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> patient = patientDao.findById(currentPatientSession.getUserId());
		
		
		
		if(patient.isPresent()) {
			
			// setting patient in appointment
			appointment.setPatient(patient.get());

			Doctor doctor = appointment.getDoctor();

			Optional<Doctor> registerDoctors = doctorDao.findById(doctor.getDoctorId()); 

			if(!registerDoctors.isEmpty()) {
				
				// setting doctor in appointment
				appointment.setDoctor(registerDoctors.get());
				
				// check if appointment date and time is available or not
				// this line generating time dynamically from doctors choice of work.
				
				getAppointmentDates(registerDoctors.get().getAppointmentFromTime(),registerDoctors.get().getAppointmentToTime());
				
				List<Appointment> listOfAppointment = appointment.getDoctor().getListOfAppointments();
				
				Boolean flag1 = false;
				
				Boolean flag2 = false;
				
				for(Appointment eachAppointment: listOfAppointment) {
					
					
					
					if(eachAppointment.getAppointmentDateAndTime().isEqual(appointment.getAppointmentDateAndTime())) {
						
						flag1 = true;
						
					}
				}
				
				// check if given date and time if correct or not
				
				for(String str : myTimeDate.keySet()) {
					
					if(myTimeDate.get(str).isEqual(appointment.getAppointmentDateAndTime())) {
						
						flag2 = true;
						
					}
				}
				
				
				
				Appointment registerAppointment = null;
				
				
				
				if(!flag1 && flag2) {
					
					
					
					registerAppointment = appointmentDao.save(appointment); 
					
					////////////////////////////////
					
					emailBody.setEmailBody("Dear Sir/Ma'am, \n You have booked an appointment with " + registerAppointment.getDoctor().getName() +
							". Please make sure to join on time. If you want to call a doctor please contact " + registerAppointment.getDoctor().getMobileNo()+"\n"
							
							+"\n"
							+"Appointment Id: " + registerAppointment.getAppointmentId()+"\n"
							+"Doctor specialty: " + registerAppointment.getDoctor().getSpecialty()+"\n"
							+"Doctor education: " + registerAppointment.getDoctor().getEducation()+"\n"
							+"Doctor experience: " + registerAppointment.getDoctor().getExperience() +"\n"
							+"\n"
							
							+"Thanks and Regards \n"
							+"Appointment Booking Application");
					
					emailBody.setEmailSubject("You have successfully book appointment at " + registerAppointment.getAppointmentDateAndTime());
					
					PatientServiceImpl patientServiceImpl = new PatientServiceImpl(appointment, emailSenderService, emailBody);
					
					Thread emailSentThread = new Thread(patientServiceImpl);
					
					
					
					/////////////////////////
					
					// Multi-Threading
					
					emailSentThread.start();
	
					
					///////////////////////////////
					
					
					
					
					
				}else {
					
					throw new AppointmentException("This time or date already booked or please enter valid appointment time and date " + appointment.getAppointmentDateAndTime());
					
				}
				
				
				// we can't map appointment object directly because we don't have appointment id in it we have to mapped after saving the 
				// appointment and then we will get the appointment id then it will not generate appointment again. If we mapped the register
				// appointment.
				
				// mapping appointment in doctor and then saving doctor
				
				
				
				registerDoctors.get().getListOfAppointments().add(registerAppointment);
				
				doctorDao.save(registerDoctors.get());
				
				// mapping appointment in patient then saving patient
				
				patient.get().getListOfAppointments().add(registerAppointment);
				
				patientDao.save(patient.get());
				
				
				
				return registerAppointment;
				
				
			}else {
				 
				throw new DoctorException("Please enter valid doctors details or doctor not present with thid id " + doctor.getDoctorId());
				
			}
			
			
			
		}else {
			
			throw new LoginException("Please enter valid key"); 
			
		}
	}

	@Override
	public List<Appointment> getAllAppointmenPatientWise(String key) throws AppointmentException, PatientException {
		
		CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> patient = patientDao.findById(currentPatientSession.getUserId());
		
		if(patient.get() != null) {
			
			List<Appointment> listOfAppointments = patient.get().getListOfAppointments();
			
			if(!listOfAppointments.isEmpty()) {
				
				return listOfAppointments;
				
			}else {
				
				throw new AppointmentException("No appointments found. Please book appointments");
			}
			
		}else {
			
			throw new PatientException("Please enter valid patient details");
		}
	}
	
	@Override 
	public Appointment updateAppointment(String key, Appointment newAppointment) throws AppointmentException, PatientException, DoctorException, IOException, TimeDateException {
		
		CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> patient = patientDao.findById(currentPatientSession.getUserId());
		
		if(patient.get() != null) {
			
			Optional<Appointment> registerAppoinment = appointmentDao.findById(newAppointment.getAppointmentId());
			
			Optional<Doctor> registerDoctor = doctorDao.findById(newAppointment.getDoctor().getDoctorId());
			
			if(!registerAppoinment.isEmpty()) {
				
				// check patient updated doctor or not
				
				Doctor newDoctor = newAppointment.getDoctor();
				Doctor oldDoctor = registerAppoinment.get().getDoctor();
				
				Boolean patientChangeDoctorOrNot = newDoctor.getDoctorId() == oldDoctor.getDoctorId() ? true : false;
				
				if(!registerDoctor.isEmpty()) {
					
					// patient did not change the doctor now check patient may have change appointment time then check this doctor is 
					// available at that time or not.
					
					LocalDateTime newTime = newAppointment.getAppointmentDateAndTime();
					LocalDateTime oldTime = registerAppoinment.get().getAppointmentDateAndTime();
					
					if(!newTime.isEqual(oldTime) || !patientChangeDoctorOrNot) {
						
						LocalDateTime presentTime = LocalDateTime.now();
						
						// it will going to check patient is updating appointment correctly or not. Patient is changing appointment
						// when appointment time is left if yes then appointment will not be update at that condition.
						
						if(oldTime.isBefore(presentTime) && !newTime.isAfter(presentTime)) {
							
							throw new AppointmentException("You can't update the appointment at this time. Your appointment date time left.");
						
						}
						
						getAppointmentDates(registerDoctor.get().getAppointmentFromTime(),registerDoctor.get().getAppointmentToTime());
						
						List<Appointment> listOfAppointment = registerDoctor.get().getListOfAppointments();
						
						Boolean flag1 = false;
						
						Boolean flag2 = false;
						
						for(Appointment eachAppointment: listOfAppointment) {
							
							if(eachAppointment.getAppointmentDateAndTime().isEqual(newAppointment.getAppointmentDateAndTime())) {
								
								flag1 = true;
								
							}
						}
						
						// check if given date and time if correct or not
						
						for(String str : myTimeDate.keySet()) {
							
							if(myTimeDate.get(str).isEqual(newAppointment.getAppointmentDateAndTime())) {
								
								flag2 = true;
								
							}
						}
						
						
						
						if(!flag1 && flag2) {
							
							
							registerAppoinment.get().getDoctor().getListOfAppointments().remove(newAppointment);
							
							appointmentDao.save(registerAppoinment.get());
							
							newAppointment.setDoctor(registerDoctor.get());
							
							registerDoctor.get().getListOfAppointments().add(newAppointment);
							
							doctorDao.save(registerDoctor.get());
							
							return newAppointment;
							
							
						}else {
							
							throw new AppointmentException("This time or date already booked. Please enter valid appointment time and date " + newAppointment.getAppointmentDateAndTime());
							
						}
						
						
					}else {
						
						throw new AppointmentException("Please update the appointment. You did not update anythings.");
					}
					
					
					
				}else {
					
					throw new DoctorException("No doctor found with this id " + newAppointment.getDoctor().getDoctorId());
				}
				
				
			}else {
				
				throw new AppointmentException("No appointments found. Please book appointments");
			}
			
		}else {
			
			throw new PatientException("Please enter valid patient details");
		}
		
		
		
		
	}

	@Override
	public List<Doctor> getAllDoctors() throws DoctorException {
		
		List<Doctor> listOfDoctors = doctorDao.findAll();
		
		if(!listOfDoctors.isEmpty()) {
			
			return listOfDoctors;
			
		}else {
			
			throw new DoctorException("No doctors register. Please contact admin.");
		}
		
	}

	@Override
	public Appointment deleteAppointment(Appointment appointment) throws Exception {
		
		Optional<Appointment> registerAppointment = appointmentDao.findById(appointment.getAppointmentId());
		
		// check booking appointment time is left or not 
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		if(localDateTime.isAfter(registerAppointment.get().getAppointmentDateAndTime())) {
			
			throw new TimeDateException("Appointment time already exceeded. You can't cancel the appointment.");
			
		}
		
		// check appointment is exist or not
		if(registerAppointment.isPresent()) {
			
			
			// check doctor is exist or not
			Optional<Doctor> registerDoctor = doctorDao.findById(appointment.getDoctor().getDoctorId());
			
			if(registerDoctor.isPresent()) {
				
				
				
				Optional<Patient> registerPatient = patientDao.findById(appointment.getPatient().getPatientId());
				
				// check patient is exist or not
				if(registerPatient.isPresent()) {
					
					Boolean doctorListFlag = registerDoctor.get().getListOfAppointments().remove(registerAppointment.get());
					
					Boolean patientListFlag = registerPatient.get().getListOfAppointments().remove(registerAppointment.get());
					
					
					if(doctorListFlag && patientListFlag) {
						
						appointmentDao.delete(registerAppointment.get());
						
						// sending mail to patient for successfully canceling booking of appointment 
						
						emailBody.setEmailBody( "Dear Sir/Ma'am, \n You have canceled an appointment with " + registerAppointment.get().getDoctor().getName() 
								
								+"\n"
								+"Appointment Id: " + registerAppointment.get().getAppointmentId()+"\n"
								+"Doctor specialty: " + registerAppointment.get().getDoctor().getSpecialty()+"\n"
								+"Doctor education: " + registerAppointment.get().getDoctor().getEducation()+"\n"
								+"Doctor experience : " + registerAppointment.get().getDoctor().getExperience() +"\n"
								+"\n"
								
								+"Thanks and Regards \n"
								+"Appointment Booking Application");
						
						emailBody.setEmailSubject("Cancel Appointment Booking: " + appointment.getAppointmentDateAndTime() + " successfully");
						
						PatientServiceImpl patientServiceImpl = new PatientServiceImpl(registerAppointment.get(), emailSenderService, emailBody);
						
						Thread emailSentThread = new Thread(patientServiceImpl);
						
						
						
						/////////////////////////
						
						// Multi-Threading
						
						emailSentThread.start();
		
						
						///////////////////////////////
						
						return registerAppointment.get();
						
					}else {
						
						throw new Exception("Something went wrong. Appointment did not cancel");
						
					}
					
					
				}else {
					
					throw new PatientException("No patient found with this id " + appointment.getPatient().getPatientId());
				}
				
			}else {
				
				throw new DoctorException("No doctor found with this id " + appointment.getDoctor().getDoctorId());
			}
			
			
		}else {
			
			throw new AppointmentException("Appointment did not found " + appointment.getAppointmentId());
		}
		
	}
	

	@Override
	public void run() {
		
		// sending mail to patient for successfully booking of appointment 
		
		
		try {
			
			// sending mail to patient for successfully booking of appointment 
			
			
			emailSenderService.sendAppointmentBookingMail(savedAppointment.getPatient().getEmail(), emailBody);
			
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public Review makeReviewToDoctorAppointment(String key, Review review) throws AppointmentException, DoctorException, ReviewException {
		
		Optional<Appointment> registerAppointment = appointmentDao.findById(review.getAppointment().getAppointmentId());
		
		
		
		if(registerAppointment.isPresent()) {
			
			CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
			
			Optional<Patient> registerPatient = patientDao.findById(currentPatientSession.getUserId());
			
			if(registerAppointment.get().getPatient().getPatientId() == registerPatient.get().getPatientId()) {
				
				Optional<Doctor> registerDoctor = doctorDao.findById(review.getDoctor().getDoctorId());
				
				
				if(registerDoctor.isPresent()) {
					
					// we need to check if the appointment time is over or not default appointment time is 1 hour 
					// we have to check if present time is > appointment time + 1 hour
					
					LocalDateTime appointmentDate = registerAppointment.get().getAppointmentDateAndTime().plusHours(1); // added one hour in local date
					
					LocalDateTime presentTime = LocalDateTime.now();
					
					if(appointmentDate.isBefore(presentTime)) {
						
						Review registerReview = registerAppointment.get().getReview();
						
						if(registerReview == null) {
							
							review.setAppointment(registerAppointment.get());
							review.setDoctor(registerDoctor.get());
							review.setPatient(registerPatient.get());
							
							
							Review registerReview2 = reviewDao.save(review);
							
							// map review to doctor
							registerDoctor.get().getListOfReviews().add(registerReview2);
							doctorDao.save(registerDoctor.get());
							
							// map review to patient
							registerPatient.get().getListReviews().add(registerReview2);
							patientDao.save(registerPatient.get());
							
							// map review to appointment
							registerAppointment.get().setReview(registerReview2);
							appointmentDao.save(registerAppointment.get());
							
							return registerReview2;
							
							
						}else {
							
							throw new ReviewException("Review already present please edit review");
						}
						
					}else {
						
						throw new AppointmentException("Please make sure appointment is over or not. Try again later.");
						
					}
					
					
				}else {
					
					throw new DoctorException("Doctor not found with this id " + review.getDoctor().getDoctorId());
				}
				
			}else {
				
				throw new AppointmentException("Please enter valid patient in appointment"); 
			}
			
		}else {
			
			throw new AppointmentException("This appointment id " + review.getAppointment().getAppointmentId() + " does not exist in database ");
		}

	}

	@Override
	public Float getDoctorRating(String key, Doctor doctor) throws DoctorException, ReviewException {
		
		Optional<Doctor> registerDoctor = doctorDao.findById(doctor.getDoctorId());
		
		if(registerDoctor.isPresent()) {
			
			List<Review> listOfReview = registerDoctor.get().getListOfReviews();
			
			Float rating = 0.0f;
			
			if(!listOfReview.isEmpty()) {
				
				for(Review eachReview : listOfReview) {
					
					rating += eachReview.getRating();
				}
				
			}else {
				
				throw new ReviewException("No review found with this doctor"); 
				
			}
			
			// formating our result in only one digit decimal
			
			DecimalFormat decimalFormat = new DecimalFormat("#.#");
			
			rating = rating/listOfReview.size();
			
			return Float.valueOf(decimalFormat.format(rating));
			
		}else {
			
			throw new DoctorException("Doctor not present in database");
		}
		
	}

	@Override
	public Patient getPatientDetails(String key) throws PatientException {
		
		CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> registerPatient = patientDao.findById(currentPatientSession.getUserId());
		
		if(registerPatient.isPresent()) {
			
			return registerPatient.get();
			
			
		}else {
			
			throw new PatientException("Patient not found");
		}
	}

	@Override
	public Review getReviewOfDoctorByPatient(String key, Review review) throws ReviewException, PatientException, DoctorException, AppointmentException {
		
		CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> registerPatient = patientDao.findById(currentPatientSession.getUserId());
		
		if(registerPatient.isPresent()) {
			
			
			Optional<Doctor> registerDoctor = doctorDao.findById(review.getDoctor().getDoctorId()); 
			
			if(registerDoctor.isEmpty()) {
				
				throw new DoctorException("No doctor found with this id " + review.getDoctor().getDoctorId());
				
			}
		
			
			
			
			Optional<Appointment> registerAppointment = appointmentDao.findById(review.getAppointment().getAppointmentId());
			
			if(registerAppointment.isEmpty()) {
				
				throw new AppointmentException("No appointment found with this id");
				
			}
			
			Review review2 = registerAppointment.get().getReview();
			
			if(review2 != null) {
				return review2;
			}else {
				throw new ReviewException("No review found");
			}
			
			
			
			
			
		}else {
			
			throw new PatientException("Patient not found");
		}
	}

	@Override
	public Review updateReview(String key, Review review) throws ReviewException {
		
		Optional<Review> registerReview = reviewDao.findById(review.getReviewId());
		
		if(registerReview.isPresent()) {
			
			return reviewDao.save(review);
			
		}else {
			throw new ReviewException("No reivew found with this id " + review.getReviewId());
		}
		
		
		
	}
	
}






