package com.inventory.exception;

/** Thrown when someone tries to remove more stock than is available. */
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
