//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.http;

/**
 * @author achigrin
 * @since 22.05.2016.
 */
public class RequestContext {
    private boolean isAuthenticated;
    private String cookies;

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String value) {
        int i = value.indexOf(';');
        if (i > -1) {
            cookies = value.substring(0, i);
        }
        else {
            this.cookies = value;
        }
    }

    public void setLoginStatus(String value) {
        if (null != value) {
            isAuthenticated = Boolean.parseBoolean(value);
        }
    }

}
