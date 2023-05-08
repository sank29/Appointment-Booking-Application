package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.Message;

public interface MessageDao extends JpaRepository<Message, Integer> {

}
