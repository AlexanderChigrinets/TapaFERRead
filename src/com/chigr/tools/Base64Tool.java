//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tools;

import javax.xml.bind.DatatypeConverter;

/**
 * @author achigrin
 * @since 17.05.2016.
 */
public class Base64Tool {

    private Base64Tool(){
    }

    public static String encode(String value) throws Exception {
        return  DatatypeConverter.printBase64Binary
                (value.getBytes("utf-8")); // use "utf-8" if java 6
    }

    public static String decode(String value) throws Exception {
        byte[] decodedValue = DatatypeConverter.parseBase64Binary(value);
        return new String(decodedValue, "utf-8"); // use "utf-8" if java 6
    }

    public static void main(String[] args) {
        //String t = "0LrRg9C30L3QtdGH0LjQug==";
        String t = "0LrRg9C30L3QtdGH0LjQug==";
        String res = null;
        try {
            res = Base64Tool.decode(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Decoded "+res);
        try {
            res="кузнечик";
            System.out.println("Encode back " + Base64Tool.encode(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
