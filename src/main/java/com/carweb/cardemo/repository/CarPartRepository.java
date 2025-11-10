package com.carweb.cardemo.repository;

import com.carweb.cardemo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarPartRepository extends JpaRepository<Car, UUID> {
}