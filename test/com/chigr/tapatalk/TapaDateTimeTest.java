package com.chigr.tapatalk;

import java.util.Date;

import junit.framework.Assert;

/**
 * @author achigrin
 * @since 28.05.2016.
 */
public class TapaDateTimeTest {

    @org.junit.Test
    public void testGetValue() throws Exception {
        String testDate = "20041006T11:13:34+04:00";
        Date date = TapaDateTime.parseDate(testDate);
        Assert.assertNotNull(date);
    }
}