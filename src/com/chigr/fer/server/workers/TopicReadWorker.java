//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.workers;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.chigr.fer.server.model.PostDTO;
import com.chigr.fer.server.model.TopicDTO;
import com.chigr.tapatalk.MethodCall;
import com.chigr.tapatalk.MethodResponse;
import com.chigr.tapatalk.TapaArray;
import com.chigr.tapatalk.TapaBaseValue;
import com.chigr.tapatalk.TapaBoolean;
import com.chigr.tapatalk.TapaInt;
import com.chigr.tapatalk.TapaString;
import com.chigr.tapatalk.TapaStruct;

/**
 * @author achigrin
 * @since 04.06.2016.
 */
public class TopicReadWorker extends BaseWorker {
    private static Set<String> paramSet = new HashSet<>();
    static {
        paramSet.add("from");
        paramSet.add("to");
        paramSet.add("unread");
    }
    @Override
    protected String processResponse(MethodResponse response) {
        TapaStruct struct = (TapaStruct)response.get(0);
        TopicDTO topic = new TopicDTO(
                struct.getString("topic_id"),
                struct.getString("topic_title"),
                null
        );
        topic.setForumId(struct.getString("forum_id"));

        readPosts(topic, (TapaArray)struct.getValue("posts"));

        return topicToHtml(topic);
    }

    private void readPosts(TopicDTO topic, TapaArray posts) {
        for(int i=0; i<posts.size(); i++){
            TapaStruct item = (TapaStruct) posts.get(i);
            topic.getPostList().add(readPost(item));
        }
    }

    private PostDTO readPost(TapaStruct item) {
        PostDTO post = new PostDTO(
                item.getString("post_id"),
                item.getString("post_title"),
                item.getString("post_content")
        );
        post.setAuthorId(item.getString("post_author_id"));
        post.setAuthorName(item.getString("post_author_name"));
        post.setAuthorAvatarURL(item.getString("icon_url"));

        post.setPostDate(item.getDate("post_time"));

        return post;
    }

    private String topicToHtml(TopicDTO topic) {
        StringBuilder html = new StringBuilder();

        for (PostDTO post : topic.getPostList()) {
            html.append(postToHtml(post));
        }

        return buildPage(getTitle(topic.getTitle()), html.toString());
    }

    private String postToHtml(PostDTO post) {
        StringBuilder html = new StringBuilder("<div class=\"post\">");
        html.append("<div class=\"postHead\">");
        html.append(
                String.format("<b>%s</b>&nbsp;<br>%s",
                        post.getAuthorName(), post.getPostDate().toString())
                );
        html.append("</div><div class=\"postContent\">");

        html.append(postContentToHtml(post.getDetails()));
        html.append("</div>");
        html.append("</div>");
        return html.toString();
    }

    private String postContentToHtml(String details) {
        String withBr = details.replace("\n", "<br>");
        return null;
    }

    @Override
    protected MethodCall createMethodCall(Properties props) {
        MethodCall call = new MethodCall("get_thread");
        String[] params = (String[]) props.get(RAW_PARAMS);
        call.add(new TapaString(params[1]));
        call.add(new TapaInt("0"));
        call.add(new TapaInt("49"));
        call.add(new TapaBoolean("1"));


        return call;
    }
}
