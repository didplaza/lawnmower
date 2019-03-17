package com.mowitnow.mower;

/**
 * Exception thrown by Mower project when execution can't continue
 *
 * @author didier
 * @version 1.0
 */
public class MowerException extends Exception {
    public MowerException(String message) {
        super(message);
    }
}
