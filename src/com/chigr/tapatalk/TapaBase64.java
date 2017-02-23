//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapatalk;

import com.chigr.tools.Base64Tool;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class TapaBase64 extends TapaString {
    public TapaBase64(String value) throws Exception {
        super(Base64Tool.decode(value));
    }
    public TapaBase64(String value, boolean decode) throws Exception {
        super(decode?Base64Tool.decode(value):value);
    }

    @Override
    protected String toXMLInt(boolean encoded) {
        try {
            return encoded?Base64Tool.encode(getValue()):getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "!!error!!";
    }

    @Override
    protected String tagName() {
        return "base64";
    }
}
