package com.boot_demo1.shipment.repository;

import com.boot_demo1.shipment.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository  extends JpaRepository<Shipment, Long> {
   Optional<Shipment> findByTrackingNumber(String trackingNumber);
}
