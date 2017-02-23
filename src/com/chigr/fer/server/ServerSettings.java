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
public class ServerSettings {
    public static final String FER_URL = "http://club443.ru/mobiquo/mobiquo.php";
    private String apiURL = FER_URL;

    public ServerSettings() {
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }
}
