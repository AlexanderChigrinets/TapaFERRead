//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.http;

import java.io.IOException;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class HttpException extends IOException {
    private int responseCode;
    private String httpErrorMessage;

    public HttpException(String message, int responseCode, String httpErrorMessage) {
        super(message);
        this.responseCode = responseCode;
        this.httpErrorMessage = httpErrorMessage;
    }
}
