//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr;

import java.io.FileOutputStream;
import java.io.IOException;

import com.chigr.fer.server.FERServer;
import com.chigr.fer.server.workers.URLBuilder;
import com.chigr.tapatalk.MethodCall;
import com.chigr.tapatalk.TapaBase64;
import com.chigr.tapatalk.TapaBoolean;
import com.chigr.tapatalk.TapaInt;
import com.chigr.tapatalk.TapaString;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class TestApp {
    private static final String FER_URL = "http://club443.ru/mobiquo/mobiquo.php";
    public static void main(String[] args) {
        try{
/*
            HttpClient cli = new HttpClient(FER_URL);
            MethodCall getConfig = createGetForum();
            saveOutput(getConfig.toXML(), "request.xml");

            RequestContext ctx = new RequestContext();
            ctx.setCookies("MSID=91444336672a95411dbaf6abe937852d; expires=Tue, 23-May-2017 19:58:00 GMT");

            String res = cli.post(getConfig.toXML(), ctx);

            saveOutput(res, "output.xml");
            ResponseParser parser = new ResponseParser();
            MethodResponseBase resp = parser.parse(res);

            saveOutput(resp.toDecodedXML(), "responseParsed.xml");
            System.out.println("Got info");
            System.out.println("Authenticated "+ctx.isAuthenticated());
            System.out.println("Cookies "+ctx.getCookies());
*/

            FERServer server = FERServer.getInstance();
            server.getCtx().setCookies("MSID=af4450fb7a5686c9f62e37a985853159");
            //server.login("Developer", "hfphf,jnxbr");
            System.out.println("Cookies: " + server.getCtx().getCookies());
            String id = "176477";
            //192411
            String html = server.getPage(URLBuilder.getTopicURL(id));
            if (null != html) {
                saveOutput(html, String.format("topic%s.html", id));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static MethodCall createGetBoxInfo() {
        //get_box_info
        return new MethodCall("get_box_info");
    }

    private static MethodCall createGetConfig() {
        MethodCall req = new MethodCall("get_config");
        return req;
    }

    private static MethodCall createLogin() throws Exception {
        MethodCall req = new MethodCall("login");
        req.add(new TapaBase64("Developer", false));
        req.add(new TapaBase64("hfphf,jnxbr", false));
        return req;
    }

    private static MethodCall createReplyPost() throws Exception {
        MethodCall req = new MethodCall("reply_post");
        req.add(new TapaString("89"));
        req.add(new TapaString("50531"));
        req.add(new TapaBase64("Simple Subject", false));
        req.add(new TapaBase64("Simple text.", false));
        return req;
    }

    private static MethodCall createUserInfo() throws Exception {
        MethodCall req = new MethodCall("get_user_info");
        req.add(new TapaBase64("кузнечик", false));
        return req;
    }

    private static MethodCall createReadTopicByPost() throws Exception {
        MethodCall req = new MethodCall("get_thread_by_post");
        req.add(new TapaString("29591080"));
        req.add(new TapaInt("50"));
        req.add(new TapaBoolean("1"));
        return req;
    }

    private static MethodCall createGetTopic() throws Exception {
        MethodCall req = new MethodCall("get_thread");
        req.add(new TapaString("50531"));
        req.add(new TapaInt("0"));
        req.add(new TapaInt("150"));
        req.add(new TapaBoolean("1"));
        return req;
    }


    private static MethodCall createGetForum() {
        MethodCall req = new MethodCall("get_forum");
        return req;
    }

    private static void saveOutput(String res, String fileName)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(res.getBytes("utf-8"));
        fos.close();
    }
}
