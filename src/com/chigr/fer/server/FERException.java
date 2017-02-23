//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server;

/**
 * @author achigrin
 * @since 23.05.2016.
 */
public class FERException extends Exception {
    private int errorCode;

    public FERException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public FERException(String message, Throwable cause) {
        this(message, cause, 0);
    }
    public FERException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
