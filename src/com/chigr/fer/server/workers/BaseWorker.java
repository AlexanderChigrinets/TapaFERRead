//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.workers;

import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.chigr.fer.server.FERException;
import com.chigr.fer.server.ServerSettings;
import com.chigr.http.HttpClient;
import com.chigr.http.RequestContext;
import com.chigr.tapaparse.ResponseParser;
import com.chigr.tapatalk.MethodCall;
import com.chigr.tapatalk.MethodResponse;
import com.chigr.tapatalk.MethodResponseBase;
import com.chigr.tapatalk.MethodResponseFault;

/**
 * Superclass for single request-response operation.
 * @author achigrin
 * @since 23.05.2016.
 */
public abstract class BaseWorker {
    public static final String HTML_TEMPLATE = "<!DOCTYPE html>\r<html>\r<head><meta charset=\"UTF-8\">%s</head>\r" +
            "<body>%s</body></html>";
    protected static final String TITLE_PATTERN = "<title>%s</title>";
    public static final String WORKER_NAME = "ferServer.worker";
    public static final String RAW_PARAMS = "raw_params";
    private RequestContext ctx;
    private ServerSettings settings;

    protected BaseWorker() {
    }

    public static String buildPage(String header, String body) {
        return String.format(HTML_TEMPLATE, header, body);
    }

    public void setCtx(RequestContext ctx) {
        this.ctx = ctx;
    }

    public void setSettings(ServerSettings settings) {
        this.settings = settings;
    }

    public RequestContext getCtx() {
        return ctx;
    }

    public ServerSettings getSettings() {
        return settings;
    }

    public String execute(Properties props) throws FERException {
        long start = System.currentTimeMillis();
        System.out.println("Start request");
        HttpClient client = new HttpClient(getSettings().getApiURL());
        MethodCall call = createMethodCall(props);
        try {
            String responseStr = client.post(call.toXML(), getCtx());
            long tmp = System.currentTimeMillis();
            System.out.println("Got server response "+(tmp-start));
            ResponseParser parser = new ResponseParser();
            MethodResponseBase response = parser.parse(responseStr);
            if (response.isFailure()) {
                responseToException((MethodResponseFault)response);
            }
            else {
                String res =  processResponse((MethodResponse)response);
                System.out.println("Response handled "+(System.currentTimeMillis()-tmp));
                return res;
            }
        } catch (IOException e) {
            throw new FERException("Error executing Server API: "+call.getMethodName(), e);
        } catch (ParserConfigurationException | SAXException e) {
            throw new FERException("Failure parsing API response.", e);
        }
        return null;
    }

    protected abstract String processResponse(MethodResponse response);

    protected abstract MethodCall createMethodCall(Properties props);

    protected void responseToException(MethodResponseFault response) throws FERException{
        int code = response.getError().getStruct().getInt("faultCode");
        String msg = response.getError().getStruct().getString("faultString");
        throw new FERException(msg, code);
    }

    public static String getTitle(String label) {
        return String.format(TITLE_PATTERN, label);
    }
}
