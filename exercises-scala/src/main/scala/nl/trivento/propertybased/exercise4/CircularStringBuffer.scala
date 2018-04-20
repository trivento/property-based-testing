package nl.trivento.propertybased.exercise4


/**
  * A Circular buffer behaves very much like a First-In-First-Out queue but
  * it has a bounded capacity. It is always possible to add an element,
  * however if the buffer is full the oldest element will be overwritten.
  *
  * The only way to retrieve elements from the buffer is by removing them.
  * In this case the oldest element (which has not yet been overwritten) is
  * returned and removed.
  */
class CircularStringBuffer(capacity: Int) {
  require(capacity > 0, s"Capacity must be larger than 0, got $capacity")

  private val array = new Array[String](capacity)

  /** Position of the oldest element */
  private var readPosition = 0

  /** Position where the next element should be written */
  private var writePosition = 0

  private def incrementWritePosition(): Unit = {
    writePosition += 1
    if (writePosition >= capacity) writePosition = 0
  }

  private def incrementReadPosition(): Unit = {
    readPosition += 1
    if (readPosition >= capacity) readPosition = 0
  }

  def isEmpty: Boolean = array(readPosition) == null

  /**
    * Add the given element at the end of the circular buffer.
    * If the buffer is full, the oldest element will be overwritten.
    * @param elem The element to add
    */
  def add(elem: String): Unit = {
    if (readPosition == writePosition && !isEmpty) {
      incrementReadPosition()
    }
    array(writePosition) = elem
    incrementWritePosition()
  }

  /**
    * Returns and removes the oldest element in the buffer
    * @throws NoSuchElementException when this buffer is empty
    */
  def remove(): String = {
    if (isEmpty) throw new NoSuchElementException("Buffer is empty")
    val result = array(readPosition)
    array(readPosition) = null
    incrementReadPosition()
    result
  }


  def size: Int = if (isEmpty) 0
  else if (writePosition == readPosition) capacity
  else if (writePosition < readPosition) capacity - readPosition + writePosition
  else writePosition - readPosition

  /**
    * @return A list containing all element in this buffer
    */
  def toList: List[String] = {
    val builder = List.newBuilder[String]

    val indexes = readPosition.until(capacity) ++ 0.until(readPosition)
    for (idx <- indexes.take(size)){
      builder += array(idx)
    }
    builder.result()
  }

  override def toString =
    s"CircularStringBuffer(capacity=$capacity, readPosition=$readPosition, writePosition=$writePosition, array=${array.mkString("[",", ", "]")})"
}
