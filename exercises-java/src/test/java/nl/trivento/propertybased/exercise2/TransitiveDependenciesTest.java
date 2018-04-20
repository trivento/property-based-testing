package nl.trivento.propertybased.exercise2;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

@RunWith(JUnitQuickcheck.class)
public class TransitiveDependenciesTest {


    //  These pre-defined tests are some sanity checks to test our generator configuration.

    /**
     * These pre-defined tests are some sanity checks to test our generator configuration.
     * In this case we want to generate characters from A to Z only.
     */
    @Property
    public void generateCharFromAToZ(@InRange(minChar = 'A', maxChar = 'Z') char key) {
        assertTrue('A' <= key && key <= 'Z');
    }

    /**
     * Find out how to generate a Set of Characters from A To Z
     */
    @Property
    public void generateSetOfCharFromAToZ(Set<@InRange(minChar = 'A', maxChar = 'Z') Character> chars) {
        // Hint: It matters where exactly we place the annotation
        for (char key: chars) {
            assertTrue('A' <= key && key <= 'Z');
        }
    }

    @Property
    public void generateDependencyMapWithAToZ(Map<@InRange(minChar = 'A', maxChar = 'Z') Character, @Size(max = 3) Set<@InRange(minChar = 'A', maxChar = 'Z') Character>> dependencyMap) {
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

    @Property(trials = 1000)
    public void notIncludedProperty(
            @InRange(minChar = 'A', maxChar = 'Z') char key,
            @Size(max = 3) Map<@InRange(minChar = 'A', maxChar = 'Z') Character, @Size(max = 3) Set<@InRange(minChar = 'A', maxChar = 'Z') Character>> dependencyMap) {
        assumeFalse(dependencyMap.containsKey(key));
        assertTrue(TransitiveDependencies.findTransitiveDependencies(key, dependencyMap).isEmpty());
    }


    @Property(trials = 1000)
    public void directDependenciesProperty(
            @Size(max = 3) Map<@InRange(minChar = 'A', maxChar = 'Z') Character, @Size(max = 3) Set<@InRange(minChar = 'A', maxChar = 'Z') Character>> dependencyMap) {
        for (char key: dependencyMap.keySet()) {
            Set<Character> directDependencies = dependencyMap.get(key);
            Set<Character> result = TransitiveDependencies.findTransitiveDependencies(key, dependencyMap);
            assertThat(result, hasItems(directDependencies.toArray(new Character[0])));
        }
    }

    @Property(trials = 1000)
    public void indirectDependenciesProperty(
            @Size(min = 2, max = 20) Map<@InRange(minChar = 'A', maxChar = 'Z') Character, @Size(max = 10) Set<@InRange(minChar = 'A', maxChar = 'Z') Character>> dependencyMap) {
        assumeTrue(dependencyMap.size() >= 2);
        Iterator<Character> keyIt = dependencyMap.keySet().iterator();
        char k1 = keyIt.next();
        char k2 = keyIt.next();
        assumeTrue(dependencyMap.containsKey(k1) && dependencyMap.get(k1).contains(k2) && dependencyMap.containsKey(k2));
        Set<Character> directDependencies = dependencyMap.get(k1);
        Set<Character> indirectDependencies = dependencyMap.get(k2);
        Set<Character> minimumDependencySet = new HashSet<>();
        minimumDependencySet.addAll(directDependencies);
        minimumDependencySet.addAll(indirectDependencies);

        Set<Character> result = TransitiveDependencies.findTransitiveDependencies(k1, dependencyMap);
        assertThat(result, hasItems(minimumDependencySet.toArray(new Character[0])));
    }

}