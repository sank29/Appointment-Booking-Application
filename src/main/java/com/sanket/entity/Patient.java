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
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	
    generator = "id_table"
    
			)

	@SequenceGenerator(
			
		name = "id_table",
		sequenceName = "id_sequence",
		allocationSize = 1
		
	)
	
	private Integer patientId;
	
	private String name;
	
	private String mobileNo;
	
	private String password;
	
	private String email;
	
	private String type;
	
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appointmentId")
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	

}





















