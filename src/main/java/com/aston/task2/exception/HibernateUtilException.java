package com.aston.task2.exception;

public class HibernateUtilException extends RuntimeException{
    public HibernateUtilException(String message) {
        super(message);
    }

    public HibernateUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public HibernateUtilException(Throwable cause) {
        super(cause);
    }
}
