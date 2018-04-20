package nl.trivento.propertybased.exercise1;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnitQuickcheck.class)
public class SumTest {

    /**
     * @return The two numbers a and b added together
     */
    public static int sum(int a, int b) {
        return a + b;
    }

    @Test public void exampleBasedTest() {
        assertEquals(3, sum(1,2));
        assertEquals(123, sum(123,0));
        assertEquals(23, sum(123,-100));
    }

    @Property public void exampleProperty(int a, int b) {
        // This is an example of how to create a test with takes two randomly generated numbers as an argument
        // This test itself does not validate anything, however sometimes we just want to verify that a method terminated and no exceptions are thrown
        sum(a, b);
    }

    @Property public void rangeProperty(@InRange(minInt = 1, maxInt = 10) int a) {
        // Sometimes we need to generate number which are within a specific range, the @InRange annotation can help us with this
        assertTrue("The generated number should be in the specified range", 1 <= a && a <= 10);
    }


    @Property public void cummutativeProperty(int a, int b) {
        fail("Not yet implemented");
    }

    @Property public void associativeProperty(int a, int b, int c) {
        fail("Not yet implemented");
    }

    @Property public void identityProperty(int a) {
        fail("Not yet implemented");
    }

    @Property public void inverseProperty(int a) {
        fail("Not yet implemented");
    }

    @Property public void addingPositiveIntegersShouldBePositive(int a, int b) {
        fail("Not yet implemented");
    }
}