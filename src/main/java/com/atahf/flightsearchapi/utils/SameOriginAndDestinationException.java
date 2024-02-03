package com.atahf.flightsearchapi.utils;

public class SameOriginAndDestinationException extends Exception {
    public SameOriginAndDestinationException() {
        super();
    }

    public SameOriginAndDestinationException(String message) {
        super(message);
    }

    public SameOriginAndDestinationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameOriginAndDestinationException(Throwable cause) {
        super(cause);
    }
}
