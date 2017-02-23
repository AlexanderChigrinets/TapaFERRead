//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapatalk;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class TapaString extends TapaType {
    private String value;

    public TapaString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    protected String tagName() {
        return "string";
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        return value;
    }
}
