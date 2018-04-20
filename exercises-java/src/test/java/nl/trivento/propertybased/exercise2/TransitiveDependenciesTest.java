package nl.trivento.propertybased.exercise2;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(JUnitQuickcheck.class)
public class TransitiveDependenciesTest {


    //  These pre-defined tests are some sanity checks to test our generator configuration.

    /**
     * These pre-defined tests are some sanity checks to test our generator configuration.
     * In this case we want to generate characters from A to Z only.
     */
    @Property
    public void generateCharFromAToZ(char key) {
        // TODO see if the @InRange annotation can help us to generate only values that satisfy this property
        assertTrue('A' <= key && key <= 'Z');
    }

    /**
     * Find out how to generate a Set of Characters from A To Z
     */
    @Property
    public void generateSetOfCharFromAToZ(Set<Character> chars) {
        // TODO Again the InRange annotation can help us
        // Hint: It matters where exactly we place the annotation
        for (char key: chars) {
            assertTrue('A' <= key && key <= 'Z');
        }
    }

    @Property
    public void generateDependencyMapWithAToZ(Map<Character, Set<Character>> dependencyMap) {
        // TODO This is where we generate a complete dependency map
        for (char key: dependencyMap.keySet()) {
            assertTrue('A' <= key && key <= 'Z');
            for (char dependency: dependencyMap.get(key)){
                assertTrue('A' <= dependency && dependency <= 'Z');
            }
        }
    }



    /*
     * Some Tips:
     * Shrinking does not always seem to work too well
     * To find more simple counterExamples the @Size(max=4) annotation on the Map parameter can be used to generate smaller examples
     * Since this might make the chance of failures smaller the trials parameter in the @Property annotation can be used
     * to increase the number of times the test will be executed: e.g. @Property(trials = 1000)
     */


}