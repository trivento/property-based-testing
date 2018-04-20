package nl.trivento.propertybased.exercise3;

import java.util.*;

/**
 * An immutable, ordered list of doubles
 * The list is constructed using three parameters: `start`, `end` and `step`.
 * Each next element is computed by adding `step` end the previous element:
 * Example:
 * start = 0.0
 * end = 10.0
 * step = 2.5
 * The list will contain.
 * 0.0  2.5  5.0  7.5
 *
 */
public class RangeList extends AbstractList<Double> {

    private final double start;
    private final double end;
    private final double step;

    /**
     * @param start First element in the list
     * @param end End element of this list (exclusive)
     *           (`end` is never included in this list)
     * @param step The step, each subsequent element will be `step` larger than the previous element.
     *             Meaning
     *             this.get(0) + step == this.get(1)
     *             this.get(1) + step == this.get(2)
     *             and end forth.
     */
    public RangeList(double start, double end, double step) {
        if (step == 0.0) throw new IllegalArgumentException("Step may not be 0");
        if (step < 0.0 && start < end) throw new IllegalArgumentException("If 'step' is negative then 'start' must be larger than or equal end 'end'");
        if (step > 0.0 && start > end) throw new IllegalArgumentException("If 'step' is positive then 'end' must be larger than or equal end 'start'");
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public int size() {
        return (int) ((end - start) / step);
    }

    @Override
    public Double get(int index) {
        return start + (step * index);
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Double){
            double d = (Double) o;
            if(step > 0 && d < start) return false;
            if(step > 0 && d > end) return false;
            if(step < 0 && d > start) return false;
            if(step < 0 && d < end) return false;
            return ((d - start) % step == 0);

        }
        return false;
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            double current = start;

            @Override
            public boolean hasNext() {
                return current < end;
            }

            @Override
            public Double next() {
                double result = current;
                current += step;
                return result;
            }
        };
    }

    @Override
    public String toString() {
        return "RangeList{" +
                "start=" + start +
                ", end=" + end +
                ", step=" + step +
                '}';
    }
}
