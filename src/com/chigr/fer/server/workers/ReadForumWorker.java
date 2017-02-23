//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.workers;

import java.util.List;
import java.util.Properties;

import com.chigr.fer.server.model.ForumContentDTO;
import com.chigr.fer.server.model.ThreadHeaderDTO;
import com.chigr.tapatalk.MethodCall;
import com.chigr.tapatalk.MethodResponse;
import com.chigr.tapatalk.TapaArray;
import com.chigr.tapatalk.TapaInt;
import com.chigr.tapatalk.TapaString;
import com.chigr.tapatalk.TapaStruct;

/**
 * Expected format of parameters
 * forum/forumId/page/pageNum
 * pageNum start from 1
 * @author achigrin
 * @since 25.05.2016.
 */
public class ReadForumWorker extends BaseWorker {
    public static final int TOPICS_ON_FORUM_PAGE = 20;
    @Override
    protected String processResponse(MethodResponse response) {
        TapaStruct data = (TapaStruct) response.get(0);

        TapaArray topics = (TapaArray) data.getValue("topics");
        ForumContentDTO forumData = new ForumContentDTO(
                data.getString("forum_id"),
                data.getString("forum_name"),
                null
        );
        forumData.setTopicCount(data.getInt("total_topic_num"));
        extractData(topics, forumData.getTopics());
        String body = makeBody(forumData.getTopics());
        return buildPage(getTitle(forumData.getTitle()), body);
    }

    private void extractData(TapaArray topics, List<ThreadHeaderDTO> topicList) {
        for (int i = 0; i < topics.size(); i++) {
            topicList.add(createTopicHeader((TapaStruct) topics.get(i)));
        }
    }

    private ThreadHeaderDTO createTopicHeader(TapaStruct topicData) {
        ThreadHeaderDTO res = new ThreadHeaderDTO(
                topicData.getString("topic_id"),
                topicData.getString("topic_title"),
                topicData.getString("short_content")
        );

        res.setForumId(topicData.getString("forum_id"));
        res.setAuthorID(topicData.getString("topic_author_id"));
        res.setAuthorName(topicData.getString("topic_author_name"));
        res.setClosed(topicData.getBoolean("is_closed"));
        res.setReplyCount(topicData.getInt("reply_number"));
        res.setViewCount(topicData.getInt("view_number"));
        return res;
    }

    private String makeBody(List<ThreadHeaderDTO> topics) {
        StringBuilder html = new StringBuilder();
        for (ThreadHeaderDTO topic:topics) {
            html.append(oneTopicHtml(topic));
        }
        return html.toString();
    }

    private String oneTopicHtml(ThreadHeaderDTO topicData) {
        String template="<div><div class=\"forumTopicName\"><a href=\"%s\">%s</a></div>"+
                "<div><span>Author: </span>%s<span>&nbsp;Posts: </span>%d</div></div>";
        return String.format(template, URLBuilder.getTopicURL(topicData.getId()),
                topicData.getTitle(), topicData.getAuthorName(), topicData.getReplyCount());
    }

    @Override
    protected MethodCall createMethodCall(Properties props) {
        MethodCall call = new MethodCall("get_topic");
        String[] params = (String[]) props.get(RAW_PARAMS);
        call.add(new TapaString(params[1]));
        int start = 0;
        if (params.length > 3) {
            int pageNum = Integer.parseInt(params[3]);
            start = (pageNum - 1)*TOPICS_ON_FORUM_PAGE;
        }
        call.add(new TapaInt(start));
        call.add(new TapaInt(start+TOPICS_ON_FORUM_PAGE-1));
        return call;
    }
}
