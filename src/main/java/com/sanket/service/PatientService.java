package com.sanket.service;




import java.io.IOException;
import java.util.List;

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

import jakarta.mail.MessagingException;


public interface PatientService {
	
	public Patient createPatient(Patient customer) throws PatientException; 
	
	public Patient updatePatient(Patient patient, String key) throws PatientException;
	
	public Patient getPatientByUuid(String uuid) throws PatientException;
	
	public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	public Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, DoctorException, IOException, TimeDateException, MessagingException;
	
	
	public List<Appointment> getAllAppointmenPatientWise(String key)throws AppointmentException, PatientException;
	
	public Appointment updateAppointment(String key, Appointment newAppointment) throws AppointmentException, PatientException, DoctorException, IOException, TimeDateException;
	
	public List<Doctor> getAllDoctors() throws DoctorException;
	
	public Appointment deleteAppointment(Appointment appointment) throws AppointmentException, DoctorException, Exception;

	public Review makeReviewToDoctorAppointment(String key, Review review) throws AppointmentException, DoctorException, ReviewException;
	
	public Review updateReview(String key, Review review) throws ReviewException;

	public Float getDoctorRating(String key, Doctor doctor) throws DoctorException, ReviewException;

	public Patient getPatientDetails(String key) throws PatientException;
	
	public Review getReviewOfDoctorByPatient(String key,Review review) throws ReviewException, PatientException, DoctorException, AppointmentException;
}
