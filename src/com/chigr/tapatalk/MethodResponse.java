//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapatalk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class MethodResponse extends MethodResponseBase{
    private List<TapaBaseValue> params = new ArrayList<>(5);

    public void add(TapaBaseValue value) {
        params.add(value);
    }

    public TapaBaseValue get(int index) {
        return params.get(index);
    }

    public int size() {
        return params.size();
    }

    @Override
    public String toDecodedXML() {
        StringBuilder res = new StringBuilder(MethodCall.XML_HEADER);
        res.append(MethodCall.LINE_SEPARATOR);

        res.append("<methodResponse><params>");
        for (TapaBaseValue value : params) {
            res.append("<param><value>").append(value.toXML(false)).append("</value></param>")
                    .append(MethodCall.LINE_SEPARATOR);
        }
        res.append("</params></methodResponse>");

        return res.toString();
    }
}
