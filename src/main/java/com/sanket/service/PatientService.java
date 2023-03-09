package com.sanket.service;




import java.io.IOException;
import java.util.List;

import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentPatientSession;
import com.sanket.entity.Patient;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.TimeDateException;


public interface PatientService {
	
	public Patient createPatient(Patient customer) throws PatientException; 
	
	public Patient updatePatient(Patient patient, String key) throws PatientException;
	
	public Patient getPatientByUuid(String uuid) throws PatientException;
	
	public CurrentPatientSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	public Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, DoctorException, IOException, TimeDateException;
	
	
	public List<Appointment> getAllAppointmenPatientWise(String key)throws AppointmentException, PatientException;
	
	public Appointment updateAppointment(String key, Appointment newAppointment) throws AppointmentException, PatientException, DoctorException, IOException, TimeDateException;
	
}
