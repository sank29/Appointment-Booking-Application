package com.sanket.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.print.Doc;

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


public interface DoctorService {
	
	List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException;
	
	Doctor getDoctorByUuid(String uuid) throws PatientException;
	
	CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	List<LocalDateTime> getDoctorAvailableTimingForBooking(String key, Doctor doctor) throws IOException, TimeDateException, DoctorException;
	
	List<Appointment> getUpcommingDoctorAppointment(Doctor doctor) throws AppointmentException;
	
	List<Appointment> getPastDoctorAppointment(Doctor doctor) throws AppointmentException;
	
	List<Appointment> getAllAppointments(Doctor registerDoctor) throws DoctorException;
	
	Doctor getDoctorDetails(String key) throws PatientException;

	Review getReviewOfParticularAppointment(String key, Appointment appointment) throws PatientException, ReviewException;
	
}
