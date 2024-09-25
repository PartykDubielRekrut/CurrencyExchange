package org.example.currencyexchange.api.exception;

public class ApiNbpException extends RuntimeException {
    public ApiNbpException() {
        super("API NBP is not responding");
    }

}
