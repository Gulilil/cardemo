package com.carweb.cardemo.repository;

import com.carweb.cardemo.model.ElectricalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElectricalCarRepository extends JpaRepository<ElectricalCar, UUID> {
}