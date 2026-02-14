package com.boot_demo1.shipment.controller;

import com.boot_demo1.shipment.dto.ShipmentRequest;
import com.boot_demo1.shipment.dto.ShipmentResponse;
import com.boot_demo1.shipment.dto.UpdateStatusRequest;
import com.boot_demo1.shipment.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "http://localhost:4200")

public class ShipmentController {

    @Autowired
    private  ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<?> createShipment(@Valid @RequestBody ShipmentRequest shipmentRequest) {
        var shipment =shipmentService.createShipment(shipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
    }


    @GetMapping
  public ResponseEntity<List<ShipmentResponse>> getAllShipments() {
        List<ShipmentResponse> shipmentResponses = shipmentService.getAllShipments();
        return ResponseEntity.status(HttpStatus.OK).body(shipmentResponses);
  }


    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponse> getShipmentById(@PathVariable Long id) {
        ShipmentResponse shipmentResponse = shipmentService.getShipmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(shipmentResponse);
    }


    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ShipmentResponse>getShipmentByTrackingNumber(@Valid @PathVariable String trackingNumber) {

        ShipmentResponse shipmentResponse = shipmentService.getBytrackingNumber(trackingNumber);
        return ResponseEntity.status(HttpStatus.OK).body(shipmentResponse);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateShipmentStatus(@PathVariable Long id,
                                                  @Valid @RequestBody
                                                  UpdateStatusRequest request) {
        ShipmentResponse shipment = shipmentService.updateShipmentStatus(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(shipment);
    }









    }
