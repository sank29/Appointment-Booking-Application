package com.sanket.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer patientId;
	
	private String name;
	
	private String mobileNo;
	
	private String password;
	
	private String email;
	
	private String type;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	

}





















