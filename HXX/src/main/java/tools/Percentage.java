package tools;

import java.text.DecimalFormat;

public class Percentage {
    /***
     *
     * @param a 中奖数
     * @param b 没中奖数
     */
    public static String winPercentage(String a, String b) {
        double molecular = Double.parseDouble(a);
        double denominator = Double.parseDouble(a) + Double.parseDouble(b);
        if (denominator == 0) {
            return "0.00%";
        } else {
            double result = molecular / denominator;
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("0.00%");
            return df.format(result);
        }
    }


    /***
     *
     * @param m 问题某选项下注额
     * @param n 次问题总下注额
     */
    public static String mountPercentage(String m, String n) {
        double molecular = Double.parseDouble(m);
        double denominator = Double.parseDouble(n);
        if (denominator == 0) {
            return "0.00%";
        } else {
            double result = molecular / denominator;
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("0.00%");
            return df.format(result);
        }
    }
}
