package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.Doctor;

public interface DoctorDao extends JpaRepository<Doctor, Integer> {

}
