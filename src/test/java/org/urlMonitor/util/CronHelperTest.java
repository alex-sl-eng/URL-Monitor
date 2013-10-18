package org.urlMonitor.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public class CronHelperTest {

    @Test
    public void testGetTypeFromExpressionThirtySeconds() {
        CronHelper.CronType result =
                CronHelper
                        .getTypeFromExpression(CronHelper.CronType.THRITY_SECONDS
                                .getExpression());
        Assert.assertEquals(CronHelper.CronType.THRITY_SECONDS, result);
    }

    @Test
    public void testGetTypeFromExpressionOneMinutes() {
        CronHelper.CronType result =
            CronHelper
                .getTypeFromExpression(CronHelper.CronType.ONE_MINUTE
                    .getExpression());
        Assert.assertEquals(CronHelper.CronType.ONE_MINUTE, result);
    }

    @Test
    public void testGetTypeFromExpressionFiveMinutes() {
        CronHelper.CronType result =
            CronHelper
                .getTypeFromExpression(CronHelper.CronType.FIVE_MINUTES
                    .getExpression());
        Assert.assertEquals(CronHelper.CronType.FIVE_MINUTES, result);
    }

    @Test
    public void testGetTypeFromExpressionTenMinutes() {
        CronHelper.CronType result =
            CronHelper
                .getTypeFromExpression(CronHelper.CronType.TEN_MINUTES
                    .getExpression());
        Assert.assertEquals(CronHelper.CronType.TEN_MINUTES, result);
    }

    @Test
    public void testGetTypeFromExpressionFifteenMinutes() {
        CronHelper.CronType result =
            CronHelper
                .getTypeFromExpression(CronHelper.CronType.FIFTEEN_MINUTES
                    .getExpression());
        Assert.assertEquals(CronHelper.CronType.FIFTEEN_MINUTES, result);
    }

    @Test
    public void testGetTypeFromExpressionDefaultValue() {
        CronHelper.CronType result =
            CronHelper
                .getTypeFromExpression("invalid expression");
        Assert.assertEquals(CronHelper.CronType.FIVE_MINUTES, result);
    }
}
