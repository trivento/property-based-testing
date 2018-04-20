package nl.trivento.propertybased;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

@RunWith(JUnitQuickcheck.class)
public class Examples {

    // Documentation can be found on http://pholser.github.io/junit-quickcheck/site/0.8/


    // The parameters of the @Property annotation can be used to configure the number of times a test is executed.
    @Property(trials = 10) public void generatePrimitives(int i, String s, double d) {
        // The parameters will be automatically generated
//        System.out.println(String.format("%s , %s, %s", i, d, s));

    }

    @Property public void generateInRange(@InRange(minInt = 0, maxInt = 10) int i,
                                          @InRange(minDouble = 0.0, maxDouble = 10.0) double d) {
        // The @InRange annotation can be used to generate parameters within a certain range
//        System.out.println(String.format("%s , %s", i, d));
    }


    @Property public void generateCollection(Set<Integer> intSet) {
        // Collections can also be generated
//        System.out.println(String.format("%s", intSet));
    }

    @Property public void generateCollectionWithSizeBound(@Size(min = 2, max = 30) Set<@InRange(minInt = 0, maxInt = 100) Integer> intSet) {
        // The @Size annotation can be used to configure the size of a collection,
        // also the @InRange annotation can be used on type parameters
//        System.out.println(String.format("%s", intSet));
    }

    @Property public void withAssumptions(int i) {
        // assume can be used to verify that assumptions hold, if the assumption is false,
        // The test is discarded and a new set of parameters will be generated.
        // This can be used to ensure that assumptions hold which are hard to configure with annotations
        // The following import is required for this:
        // import static org.junit.Assume.*;
        assumeTrue(i >= 0);
//        System.out.println(String.format("%s", i));
    }
}