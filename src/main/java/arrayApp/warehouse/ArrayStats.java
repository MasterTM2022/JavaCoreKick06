package arrayApp.warehouse;

public final class ArrayStats {
    private final long id;
    private final int sum;
    private final int min;
    private final int max;
    private final double average;

    public ArrayStats(long id, int[] data) {
        this.id = id;
        if (data.length == 0) {
            this.sum = 0;
            this.min = 0;
            this.max = 0;
            this.average = 0.0;
        } else {
            int s = 0, mn = data[0], mx = data[0];
            for (int v : data) {
                s += v;
                if (v < mn) mn = v;
                if (v > mx) mx = v;
            }
            this.sum = s;
            this.min = mn;
            this.max = mx;
            this.average = (double) s / data.length;
        }
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
