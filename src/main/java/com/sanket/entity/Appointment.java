package com.sanket.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer appointmentId;
	
	@ManyToOne
	Patient patient;
	
	
	// Appointement default time will be 15 mins from appoaintment start time.
	

	LocalDateTime appointmentDateAndTime;
	
	
	@ManyToOne
	Doctor doctor;


	@Override
	public int hashCode() {
		return Objects.hash(appointmentId);
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			
			return true;
		
		if (obj == null)
			
			return false;
		
		if (getClass() != obj.getClass())
			
			return false;
		
		Appointment other = (Appointment) obj;
		
		return Objects.equals(appointmentId, other.appointmentId);

	}
	
	

}
















