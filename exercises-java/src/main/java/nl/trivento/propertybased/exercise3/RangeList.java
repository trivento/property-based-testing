package nl.trivento.propertybased.exercise3;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

    // this solution is cheating, we simply use the iterator for everything. But the tests are green :)

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
        int result = 0;
        for (Double d : this) {
            result++;
        }
        return result;
    }

    @Override
    public Double get(int index) {
        if (index < 0) throw new NoSuchElementException("index " + index + " is negative");
        int currentIdx = 0;
        for (Double d : this) {
            if (currentIdx == index) return d;
            currentIdx++;
        }
        throw new NoSuchElementException("index " + index + " is larger than size");
    }

    @Override
    public boolean contains(Object o) {
        for (Double d : this) {
            if (d.equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            double current = start;
            @Override
            public boolean hasNext() {
                if (step > 0) return current < end;
                else return current > end;
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
