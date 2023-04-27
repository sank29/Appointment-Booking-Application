package com.sanket.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.entity.Review;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.ReviewException;
import com.sanket.exception.TimeDateException;
import com.sanket.repository.DoctorDao;
import com.sanket.repository.SessionDao;

@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException {
		
		List<Doctor> listOfDoctors = doctorDao.findAll();
		
		if(!listOfDoctors.isEmpty()) {
			
			return listOfDoctors;
			
		}else {
			
			throw new DoctorException("No doctors register till now.");
		}
		
		
	}

	@Override
	public List<LocalDateTime> getDoctorAvailableTimingForBooking(String key, Doctor doctor) throws IOException, TimeDateException, DoctorException {
		
		Optional<Doctor> registerDoctor = doctorDao.findById(doctor.getDoctorId());
		
		List<LocalDateTime> doctorAvailableTiming = new ArrayList<>();
		
		if(registerDoctor.isPresent()) {
			
			PatientServiceImpl.getAppointmentDates(registerDoctor.get().getAppointmentFromTime(), registerDoctor.get().getAppointmentToTime());
			
		    Map<String, LocalDateTime> myTimeDate = PatientServiceImpl.myTimeDate;
		    
		    List<Appointment> listOfDoctorAppointment = registerDoctor.get().getListOfAppointments();
		    
		    
		    
		    
		    
		    for(String str: myTimeDate.keySet()) {
		    	
		    	Boolean flag = false;
		    	
		    	for(Appointment eachAppointment: listOfDoctorAppointment) {
		    		
		    		LocalDateTime localDateTime = myTimeDate.get(str);
		    		
		    		if(localDateTime.isEqual(eachAppointment.getAppointmentDateAndTime())) {
		    			
		    			flag = true;
		    			break;
		    			
		    		}
		    	}
		    	
		    	if(flag == false) {
		    		
		    		doctorAvailableTiming.add(myTimeDate.get(str));
		    		
		    	}
		    }
		    
			if(!doctorAvailableTiming.isEmpty()) {
				
				return doctorAvailableTiming;
				
			}else {
				
				throw new DoctorException("No time and date available to book appointment. Please try again");
			}
			
			
		}else {
			
			throw new DoctorException("No doctor found with this id " + doctor.getDoctorId());
		}
		
		
		
	}

	@Override
	public Doctor getDoctorByUuid(String uuid) throws PatientException {
		
		CurrentSession currentDoctor = sessionDao.findByUuid(uuid);
		
		Optional<Doctor> doctor = doctorDao.findById(currentDoctor.getUserId());
		
		if(doctor.isPresent()) {
			
			return doctor.get();
		
		}else {
			
			throw new PatientException("Doctor not present by this uuid " + uuid);
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

	@Override
	public List<Appointment> getUpcommingDoctorAppointment(Doctor doctor) throws AppointmentException {
		
		List<Appointment> listOfAppointments = doctor.getListOfAppointments();
		
		List<Appointment> listOfCommingAppointmnet = new ArrayList<>();
		
		LocalDateTime currentTimeAndDate = LocalDateTime.now();
		
		
		try {
			
			for(Appointment eachAppointment: listOfAppointments) {
				
				if(eachAppointment.getAppointmentDateAndTime().isAfter(currentTimeAndDate)) {
					
					listOfCommingAppointmnet.add(eachAppointment);
				}
			}
		}catch(Exception exception) {
			
			System.out.println(exception.fillInStackTrace());
			
		}
		
		if(!listOfCommingAppointmnet.isEmpty()) {
			
			return listOfCommingAppointmnet;
		
		}else {
			
			throw new AppointmentException("No upcoming appointments. Sorry!");
			
		}
	}

	@Override
	public List<Appointment> getPastDoctorAppointment(Doctor doctor) throws AppointmentException {
		
		List<Appointment> listOfAppointments = doctor.getListOfAppointments();
		
		List<Appointment> listOfPastAppointments = new ArrayList<>();
		
		LocalDateTime currentTimeAndDate = LocalDateTime.now();
		
		testing();
		
		
		try {
			
			for(Appointment eachAppointment: listOfAppointments) {
				
				if(eachAppointment.getAppointmentDateAndTime().isBefore(currentTimeAndDate)) {
					
					listOfPastAppointments.add(eachAppointment);
					
				}
				
			}
			
		}catch(Exception exception) {
			
			
			System.out.println(exception.fillInStackTrace());
			
		}
		
		if(!listOfPastAppointments.isEmpty()) {
			
			return listOfPastAppointments;
		
		}else {
			
			throw new AppointmentException("No past appointments. Sorry!");
			
		}
	}
	
	public static void testing() {
		
		 int strength = 10; // work factor of bcrypt
		 
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
		 
		 String encodedPassword = bCryptPasswordEncoder.encode("1234");
		 
	}

	@Override
	public List<Appointment> getAllAppointments(Doctor registerDoctor) throws DoctorException {
		
		List<Appointment> listOfAppointments = registerDoctor.getListOfAppointments();
		
		if(!listOfAppointments.isEmpty()) {
			
			return listOfAppointments;
			
		}else {
			
			throw new DoctorException("No appointments found.");
		}
	}

	@Override
	public Doctor getDoctorDetails(String key) throws PatientException {
		
		CurrentSession currentDoctor = sessionDao.findByUuid(key);
		
		Optional<Doctor> doctor = doctorDao.findById(currentDoctor.getUserId());
		
		if(doctor.isPresent()) {
			
			return doctor.get();
		
		}else {
			
			throw new PatientException("Doctor not present by this uuid " + key);
		}
		
		
	}

	@Override
	public Review getReviewOfParticularAppointment(String key, Appointment appointment) throws PatientException, ReviewException {
		
		CurrentSession currentDoctor = sessionDao.findByUuid(key);
		
		Optional<Doctor> registerDoctor = doctorDao.findById(currentDoctor.getUserId());
		
		if(registerDoctor.isPresent()) {
			
			List<Review> listOfReview = registerDoctor.get().getListOfReviews();
			
			if(!listOfReview.isEmpty()) {
				
				for(Review eachReview: listOfReview) {
					
					if(eachReview.getAppointment().getAppointmentId() == appointment.getAppointmentId()) {
						
						return eachReview;
					}
				}
				
				throw new ReviewException("No review found");
				
			}else {
				
				throw new ReviewException("No review found with this doctor");
				
			}
		}else {
			
			throw new PatientException("Doctor not present by this uuid " + key);
		}
	}

}




