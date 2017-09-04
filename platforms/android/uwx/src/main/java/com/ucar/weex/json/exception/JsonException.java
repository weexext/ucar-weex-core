package com.ucar.weex.json.exception;

/**
 * Created by david on 16/3/30.
 */
public class JsonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JsonException() {
    }

    public JsonException(String detailMessage) {
        super(detailMessage);
    }

    public JsonException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public JsonException(Throwable throwable) {
        super(throwable);
    }
}
