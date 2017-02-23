//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapatalk;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class TapaStruct extends TapaBaseValue {
    public static final int INITIAL_CAPACITY = 10;
    private List<TapaMember> members = new ArrayList(INITIAL_CAPACITY);

    public TapaStruct() {
    }

    public void addMember(String name, TapaBaseValue value) {
        members.add(new TapaMember(name, value));
    }

    public TapaBaseValue getValue(String name) {
        TapaMember member = getMember(name);
        return (null==member)?null:member.getValue();
    }

    public int getInt(String name) {
        TapaInt intValue = (TapaInt) getValue(name);
        return intValue.getValue();
    }

    public boolean getBoolean(String name) {
        TapaBoolean boolValue = (TapaBoolean) getValue(name);
        return boolValue.getValue();
    }

    public Date getDate(String name){
        TapaDateTime d = (TapaDateTime) getValue(name);
        return d.getDate();
    }


    public String getString(String name) {
        TapaString strValue = (TapaString) getValue(name);
        return strValue.getValue();
    }

    private TapaMember getMember(String name) {
        for (TapaMember mem : members) {
            if (mem.getName().equals(name)) {
                return mem;
            }
        }
        return null;
    }

    @Override
    protected String tagName() {
        return "struct";
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        StringBuilder res = new StringBuilder();
        for (TapaMember item : members) {
            appendElement(res, "name", item.getName());
            appendElement(res, "value", item.getValue().toXML(encoded));
        }
        return res.toString();
    }
}
