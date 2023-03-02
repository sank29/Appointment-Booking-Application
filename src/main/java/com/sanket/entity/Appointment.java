package com.sanket.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}