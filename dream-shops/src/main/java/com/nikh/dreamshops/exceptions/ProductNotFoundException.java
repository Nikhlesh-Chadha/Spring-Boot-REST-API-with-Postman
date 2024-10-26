package com.nikh.dreamshops.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message); // the super keyword in java refers to dealing with parent class object while super() deals with parent class constructor.
    }
}
