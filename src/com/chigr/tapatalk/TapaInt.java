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
public class TapaInt extends TapaType {
    private int value;

    public TapaInt(String strValue) {
        value = Integer.parseInt(strValue);
    }

    public TapaInt(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    @Override
    protected String tagName() {
        return "int";
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        return Integer.toString(value);
    }
}
