package egestats;

//** ** Created by DeveloperHacker ** **//
//* https://github.com/DeveloperHacker *//

import java.util.Arrays;
import java.util.List;

public class Histogram {

    private final int[] columns;
    public final float lower;
    public final float upper;
    public final float step;
    public final int qColumns;
    public final int elements;

    public Histogram(List<Float> data, float lower, float upper, int qColumns) {
        if (data.size() == 0) throw new IllegalArgumentException("Histogram: empty input data");
        if (lower >= upper) throw new IllegalArgumentException("Gits: Wrong lower and upper values");
        this.columns = new int[qColumns];
        this.lower = lower;
        this.upper = upper;
        this.qColumns = qColumns;
        this.step = (upper - lower) / qColumns;
        int elements = 0;
        for (Float value : data) {
            int index = Math.round((value - lower) / step);
            if (0 <= index && index < qColumns) {
                ++(this.columns[index]);
                ++elements;
            }
        }
        this.elements = elements;
    }

    public Histogram(List<Float> data, int qColumns) {
        if (data.size() == 0) throw new IllegalArgumentException("Histogram: empty input data");
        this.columns = new int[qColumns];
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        for (Float value: data) {
            if (value < min) min = value;
            if (value > max) max = value;
        }
        this.lower = min;
        this.upper = max;
        this.qColumns = qColumns;
        this.step = (upper - lower) / qColumns;
        int elements = 0;
        for (Float value : data) {
            int index = Math.round((value - lower) / step);
            if (0 <= index && index < qColumns) {
                ++(this.columns[index]);
                ++elements;
            }
        }
        this.elements = elements;
    }

    public String absolute() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < qColumns; ++i) {
            float left = i * step + lower;
            float right = left + step;
            builder.append(String.format("(%.2f;%.2f)=%d ", left, right, columns[i]));
        }
        return builder.toString();
    }

    public String relative() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < qColumns; ++i) {
            float left = i * step + lower;
            float right = left + step;
            builder.append(String.format("(%.2f;%.2f)=%.0f ", left, right, (float) (columns[i]) / elements));
        }
        return builder.toString();
    }

    public static float mean(List<Float> data) {
        float sum = 0;
        for (Float value : data) sum += value;
        return sum / data.size();
    }

    public int[] columns() {
        return Arrays.copyOf(columns, qColumns);
    }
}
