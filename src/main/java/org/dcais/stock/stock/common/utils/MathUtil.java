package org.dcais.stock.stock.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtil {

    private static DecimalFormat priceFormat = new DecimalFormat("0.00");
    private static DecimalFormat rateFormat = new DecimalFormat("0.00000000");

    private static DecimalFormat priceFormatHalfUp = new DecimalFormat("0.00");
    private static DecimalFormat virtualCoinPriceHalfUp = new DecimalFormat("0");

    public final static BigDecimal ZERO = new BigDecimal("0");

    public final static BigDecimal MIN_AMOUNT = new BigDecimal("0.01");

    static {
        priceFormat.setRoundingMode(RoundingMode.DOWN);
        rateFormat.setRoundingMode(RoundingMode.DOWN);
        priceFormatHalfUp.setRoundingMode(RoundingMode.HALF_UP);
        virtualCoinPriceHalfUp.setRoundingMode(RoundingMode.HALF_UP);
    }


    public static Integer compareTo(BigDecimal one, BigDecimal two) {
        if ((one == null || one.compareTo(ZERO) == 0) && (two == null || two.compareTo(ZERO) == 0)) {
            return 0;
        } else if (one == null) {
            return -1;
        } else if (two == null) {
            return 1;
        }
        return one.compareTo(two);
    }

    public static String toPriceHalfUpFormat(BigDecimal bd) {
        if (bd == null) {
            return priceFormatHalfUp.format(ZERO);
        }
        return priceFormatHalfUp.format(bd);
    }

    public static String toVirtualPriceHalfUpFormat(BigDecimal bd) {
        if (bd == null) {
            return virtualCoinPriceHalfUp.format(ZERO);
        }
        return virtualCoinPriceHalfUp.format(bd);
    }

    public static String toPriceFormat(BigDecimal bd) {
        if (bd == null) {
            return priceFormat.format(ZERO);
        }
        return priceFormat.format(bd);
    }

    public static String toPriceFormatExistNull(BigDecimal bd) {
        if (bd == null) {
            return null;
        }
        return priceFormat.format(bd);
    }

    public static String toRateFormat(BigDecimal bd) {
        if (bd == null) {
            return rateFormat.format(ZERO);
        }
        return rateFormat.format(bd);
    }

    public static BigDecimal newBigDecimal(String str) {
        if (StringUtil.isNotBlank(str)) {
            return new BigDecimal(str);
        }
        return ZERO;
    }

    public static BigDecimal subtract(BigDecimal one, BigDecimal... bigs) {
        boolean isOneNull = false;
        if (one == null) {
            one = new BigDecimal("0");
            isOneNull = true;
        }
        BigDecimal all = one;
        for (BigDecimal big : bigs) {
            if (big == null || toRateFormat(ZERO).equals(toRateFormat(big))) {
                continue;
            }
            if (isOneNull) {
                throw new RuntimeException("减数为空，但是被减数不为空. " + toPriceFormat(big));
            }
            all = all.subtract(big);
        }
        return all;
    }


    public static BigDecimal add(BigDecimal... bigs) {
        BigDecimal all = new BigDecimal("0");
        for (BigDecimal big : bigs) {
            if (big == null) {
                continue;
            }
            all = all.add(big);
        }
        return all;
    }

    public static BigDecimal divide(BigDecimal one, BigDecimal two) {
        if (one == null) {
            return new BigDecimal("0");
        }
        if (two == null) {
            throw new RuntimeException("数据错误,被出数为空. 除数为:" + toPriceFormat(one));
        }
        return one.divide(two, 2, RoundingMode.DOWN);
    }


    public static Boolean checkRtZeroBigDecimal(String str) {
        try {
            if (new BigDecimal(str).compareTo(ZERO) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
    