package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.Appointment;

public interface AppointmentDao extends JpaRepository<Appointment, Integer> {

}
