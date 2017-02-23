//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.chigr.fer.server.workers.BaseWorker;
import com.chigr.fer.server.workers.ForumListWorker;
import com.chigr.fer.server.workers.LoginWorker;
import com.chigr.fer.server.workers.ReadForumWorker;
import com.chigr.fer.server.workers.TopicReadWorker;
import com.chigr.fer.server.workers.URLBuilder;
import com.chigr.http.RequestContext;

/**
 * This is a singleton class that gives an entry point to Forum content represented as HTML pages
 *  Requests:
 *  /forumlist - list of available forumes
 *  /forum/id - loads list of topics in a forum with a given forum ID
 *  /topic/id - loads a topic from scratch (first 50 posts)
 *  /topic/id1/post/id2 - returns topic, navigated to specified post
 *  /topic/id/new - loads topic from last read post.
 * @author achigrin
 * @since 23.05.2016.
 */
public class FERServer {

    private static final FERServer instance = new FERServer();

    private RequestContext ctx = new RequestContext();
    private ServerSettings settings = new ServerSettings();
    private static final Map<String, Class> WORKER_MAP = new HashMap<>();

    static{
        WORKER_MAP.put(URLBuilder.FORUMLIST, ForumListWorker.class);
        WORKER_MAP.put(URLBuilder.FORUM, ReadForumWorker.class);
        WORKER_MAP.put(URLBuilder.TOPIC, TopicReadWorker.class);
    }


    private FERServer() {
    }

    public static FERServer getInstance() {
        return instance;
    }

    public RequestContext getCtx() {
        return ctx;
    }

    public ServerSettings getSettings() {
        return settings;
    }

    public String getPage(String url) throws FERException {
        Properties props = parseUrl(url);
        BaseWorker worker = getWorker(props.getProperty(BaseWorker.WORKER_NAME));
        return worker.execute(props);
    }

    public String getErrorPage(FERException err) {
        return BaseWorker.buildPage(BaseWorker.getTitle("Error page"),
                err.getMessage());
    }

    private BaseWorker getWorker(String workerName) throws FERException{
        Class clazz = WORKER_MAP.get(workerName);
        if (null != clazz) {
            try {
                BaseWorker worker = (BaseWorker) clazz.newInstance();
                worker.setCtx(getCtx());
                worker.setSettings(getSettings());
                return worker;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new FERException("Behavior not defined for "+workerName, e);
            }
        }
        return null;
    }

    private Properties parseUrl(String url) throws FERException{
        Properties props = new Properties();
        try {
            URL oURL = new URL(url);
            String path = oURL.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            String[] items = path.split("/");
            props.setProperty(BaseWorker.WORKER_NAME, items[0].trim());
            props.put(BaseWorker.RAW_PARAMS, items);
        } catch (MalformedURLException e) {
            throw new FERException("Incorrect URL "+url, e);
        }
        return props;
    }

    public void login(String userName, String password) throws FERException {
        // Here we create Method call, executes it, parse result.
        // If result is failure then we throw FERException
        Properties props = new Properties();
        props.setProperty(LoginWorker.USER, userName);
        props.setProperty(LoginWorker.PASSWORD, password);
        LoginWorker worker = new LoginWorker(ctx, settings);
        worker.execute(props);
    }

    public static void main(String[] args) {
        try {
            Properties p = FERServer.getInstance().parseUrl(URLBuilder.getForumURL("123"));
            System.out.println("Number of porperties "+p.size());
            System.out.println("Worker name is  "+p.getProperty(BaseWorker.WORKER_NAME));

        } catch (FERException e) {
            e.printStackTrace();
        }
    }
}
