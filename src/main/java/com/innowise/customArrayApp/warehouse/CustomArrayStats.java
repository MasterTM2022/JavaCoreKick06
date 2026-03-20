package com.innowise.customArrayApp.warehouse;

import com.innowise.customArrayApp.util.ArrayStatisticsUtils;

public final class CustomArrayStats {
    private final long id;
    private final int sum;
    private final int min;
    private final int max;
    private final double average;

    public CustomArrayStats(long id, int[] data) {
        this.id = id;
        this.sum = ArrayStatisticsUtils.sum(data);
        this.min = ArrayStatisticsUtils.min(data);
        this.max = ArrayStatisticsUtils.max(data);
        this.average = ArrayStatisticsUtils.average(data);
    }

    public long getId() { return id; }
    public int getSum() { return sum; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public double getAverage() { return average; }

    @Override
    public String toString() {
        return String.format("Stats{id=%d, sum=%d, min=%d, max=%d, avg=%.2f}",
                id, sum, min, max, average);
    }
}
