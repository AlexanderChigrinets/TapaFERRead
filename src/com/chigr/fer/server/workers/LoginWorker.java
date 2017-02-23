//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.workers;

import java.util.Properties;

import com.chigr.fer.server.FERException;
import com.chigr.fer.server.ServerSettings;
import com.chigr.http.RequestContext;
import com.chigr.tapatalk.MethodCall;
import com.chigr.tapatalk.MethodResponse;
import com.chigr.tapatalk.TapaBase64;
import com.chigr.tapatalk.TapaStruct;

/**
 * @author achigrin
 * @since 23.05.2016.
 */
public class LoginWorker extends BaseWorker {

    public static final String USER = "user";
    public static final String PASSWORD = "password";

    public LoginWorker(RequestContext ctx, ServerSettings settings) {
        super();
        setCtx(ctx);
        setSettings(settings);
    }

    @Override
    protected String processResponse(MethodResponse response) {
        TapaStruct userInfo = (TapaStruct) response.get(0);

        return "Logged in successfully. "+userInfo.getString("username");
    }

    @Override
    protected MethodCall createMethodCall(Properties props) {
        MethodCall req = new MethodCall("login");
        try {
            req.add(new TapaBase64(props.getProperty(USER), false));
            req.add(new TapaBase64(props.getProperty(PASSWORD), false));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating login request", e);
        }
        return req;
    }
}
