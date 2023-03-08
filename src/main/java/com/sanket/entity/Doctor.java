package com.sanket.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
@ToString
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer doctorId;
	
	private String name;
	
	private String specialty;
	
	private String location;
	
	private Boolean insuranceAcceptance;
	
	private String education;
	
	private String experience;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	
	// put time only 24 hours formate
	
	private Integer appointmentFromTime;
	
	// put time only 24 hours formate
	
	
	private Integer appointmentToTime;
	
}






















