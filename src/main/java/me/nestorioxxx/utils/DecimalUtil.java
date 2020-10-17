package me.nestorioxxx.utils;

import java.text.DecimalFormat;

public class DecimalUtil {
    public static String round(double toRound) {
        DecimalFormat df = new DecimalFormat("##.##");
        return df.format(toRound);
    }
}
