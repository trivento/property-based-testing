package nl.trivento.propertybased.exercise4;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitQuickcheck.class)
public class CircularStringBufferTest {


    // Example for how to specify to generate a type using a specific generator
    @Property public void addProperty(List<@From(AddActionGenerator.class) Action> addActions) {

    }

}