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
public class TapaDouble extends TapaType {
    private double value;

    public TapaDouble(String strValue) {
        value = Double.parseDouble(strValue);
    }

    public double getValue() {
        return value;
    }

    @Override
    protected String tagName() {
        return "double";
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        return String.format(".2%f", value);
    }
}
