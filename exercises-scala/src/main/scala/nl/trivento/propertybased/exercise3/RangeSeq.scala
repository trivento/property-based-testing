package nl.trivento.propertybased.exercise3

import scala.collection.immutable.Seq

/**
  * An immutable, ordered seq of doubles
  * The seq is constructed using three parameters: `start`, `end` and `step`.
  * Each next element is computed by adding `step` end the previous element:
  * Example:
  * start = 0.0
  * end = 10.0
  * step = 2.5
  * The list will contain.
  * 0.0  2.5  5.0  7.5
  *
  * @param start First element in the list
  * @param end   End element of this list (exclusive)
  *              (`end` is never included in this list)
  * @param step  The step, each subsequent element will be `step` larger than the previous element.
  *             Meaning
  *             this.apply(0) + step == this.apply(1)
  *             this.apply(1) + step == this.apply(2)
  *             and end forth.
  */
case class RangeSeq(start: Double, end: Double, step: Double) extends Seq[Double]{
  require(step != 0.0, "Step may not be zero")
  require(step < 0.0 || start <= end, "If step is positive, then start must be smaller than or equal to end")
  require(step > 0.0 || start >= end, "If step is negative, then end must be smaller than or equal to start")

  override def toString(): String = s"RangeSeq($start, $end, $step)"

  override def length: Int = ((end - start) / step).toInt

  override def apply(index: Int): Double = start + (step * index)

  override def iterator: Iterator[Double] = new Iterator[Double] {
    private var current = start

    override def hasNext: Boolean = current < end

    override def next(): Double = {
      val result = current
      current += step
      result
    }
  }

  private def isWithinBoundaries(elem: Double): Boolean =
  (step > 0.0 && start <= elem && elem <= last ) ||
    (step < 0.0 &&  last <= elem && elem <= start)

  override def contains[A1 >: Double](elem: A1): Boolean = elem match {
    case d: Double => isWithinBoundaries(d) && (d - start) % step == 0.0
    case _         => false
  }
}
