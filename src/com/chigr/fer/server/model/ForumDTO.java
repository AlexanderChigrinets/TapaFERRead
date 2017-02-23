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
public class ForumDTO extends BaseDTO{
    private final static int INITIAL_CHILDREN_COUNT = 5;
    private List<ForumDTO> children = new ArrayList<>(INITIAL_CHILDREN_COUNT);

    public ForumDTO(String id, String title, String details) {
        super(id, title, details);
    }

    public List<ForumDTO> getChildren() {
        return children;
    }
}
