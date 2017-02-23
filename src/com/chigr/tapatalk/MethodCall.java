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
 * @since 22.05.2016.
 */
public class MethodCall {
    public static final String XML_HEADER = "<?xml version=\"1.0\"?>";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private List<TapaBaseValue> params = new ArrayList<>();
    private String methodName;

    public MethodCall(String methodName) {
        this.methodName = methodName;
    }

    public void add(TapaBaseValue value){
        params.add(value);
    }

    public String toXML() {
        StringBuilder res = new StringBuilder(XML_HEADER);
        res.append(LINE_SEPARATOR);

        res.append("<methodCall><methodName>").append(methodName).append("</methodName>");
        res.append("<params>");
        for (TapaBaseValue value : params) {
            res.append("<param><value>").append(value.toXML(true)).append("</value></param>").append(LINE_SEPARATOR);
        }
        res.append("</params></methodCall>");

        return res.toString();
    }

    public String getMethodName() {
        return methodName;
    }
}
