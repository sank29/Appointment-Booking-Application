package com.sanket.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer doctorId;
	
	private String name;
	
	private String specialty;
	
	private String location;
	
	private Boolean insuranceAcceptance;
	
	private LocalDate availabilityStart;
	
	private LocalDate availabilityEnd;
	
	private String education;
	
	private String experience;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Appointment> listOFAppointments = new ArrayList<>();
	
	
}



























