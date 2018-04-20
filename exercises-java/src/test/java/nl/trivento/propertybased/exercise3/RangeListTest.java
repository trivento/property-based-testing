package nl.trivento.propertybased.exercise3;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

@RunWith(JUnitQuickcheck.class)
public class RangeListTest {

    //==============================================EXAMPLE-BASED-TESTS=================================================

    @Test
    public void returnArrayListBasedOnTheRangeList() {
        RangeList list = new RangeList(1.0, 10.0, 1.0);
        ArrayList<Double> arrayList = new ArrayList<Double>();
        list.iterator().forEachRemaining(arrayList :: add);
        assertEquals(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), arrayList);
    }

    @Test
    public void containsShouldFindAllStepsInTheRangeList() {
        RangeList list = new RangeList(1.0, 10.0, 1.0);
        for(Double d : list){
            assertTrue("Rangelist should contain all elements, could not find: "+d, list.contains(d));
        }
    }

    @Test
    public void returnAnEmptyArrayListStartAndEndAreEqual() {
        RangeList list = new RangeList(1.0, 1.0, 1.0);
        assertTrue(list.isEmpty());
    }

    //==============================================PROPERTY-BASED-TESTS================================================


    @Property
    public void positiveStepContainsProperty(@InRange(minDouble =  -1000.0, maxDouble = 1000.0) double start,
                                             @InRange(minDouble =  -1000.0, maxDouble = 1000.0) double end,
                                             @InRange(minDouble =  0.01, maxDouble = 1000.0) double step){
        assumeTrue("if 'step' is positive 'start' must be smaller or equal to 'end'",  start <= end);
        RangeList rangeList = new RangeList(start, end, step);

        for(double d: rangeList){
            assertTrue("contains method should return true for all elements in the iterator, failed for: "+d,rangeList.contains(d));
        }
    }

    @Property
    public void negativeStepContainsProperty(@InRange(minDouble =  -1000.0, maxDouble = 1000.0) double start,
                                             @InRange(minDouble =  -1000.0, maxDouble = 1000.0) double end,
                                             @InRange(minDouble =  -1000.0, maxDouble = -0.01) double step){
        assumeTrue("if 'step' is negative 'start' must be larger or equal to 'end'",  start >= end);
        RangeList rangeList = new RangeList(start, end, step);

        for(double d: rangeList){
            assertTrue("contains method should return true for all elements in the iterator, failed for: "+d,rangeList.contains(d));
        }
    }

    @Property
    public void positiveStepSizeProperty(@InRange(minDouble =  -1000.0, maxDouble = 1000.0) double start,
                                         @InRange(minDouble =  -1000.0, maxDouble = 1000.0) double end,
                                         @InRange(minDouble =  0.01, maxDouble = 1000.0) double step){
        assumeTrue("if 'step' is positive 'start' must be smaller or equal to 'end'",  start <= end);
        RangeList rangeList = new RangeList(start, end, step);

        int size = 0;
        for(double d: rangeList){
            size = size + 1;
        }

        assertEquals(size, rangeList.size());
    }

    @Property
    public void negativeStepSizeProperty(@InRange(minDouble =  -1000.0, maxDouble = 1000.0) double start,
                                         @InRange(minDouble =  -1000.0, maxDouble = 1000.0) double end,
                                         @InRange(minDouble =  -1000.0, maxDouble = -0.01) double step){
        assumeTrue("if 'step' is negative 'start' must be larger or equal to 'end'",  start >= end);
        RangeList rangeList = new RangeList(start, end, step);

        int size = 0;
        for(double d: rangeList){
            size = size + 1;
        }

        assertEquals(size, rangeList.size());

    }
}
