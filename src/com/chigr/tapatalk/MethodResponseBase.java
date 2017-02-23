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
public abstract class MethodResponseBase {
    public String toDecodedXML(){
        return "";
    }
    public boolean isFailure() {
        return false;
    }
}
