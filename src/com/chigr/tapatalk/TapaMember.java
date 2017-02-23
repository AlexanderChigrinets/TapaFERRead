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
public class TapaMember {
    private String name;
    private TapaBaseValue value;

    public TapaMember(String name, TapaBaseValue value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public TapaBaseValue getValue() {
        return value;
    }
}
