package com.sanket.entity;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.TableGenerator;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	                generator = "id_table"
			)
	
	@SequenceGenerator(
			name = "id_table",
			sequenceName = "id_sequence",
			allocationSize = 1
			)
	private Integer doctorId;
	
	private String mobileNo;
	
	
	private String password;
	
	private String name;
	
	private String specialty;
	
	private String location;
	
//	@Column(name = "insurance")
	private Boolean insuranceAcceptance;
	
	private String education;
	
	private String experience;
	
//	@OneToMany(cascade = CascadeType.ALL,mappedBy = "appointmentId")
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	
	// put time only 24 hours formate
	
//	@Column(name = "from")
	private Integer appointmentFromTime;
	
	// put time only 24 hours formate
	
//	@Column(name = "to")
	private Integer appointmentToTime;
	
	// for checking this is doctor or patient
	
	private String type;
	
	@OneToMany
	@JsonIgnore
	private List<Review> listOfReviews = new ArrayList<>();
	
	private String doctorImg;
}






















