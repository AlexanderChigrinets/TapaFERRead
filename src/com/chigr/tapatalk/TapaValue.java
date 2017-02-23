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
public class TapaValue {
    private TapaBaseValue value;

    public TapaValue() {
    }

    public TapaValue(TapaBaseValue value) {
        this.value = value;
    }

    public TapaBaseValue getValue() {
        return value;
    }

    public void setValue(TapaBaseValue value) {
        this.value = value;
    }
}
