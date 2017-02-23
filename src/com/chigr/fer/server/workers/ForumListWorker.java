//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.workers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.chigr.fer.server.model.ForumDTO;
import com.chigr.tapatalk.MethodCall;
import com.chigr.tapatalk.MethodResponse;
import com.chigr.tapatalk.TapaArray;
import com.chigr.tapatalk.TapaBaseValue;
import com.chigr.tapatalk.TapaBoolean;
import com.chigr.tapatalk.TapaStruct;

/**
 * @author achigrin
 * @since 23.05.2016.
 */
public class ForumListWorker extends BaseWorker {
    @Override
    protected String processResponse(MethodResponse response) {

        TapaArray array = (TapaArray)response.get(0);

        List<ForumDTO> forums = new ArrayList<>();

        extractData(array, forums);
        // Here we create page with the list of forums.
        StringBuilder html = new StringBuilder();

        produceHtml(html, forums);

        return buildPage(getTitle("List of Forums"), html.toString());
    }

    private void extractData(TapaArray array, List<ForumDTO> forumList) {
        for (int i = 0; i < array.size(); i++) {
            TapaStruct item = (TapaStruct) array.get(i);
            ForumDTO forum = createForumDTO(item);
            forumList.add(forum);
            TapaBaseValue children = item.getValue("child");
            if (null != children) {
                extractData((TapaArray)children, forum.getChildren());
            }
        }
    }

    private ForumDTO createForumDTO(TapaStruct item) {
        return new ForumDTO(
                item.getString("forum_id"),
                item.getString("forum_name"),
                null
        );
    }

    private void produceHtml(StringBuilder html, List<ForumDTO> forums) {
        html.append("<ul>");
        for (ForumDTO forum: forums) {
            forumToHtml(html, forum);
            List<ForumDTO> children = forum.getChildren();
            if (!children.isEmpty()) {
                produceHtml(html, children);
            }
        }
        html.append("</ul>");
    }

    /**
     * <li><a href="http://fer.client.go/forum/ID">Forum name</a></li>
     * */
    private void forumToHtml(StringBuilder html, ForumDTO item) {
        html.append(
                String.format("<li><a href=\"%s\">%s</a></li>",
                        URLBuilder.getForumURL(item.getId()),
                        item.getTitle())
        );
    }

    @Override
    protected MethodCall createMethodCall(Properties props) {
        MethodCall call = new MethodCall("get_forum");
        call.add(new TapaBoolean("1"));
        return call;
    }
}
