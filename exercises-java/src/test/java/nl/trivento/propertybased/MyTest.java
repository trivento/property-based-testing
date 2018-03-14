package nl.trivento.propertybased;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.Property;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class MyTest {
    @Property public void allIntegersShouldBePositive(int i) throws Exception {
        assertTrue(i >= 0);
    }
}