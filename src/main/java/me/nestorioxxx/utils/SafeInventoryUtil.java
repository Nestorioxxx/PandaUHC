package me.nestorioxxx.utils;

public class SafeInventoryUtil {
    public static int safestInventorySize(int i) {
        if (i % 9 == 0) {
            return i;
        } else {
            boolean found = false;
            int b = 1;
            while (!found) {
                if ((i + b) % 9 == 0) {
                    return i + b;
                } else {
                    b++;
                }
            }
        }
        return 9;
    }
}
