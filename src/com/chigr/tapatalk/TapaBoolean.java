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
public class TapaBoolean extends TapaType {
    private int value;

    public TapaBoolean(String strValue) {
        value = Integer.parseInt(strValue);
    }

    public boolean getValue() {
        return value==1;
    }

    @Override
    protected String tagName() {
        return "boolean";
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        return Integer.toString(value);
    }
}
