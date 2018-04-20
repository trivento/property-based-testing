package nl.trivento.propertybased.exercise4;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;


@RunWith(JUnitQuickcheck.class)
public class CircularStringBufferTest {


    // Example for how to specify to generate a type using a specific generator
    @Property public void addProperty(List<@From(AddActionGenerator.class) Action> addActions) {

    }




    @Property
    public void boundedByCapacity(@InRange(minInt = 1, maxInt = 100) int capacity,
                                  List<@From(AddActionGenerator.class) Action> addActions){
        CircularStringBuffer buffer = new CircularStringBuffer(capacity);

        for (Action add: addActions) {
            buffer.add(add.getElement());
        }
        assertTrue("Buffer size should be smaller than capacity",
                buffer.size() <= capacity);
    }


    @Property
    public void elementOrderProperty(@InRange(minInt = 1, maxInt = 100) int capacity,
                                     List<@From(AddActionGenerator.class) Action> addActions){
        CircularStringBuffer buffer = new CircularStringBuffer(capacity);

        for (Action add: addActions) {
            buffer.add(add.getElement());
        }

        List<Action> lastElements = addActions.subList(Math.max(0, addActions.size() - capacity), addActions.size());

        for (Action add: lastElements) {
            assertEquals(add.getElement(), buffer.remove());
        }

        assertTrue("buffer should be empty", buffer.isEmpty());
    }


    @Property
    public void correctSizeProperty(@InRange(minInt = 1, maxInt = 100) int capacity,
                                    List<@From(ActionGenerator.class) Action> actions){
        CircularStringBuffer buffer = new CircularStringBuffer(capacity);

        int currentSize = 0;

        for (Action action: actions) {
            if (action.isAdd()) {
                buffer.add(action.getElement());
                if (currentSize < capacity) currentSize++;
            } else if (currentSize == 0) {
                boolean exceptionThrown = false;
                try {
                    buffer.remove();
                } catch (NoSuchElementException e) {
                    exceptionThrown = true;
                }
                assertTrue(exceptionThrown);
            } else {
                buffer.remove();
                currentSize--;
            }
        }
        assertEquals("Size should be " + currentSize + " was " + buffer.size(),
                currentSize, buffer.size());
    }

    @Property
    public void behaveLikeFifoQueue(@InRange(minInt = 1, maxInt = 100) int capacity,
                                    List<@From(ActionGenerator.class) Action> actions){
        CircularStringBuffer buffer = new CircularStringBuffer(capacity);
        Queue<String> queue = new LinkedList<>();

        for (Action action: actions) {
            if (action.isAdd()) {
                if (buffer.size() == capacity) {
                    queue.remove();
                }
                queue.offer(action.getElement());
                buffer.add(action.getElement());
            } else if (queue.isEmpty()) {
                boolean exceptionThrown = false;
                try {
                    buffer.remove();
                } catch (NoSuchElementException e) {
                    exceptionThrown = true;
                }
                assertTrue("Exception should be thrown when empty", exceptionThrown);
            } else {
                String expected = queue.remove();
                assertEquals(expected, buffer.remove());
            }
        }
        assertEquals(queue, buffer.toList());
    }

}