package com.cairone;

public class AppRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AppRuntimeException(Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
    }

    public AppRuntimeException(String format, Object... args) {
        super(String.format(format, args));
    }

}
