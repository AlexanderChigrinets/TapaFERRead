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
public class TapaArray extends TapaBaseValue{
    private List<TapaBaseValue> data = new ArrayList<>();

    public void addItem(TapaBaseValue item) {
        data.add(item);
    }

    public int size() {
        return data.size();
    }

    public TapaBaseValue get(int index) {
        return data.get(index);
    }

    @Override
    protected String tagName() {
        return "array";
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        StringBuilder res = new StringBuilder();
        res.append(openTag("data"));
        for (TapaBaseValue item : data) {
            appendElement(res, "value", item.toXML(encoded));
        }
        res.append(closeTag("data"));
        return res.toString();
    }
}
