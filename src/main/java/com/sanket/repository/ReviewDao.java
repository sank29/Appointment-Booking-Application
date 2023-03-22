package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.Review;

public interface ReviewDao extends JpaRepository<Review, Integer> {

}
