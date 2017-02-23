//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapatalk;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class TapaDateTime extends TapaType {
    private String value;

    public TapaDateTime(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    protected String tagName() {
        return "dateTime.iso8601";
    }

    public Date getDate(){
        return parseDate(value);
    }


    @Override
    protected String toXMLInt(boolean encoded) {
        return value;
    }

    public static Date parseDate(String strDate) {
        String year = strDate.substring(0, 4);
        String month = strDate.substring(4, 6);
        String day = strDate.substring(6, 8);
        String timePart = strDate.substring(strDate.indexOf('T')+1);
        Character sign = timePart.charAt(8);
        String[] timeParts = timePart.substring(0, 8).split(":");

        TimeZone tz = getTimeZone(sign, timePart.substring(9));

        Calendar cl = Calendar.getInstance(tz);

        // Here we apply parsed values
        cl.set(
                Integer.parseInt(year),
                Integer.parseInt(month)-1,
                Integer.parseInt(day),
                Integer.parseInt(timeParts[0]),
                Integer.parseInt(timeParts[1]),
                Integer.parseInt(timeParts[2])
        );

        return cl.getTime();
    }

    private static TimeZone getTimeZone(Character sign, String timeOffset) {
        String[] parts = timeOffset.split(":");
        int offset =  (Integer.parseInt(parts[0]) * 60 +
                Integer.parseInt(parts[1])) * 60000;
        if (sign=='-'){
            offset = -offset;
        }

        String[] ids = TimeZone.getAvailableIDs(offset);
        return (ids.length==0) ? TimeZone.getDefault() : TimeZone.getTimeZone(ids[0]);
    }
}
