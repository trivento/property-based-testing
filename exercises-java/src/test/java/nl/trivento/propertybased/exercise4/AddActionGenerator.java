package nl.trivento.propertybased.exercise4;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.java.lang.StringGenerator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class AddActionGenerator extends Generator<Action> {

    private StringGenerator stringGenerator = new StringGenerator();

    public AddActionGenerator() {
        super(Action.class);
    }

    @Override
    public Action generate(SourceOfRandomness random, GenerationStatus status) {
        String element = stringGenerator.generate(random, status);
        return Action.createAddAction(element);
    }
}
