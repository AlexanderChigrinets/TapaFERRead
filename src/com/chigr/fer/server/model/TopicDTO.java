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
 * @since 04.06.2016.
 */
public class TopicDTO extends BaseDTO {

    private List<PostDTO> postList = new ArrayList<>();

    private String forumId;

    private int totalPosts;

    public TopicDTO(String id, String title, String details) {
        super(id, title, details);
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }
}
