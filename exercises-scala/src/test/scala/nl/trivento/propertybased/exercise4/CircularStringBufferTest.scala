package nl.trivento.propertybased.exercise4

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

class CircularStringBufferTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 100)

  // To test the circular buffer, we can generate actions to execute as test data

  sealed trait Action
  case object Remove extends Action
  case class Add(value: String) extends Action

  /**
    * Capacity generator
    * Does it make sense to generate huge numbers? Or can we generate small numbers only?
    *
    * Since the capacity must be positive, it helps to add a .suchThat(n => n >= 1)
    * This ensures that the shrinking mechanism which finds a small counterexample will not try executing the test
    * with capacity 0
    */
  def genCapacity: Gen[Int] = Gen.chooseNum(1, 100).suchThat(n => n >= 1)

  // Generator for Add
  def genAdd: Gen[Add] = for {
    str <- Gen.alphaNumStr
  } yield Add(str)

  /**
    * Generator for add actions.
    * Does it make sense to generate an empty list?
    */
  def genAddActions: Gen[List[Add]] = Gen.nonEmptyListOf(genAdd)

  /**
    * Generator for either an add or a remove action.
    */
  def genAction: Gen[Action] = Gen.oneOf(
    Gen.const(Remove),
    genAdd
  )

  /**
    * Generator for a list with any sequence of actions
    */
  def genActions: Gen[List[Action]] = Gen.nonEmptyListOf(genAction)
  behavior of "circular string buffer"

  // Add your tests here

  it should "never contain more elements than the capacity" in {
    forAll(genCapacity, genAddActions){ (capacity, addActions) =>
      val buffer = new CircularStringBuffer(capacity)

      addActions.foreach { add =>
        buffer.add(add.value)
      }

      buffer.size should be <= capacity

    }
  }



  it should "return elements in the order they were added" in {
    forAll(genCapacity, genAddActions){ (capacity, addActions) =>
      executeTest(capacity, addActions)
    }
  }

  def executeTest(capacity: Int, addActions: List[Add]): Unit = {
    val buffer = new CircularStringBuffer(capacity)

    addActions.foreach { add =>
      buffer.add(add.value)
    }

    val lastElements = addActions.takeRight(capacity).map(_.value)
    buffer.toList shouldBe lastElements
    lastElements.foreach { value =>
      buffer.remove() shouldBe value
    }

    buffer shouldBe empty
  }

  it should "always report the correct size" in {
    forAll(genCapacity, genActions) { (capacity, actions) =>
      val buffer = new CircularStringBuffer(capacity)

      var currentSize = 0

      for (action <- actions) {
        action match {
          case Add(str) =>
            buffer.add(str)
            if (currentSize < capacity) currentSize += 1
          case Remove if currentSize == 0 =>
            // nothing to remove
            a [NoSuchElementException] shouldBe thrownBy {
              buffer.remove()
            }
          case Remove =>
            buffer.remove()
            currentSize -= 1
        }
        buffer should have size currentSize
      }
    }
  }

  it should "Compare with FIFO queue" in {
    forAll(genCapacity, genActions) { (capacity, actions) =>
      val buffer = new CircularStringBuffer(capacity)
      val queue = mutable.Queue[String]()

      actions.foreach{ action =>
        action match {
          case Add(str) =>
            if (buffer.size == capacity) {
              queue.dequeue()
            }
            queue.enqueue(str)
            buffer.add(str)
          case Remove if queue.isEmpty =>
            a [NoSuchElementException] shouldBe thrownBy {
              buffer.remove()
            }
          case Remove =>
            val expected = queue.dequeue()
            buffer.remove() shouldBe expected
        }
        queue.toList shouldBe buffer.toList
      }
    }
  }

}
