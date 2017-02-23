//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapatalk;

/**
 * Super class for all TapaTalk constructs: Struct, Array, Value
 * @author achigrin
 * @since 21.05.2016.
 */
public abstract class TapaBaseValue {
    protected abstract String tagName();

    public String toXML(boolean encoded){
        StringBuilder res = new StringBuilder();
        res.append(openTag(tagName())).append(toXMLInt(encoded)).append(closeTag(tagName()));
        return res.toString();
    }

    protected abstract String toXMLInt(boolean encoded);

    protected static String openTag(String name) {
        return "<"+name+">";
    }
    protected static String closeTag(String name) {
        return "</"+name+">";
    }

    protected static StringBuilder appendElement(StringBuilder buf, String tag, String value) {
        buf.append(openTag(tag)).append(value).append(closeTag(tag));
        return buf;
    }
}
