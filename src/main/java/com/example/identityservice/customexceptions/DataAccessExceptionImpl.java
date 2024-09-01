package com.example.identityservice.customexceptions;

import org.springframework.dao.DataAccessException;

public class DataAccessExceptionImpl extends DataAccessException {
    public DataAccessExceptionImpl(String msg) {
        super(msg);
    }

    public DataAccessExceptionImpl(String msg, Throwable cause) {
        super(msg, cause);
    }
}
