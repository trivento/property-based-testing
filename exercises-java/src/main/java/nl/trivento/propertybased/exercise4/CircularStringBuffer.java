package nl.trivento.propertybased.exercise4;

import java.util.*;


/**
 * A Circular buffer behaves very much like a First-In-First-Out queue but
 * it has a bounded capacity. It is always possible to add an element,
 * however if the buffer is full the oldest element will be overwritten.
 *
 * The only way to retrieve elements from the buffer is by removing them.
 * In this case the oldest element (which has not yet been overwritten) is
 * returned and removed.
 */
public class CircularStringBuffer {

    private final int capacity;
    private final String[] array;


    /** Position of the oldest element */
    private int readPosition = 0;

    /** Position where the next element should be written */
    private int writePosition = 0;

    public CircularStringBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be larger than 0, got " + capacity);
        }
        this.capacity = capacity;
        this.array = new String[capacity];
    }

    private void incrementWritePosition() {
        writePosition += 1;
        if (writePosition >= capacity) {
            writePosition = 0;
        }
    }

    private void incrementReadPosition() {
        readPosition += 1;
        if (readPosition >= capacity) {
            readPosition = 0;
        }
    }

    public boolean isEmpty() {
        return array[readPosition] == null;
    }


    /**
     * Add the given element at the end of the circular buffer.
     * If the buffer is full, the oldest element will be overwritten.
     * @param elem The element to add
     */
    public void add(String elem) {
        Objects.requireNonNull(elem);
        if (readPosition == writePosition && !isEmpty() ) {
            incrementReadPosition();
        }
        array[writePosition] = elem;
        incrementWritePosition();
    }

    /**
     * @return the oldest element in the buffer
     * @throws NoSuchElementException when this buffer is empty
     */
    public String remove() {
        if (isEmpty()) throw new NoSuchElementException("Buffer is empty");
        String result = array[readPosition];
        array[readPosition] = null;
        incrementReadPosition();
        return result;
    }

    public int size() {
        if (isEmpty()) return 0;
        else if (writePosition == readPosition) return capacity;
        else if (writePosition < readPosition) return capacity - readPosition + writePosition;
        else return writePosition - readPosition;
    }

    public List<String> toList() {
        ArrayList<String> allElements = new ArrayList<>();
        allElements.addAll(Arrays.asList(array).subList(readPosition, capacity));
        allElements.addAll(Arrays.asList(array).subList(0, readPosition));
        return allElements.subList(0, size());
    }


    @Override
    public String toString() {
        return "CircularStringBuffer{" +
                "capacity=" + capacity +
                ", array=" + Arrays.toString(array) +
                ", readPosition=" + readPosition +
                ", writePosition=" + writePosition +
                '}';
    }
}
