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
public class TapaFault {
    private TapaStruct struct;

    public TapaFault(TapaStruct struct) {
        this.struct = struct;
    }

    public TapaStruct getStruct() {
        return struct;
    }
}
