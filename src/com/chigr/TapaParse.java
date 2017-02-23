//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr;

import java.io.BufferedReader;
import java.io.FileReader;

import com.chigr.tapaparse.ResponseParser;
import com.chigr.tapatalk.MethodResponseBase;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class TapaParse {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("output.xml");
            BufferedReader br = new BufferedReader(reader);
            StringBuilder buff = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                buff.append(line);
                line = br.readLine();
            }
            ResponseParser parser = new ResponseParser();
            MethodResponseBase resp = parser.parse(buff.toString());
            System.out.println("Parsed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
