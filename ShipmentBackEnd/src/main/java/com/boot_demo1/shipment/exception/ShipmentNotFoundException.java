package com.boot_demo1.shipment.exception;

public class ShipmentNotFoundException extends RuntimeException{
    public ShipmentNotFoundException(String message) {
        super(message);
    }
}
