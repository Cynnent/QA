package com.cynnent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuitableDriverException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger(NoSuitableDriverException.class);

    public NoSuitableDriverException(String message) {
        super(message);
        log.error(message);
    }
}
