package com.example.testmodule.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <pre>
 Assert.assertEquals("314159.27", NumberUtils.format(val, 2));
 Assert.assertEquals("314159.265", NumberUtils.format(val, 3));

 Assert.assertEquals("314159.27", NumberUtils.format(val, 2, true));
 Assert.assertEquals("314159.26", NumberUtils.format(val, 2, false));

 Assert.assertEquals("00314159.27", NumberUtils.format(val, 8, 2, true));
 Assert.assertEquals("0000314159.27", NumberUtils.format(val, 10, 2, true));

 Assert.assertEquals("314,159.27", NumberUtils.format(val, true, 2));
 Assert.assertEquals("314159.27", NumberUtils.format(val, false, 2));

 Assert.assertEquals("314159.27", NumberUtils.format(val, false, 2, true));
 Assert.assertEquals("314159.26", NumberUtils.format(val, false, 2, false));

 Assert.assertEquals("314159.27", NumberUtils.format(val, false, 2, true));
 Assert.assertEquals("314159.265", NumberUtils.format(val, false, 3, false));
 * </pre>
 */
public final class NumberUtils {

    private static final ThreadLocal<DecimalFormat> DF_THREAD_LOCAL = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return (DecimalFormat) NumberFormat.getInstance();
        }
    };

    public static DecimalFormat getSafeDecimalFormat() {
        return DF_THREAD_LOCAL.get();
    }

    private NumberUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @return the format value
     */
    public static String format(float value, int fractionDigits) {
        return format(value, false, 1, fractionDigits, true);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @param isHalfUp       True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(float value, int fractionDigits, boolean isHalfUp) {
        return format(value, false, 1, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value            The value.
     * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
     * @param fractionDigits   The number of digits allowed in the fraction portion of value.
     * @param isHalfUp         True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(float value, int minIntegerDigits, int fractionDigits, boolean isHalfUp) {
        return format(value, false, minIntegerDigits, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param isGrouping     True to set grouping will be used in this format, false otherwise.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @return the format value
     */
    public static String format(float value, boolean isGrouping, int fractionDigits) {
        return format(value, isGrouping, 1, fractionDigits, true);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param isGrouping     True to set grouping will be used in this format, false otherwise.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @param isHalfUp       True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(float value, boolean isGrouping, int fractionDigits, boolean isHalfUp) {
        return format(value, isGrouping, 1, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value            The value.
     * @param isGrouping       True to set grouping will be used in this format, false otherwise.
     * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
     * @param fractionDigits   The number of digits allowed in the fraction portion of value.
     * @param isHalfUp         True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(float value, boolean isGrouping, int minIntegerDigits, int fractionDigits, boolean isHalfUp) {
        return format(float2Double(value), isGrouping, minIntegerDigits, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @return the format value
     */
    public static String format(double value, int fractionDigits) {
        return format(value, false, 1, fractionDigits, true);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @param isHalfUp       True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(double value, int fractionDigits, boolean isHalfUp) {
        return format(value, false, 1, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value            The value.
     * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
     * @param fractionDigits   The number of digits allowed in the fraction portion of value.
     * @param isHalfUp         True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(double value, int minIntegerDigits, int fractionDigits, boolean isHalfUp) {
        return format(value, false, minIntegerDigits, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param isGrouping     True to set grouping will be used in this format, false otherwise.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @return the format value
     */
    public static String format(double value, boolean isGrouping, int fractionDigits) {
        return format(value, isGrouping, 1, fractionDigits, true);
    }

    /**
     * Format the value.
     *
     * @param value          The value.
     * @param isGrouping     True to set grouping will be used in this format, false otherwise.
     * @param fractionDigits The number of digits allowed in the fraction portion of value.
     * @param isHalfUp       True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(double value, boolean isGrouping, int fractionDigits, boolean isHalfUp) {
        return format(value, isGrouping, 1, fractionDigits, isHalfUp);
    }

    /**
     * Format the value.
     *
     * @param value            The value.
     * @param isGrouping       True to set grouping will be used in this format, false otherwise.
     * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
     * @param fractionDigits   The number of digits allowed in the fraction portion of value.
     * @param isHalfUp         True to rounded towards the nearest neighbor.
     * @return the format value
     */
    public static String format(double value, boolean isGrouping, int minIntegerDigits, int fractionDigits, boolean isHalfUp) {
        DecimalFormat nf = getSafeDecimalFormat();
        nf.setGroupingUsed(isGrouping);
        nf.setRoundingMode(isHalfUp ? RoundingMode.HALF_UP : RoundingMode.DOWN);
        nf.setMinimumIntegerDigits(minIntegerDigits);
        nf.setMinimumFractionDigits(fractionDigits);
        nf.setMaximumFractionDigits(fractionDigits);
        return nf.format(value);
    }

    /**
     * Float to double.
     *
     * @param value The value.
     * @return the number of double
     */
    public static double float2Double(float value) {
        return new BigDecimal(String.valueOf(value)).doubleValue();
    }

    /**
     * Float to double.
     *
     * @param value The value.
     * @return the number of double
     */
    public static int str2Int(String value) {
        if(StringUtils.isEmpty(value))return 0;
        try {
            return new BigDecimal(value).intValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static String decimalFormat2(float value){
        try {
            return  new DecimalFormat("#.##").format(new BigDecimal(value));
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }
    }
    public static String decimalFormat2(String value){
        if(StringUtils.isEmpty(value))return "0";
        try {
            return  new DecimalFormat("#.##").format(new BigDecimal(value));
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }
    }
    public static String decimalFormat1(String value){
        if(StringUtils.isEmpty(value))return "0";
        try {
         return  new DecimalFormat("#.#").format(new BigDecimal(value));
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }
    }
    public static String decimalFormat1(float value){
        try {
            return  new DecimalFormat("#.#").format(new BigDecimal(value));
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }
    }

    public static String decimalFormat1(int value){
        try {
            return new DecimalFormat("#.#").format(new BigDecimal(value));
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }
    }


    public static float str2Float(String value){
        if(StringUtils.isEmpty(value))return 0;
        try {
            return  new BigDecimal(value).floatValue();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将2个浮点数进行乘法运算 默认保留2位小数
     *
     * @param number  double的字符串表示
     * @param number2 double的字符串表示
     * @param scale   保留几位小数
     * @return 想乘后的结果
     */
    public static String multiply(int number, int number2, int scale) {
        try {
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            return bd.multiply(bd2).setScale(scale, BigDecimal.ROUND_DOWN).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    /**
     * 将2个浮点数进行乘法运算 默认保留2位小数
     *
     * @param number  double的字符串表示
     * @param number2 double的字符串表示
     * @param scale   保留几位小数
     * @return 想乘后的结果
     */
    public static String multiply(float number, float number2, int scale) {
        try {
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            return bd.multiply(bd2).setScale(scale, BigDecimal.ROUND_DOWN).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static int multiply(float number, float number2) {
        try {
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            return bd.multiply(bd2).setScale(0, BigDecimal.ROUND_DOWN).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将2个浮点数进行乘法运算 默认保留2位小数
     *
     * @param number  double的字符串表示
     * @param number2 double的字符串表示
     * @param scale   保留几位小数
     * @return 想乘后的结果
     */
    public static String multiply(String number, String number2, int scale) {
        BigDecimal bd4 = null;
        try {
            if(StringUtils.isEmpty(number))number="0";
            if(StringUtils.isEmpty(number2))number2="0";
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            return bd.multiply(bd2).setScale(scale, BigDecimal.ROUND_DOWN).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }


    public static float multiplyF(float number, float number2) {
        BigDecimal bd4 = null;
        try {
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            return bd.multiply(bd2).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }

    }

    /**
     * 进行浮点除法运算
     *
     * @param number  double的字符串表示
     * @param number2 double的字符串表示
     * @param scale   保留几位小数
     * @return
     */
    public static String getDivFloat(String number, String number2, int scale) {
        BigDecimal bd4 = null;
        try {
            if(StringUtils.isEmpty(number))number="0";
            if(StringUtils.isEmpty(number2))number2="0";
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            bd4 = bd.divide(bd2, scale, BigDecimal.ROUND_DOWN).setScale(scale, BigDecimal.ROUND_DOWN);
            return bd4.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }


    /**
     * 进行浮点除法运算
     *
     * @param number  double的字符串表示
     * @param number2 double的字符串表示
     * @param scale   保留几位小数
     * @return
     */
    public static String getDivFloat(int number, int number2, int scale) {
        BigDecimal bd4 = null;
        try {
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            bd4 = bd.divide(bd2, scale, BigDecimal.ROUND_DOWN).setScale(scale, BigDecimal.ROUND_DOWN);
            return bd4.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }
    /**
     * 减法
     * @return
     */
    public static float subtractF(float number, float number2) {
        BigDecimal bd4 = null;
        try {
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            BigDecimal bd3 = bd.subtract(bd2);
            return bd3.floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }
    }



    /**
     * 减法
     * @return
     */
    public static String subtractStr(String number, String number2, int scale) {
        BigDecimal bd4 = null;
        try {
            if(StringUtils.isEmpty(number))number="0";
            if(StringUtils.isEmpty(number2))number2="0";
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            BigDecimal bd3 = bd.subtract(bd2);
            bd4 = bd3.setScale(scale, BigDecimal.ROUND_DOWN);
            return bd4.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    /**
     * 减法
     * @return
     */
    public static float subtractFloat(String number, String number2, int scale) {
        BigDecimal bd4 = null;
        try {
            if(StringUtils.isEmpty(number))number="0";
            if(StringUtils.isEmpty(number2))number2="0";
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            BigDecimal bd3 = bd.subtract(bd2);
            bd4 = bd3.setScale(scale, BigDecimal.ROUND_DOWN);
            return bd4.floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 加法
     *
     * @param number
     * @param number2
     * @param scale
     * @return
     */
    public static String getAddString(String number, String number2, int scale) {
        BigDecimal bd4 = null;
        try {
            if(StringUtils.isEmpty(number))number="0";
            if(StringUtils.isEmpty(number2))number2="0";
            BigDecimal bd = new BigDecimal(number);
            BigDecimal bd2 = new BigDecimal(number2);
            BigDecimal bd3 = bd.add(bd2);
            bd4 = bd3.setScale(scale, BigDecimal.ROUND_DOWN);
            return bd4.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }


}
