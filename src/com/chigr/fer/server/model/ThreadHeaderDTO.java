//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.fer.server.model;

/**
 * @author achigrin
 * @since 28.05.2016.
 */
public class ThreadHeaderDTO extends BaseDTO {
    /**
     * User ID of a topic starter
     * */
    private String authorID;
    /**
     * Name of a topic starter
     * */
    private String authorName;

    private boolean closed;

    private int replyCount;

    private int viewCount;

    private String forumId;


    public ThreadHeaderDTO(String id, String title, String details) {
        super(id, title, details);
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
