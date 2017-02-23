//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.workers;

/**
 * @author achigrin
 * @since 23.05.2016.
 */
public class URLBuilder {

    public static final String FER_INNER_SERVER = "fer.client.go";
    private static final String PREFIX = "http://"+FER_INNER_SERVER+"/";
    public static final char URL_SEPARATOR = '/';
    public static final String FORUMLIST = "forumlist";
    public static final String FORUM = "forum";
    public static final String TOPIC = "topic";

    public static String getForumURL(String forumId) {
        StringBuilder builder = new StringBuilder(PREFIX);
        builder.append(FORUM).append(URL_SEPARATOR).append(forumId);
        return builder.toString();
    }

    public static String getForumListURL() {
        return PREFIX+ FORUMLIST;
    }

    public static String getTopicURL(String topicId) {
        StringBuilder builder = new StringBuilder(PREFIX);
        builder.append(TOPIC).append(URL_SEPARATOR).append(topicId);
        return builder.toString();
    }


}
