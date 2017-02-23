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
public class MethodResponseFault extends MethodResponseBase {
    private TapaFault error;

    public MethodResponseFault(TapaFault error) {
        this.error = error;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    public TapaFault getError() {
        return error;
    }
}
