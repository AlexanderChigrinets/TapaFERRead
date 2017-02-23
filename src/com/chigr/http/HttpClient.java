//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class HttpClient {
    public static final int STATUS_OK = 200;
    private String urlStr;
    private HttpURLConnection connection;

    public HttpClient(String urlStr) {
        this.urlStr = urlStr;
    }

    public String get() throws IOException {
        URL url = new URL(urlStr);
        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.connect();
        int code = connection.getResponseCode();
        String response = readResponse(connection.getInputStream());
        if (STATUS_OK ==code) {
            return response;
        }
        throw new HttpException("Error reading "+urlStr, code, response);
    }

    public String post(String data, RequestContext ctx) throws IOException {
        URL url = new URL(urlStr);
        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        if (ctx.getCookies() != null) {
            connection.setRequestProperty("Cookie", ctx.getCookies());
        }
        connection.setDoOutput(true);
        connection.setDoInput(true);



        OutputStream outputStream = connection.getOutputStream();

        writeOutput(outputStream, data);

        connection.connect();

        int code = connection.getResponseCode();
        String response = readResponse(connection.getInputStream());
        // Mobiquo_is_login
        ctx.setLoginStatus(connection.getHeaderField("Mobiquo_is_login"));
        if (connection.getHeaderField("Set-Cookie") != null) {
            ctx.setCookies(connection.getHeaderField("Set-Cookie"));
        }
        if (STATUS_OK ==code) {
            return response;
        }
        throw new HttpException("Error reading "+urlStr, code, response);
    }

    private void writeOutput(OutputStream outputStream, String data) throws IOException {
        outputStream.write(data.getBytes("utf-8"));
        outputStream.flush();
    }

    private String readResponse(InputStream inputStream) throws IOException {
        byte[] buf = new byte[1024];
        StringBuilder res = new StringBuilder();

        int read = inputStream.read(buf);
        while (read > 0) {
            res.append(new String(buf, 0, read));
            read = inputStream.read(buf);
        }
        return res.toString();
    }
}
