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

    // Implement your property based tests here
}
