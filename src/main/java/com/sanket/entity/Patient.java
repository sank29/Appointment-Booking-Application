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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Please enter valid mobile number")
	private String mobileNo;
	
	
	private String password;
	
	@Email(message = "Email should be a valid email")
	private String email;
	
	private String type;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Review> listReviews = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Message> listOfMessage = new ArrayList<>();
	

}

