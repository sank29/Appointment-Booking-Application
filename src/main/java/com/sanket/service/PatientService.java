package com.sanket.service;



import com.sanket.entity.Patient;
import com.sanket.exception.PatientException;


public interface PatientService {
	
	public Patient createPatient(Patient customer) throws PatientException; 
	
	public Patient updatePatient(Patient patient, String key) throws PatientException;
	
	public Patient getPatientByUuid(String uuid) throws PatientException;
	
}
