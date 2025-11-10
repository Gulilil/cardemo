package com.carweb.cardemo.repository;

import com.carweb.cardemo.model.TwoWheelsCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TwoWheelsCarRepository extends JpaRepository<TwoWheelsCar, UUID> {
}