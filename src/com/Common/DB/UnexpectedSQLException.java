package com.Common.DB;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * occurs when an unexpected SQL exception occurs
 */
public class UnexpectedSQLException extends RuntimeException {
    SQLException exception;
    public UnexpectedSQLException(SQLException exception){
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return exception.getLocalizedMessage();
    }

    @Override
    public Throwable getCause() {
        return exception.getCause();
    }

    @Override
    public Throwable initCause(Throwable cause) {
        return exception.initCause(cause);
    }

    @Override
    public String toString() {
        return exception.toString();
    }

    @Override
    public void printStackTrace() {
        exception.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        exception.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        exception.printStackTrace(s);
    }

    @Override
    public Throwable fillInStackTrace() {
        return exception.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return exception.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        exception.setStackTrace(stackTrace);
    }
}
