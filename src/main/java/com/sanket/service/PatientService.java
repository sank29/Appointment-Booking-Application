package com.sanket.service;

import java.io.IOException;
import java.util.List;
import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.ForgetPassword;
import com.sanket.entity.Patient;
import com.sanket.entity.Review;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PasswordException;
import com.sanket.exception.PatientException;
import com.sanket.exception.ReviewException;
import com.sanket.exception.TimeDateException;

import jakarta.mail.MessagingException;


public interface PatientService {
	
	Patient createPatient(Patient customer) throws PatientException; 
	
	Patient updatePatient(Patient patient, String key) throws PatientException;
	
	Patient getPatientByUuid(String uuid) throws PatientException;
	
	CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, DoctorException, IOException, TimeDateException, MessagingException;
	
	List<Appointment> getAllAppointmenPatientWise(String key)throws AppointmentException, PatientException;
	
	Appointment updateAppointment(String key, Appointment newAppointment) throws AppointmentException, PatientException, DoctorException, IOException, TimeDateException;
	
	List<Doctor> getAllDoctors() throws DoctorException;
	
	Appointment deleteAppointment(Appointment appointment) throws AppointmentException, DoctorException, Exception;

	Review makeReviewToDoctorAppointment(String key, Review review) throws AppointmentException, DoctorException, ReviewException;
	
	Review updateReview(String key, Review review) throws ReviewException;

	Float getDoctorRating(String key, Doctor doctor) throws DoctorException, ReviewException;

	Patient getPatientDetails(String key) throws PatientException;
	
	Review getReviewOfDoctorByPatient(String key,Review review) throws ReviewException, PatientException, DoctorException, AppointmentException;

	Patient forgetPassword(String key, ForgetPassword forgetPassword) throws PasswordException;

	Review deleteReview(String key, Review review) throws ReviewException; 
	
}
