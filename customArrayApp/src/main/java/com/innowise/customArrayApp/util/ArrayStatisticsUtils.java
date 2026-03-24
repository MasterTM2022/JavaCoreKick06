package com.innowise.customArrayApp.util;


public class ArrayStatisticsUtils {
    public ArrayStatisticsUtils() {} // utility class

    public static int sum(int[] data) {
        if (data.length == 0) return 0;
        int s = 0;
        for (int v : data) s += v;
        return s;
    }

    public static int min(int[] data) {
        if (data.length == 0) return 0;
        int m = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] < m) m = data[i];
        }
        return m;
    }

    public static int max(int[] data) {
        if (data.length == 0) return 0;
        int m = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] > m) m = data[i];
        }
        return m;
    }

    public static double average(int[] data) {
        return data.length == 0 ? 0.0 : (double) sum(data) / data.length;
    }

}
