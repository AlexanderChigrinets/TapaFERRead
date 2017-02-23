//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author achigrin
 * @since 28.05.2016.
 */
public class ForumContentDTO extends BaseDTO{
    private int topicCount;
    List<ThreadHeaderDTO> topics = new ArrayList<>();

    public ForumContentDTO(String id, String title, String details) {
        super(id, title, details);
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public List<ThreadHeaderDTO> getTopics() {
        return topics;
    }
}
