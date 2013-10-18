package org.urlMonitor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testFormatShortDate() {
        String result = DateUtil.formatShortDate(getSampleDate());

        Assert.assertEquals("12/12/12 12:12", result);
    }

    @Test
    public void testFormatTime() {
        String result = DateUtil.formatTime(getSampleDate());

        Assert.assertEquals("12:12:12", result);
    }

    @Test
    public void testGetMonthAndYear() {
        String result = DateUtil.getMonthAndYear(getSampleDate());

        Assert.assertEquals("Dec 2012", result);
    }

    @Test
    public void testGetHowLongAgoDescription() {
        String result =
                DateUtil.getHowLongAgoDescription(getSampleDate(),
                        getSampleDate2());

        Assert.assertEquals("3d 13h ago", result);
    }

    private Date getSampleDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            return sdf.parse("12/12/2012 12:12:12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date getSampleDate2() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            return sdf.parse("15/12/2012 13:12:12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
