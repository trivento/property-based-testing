package nl.trivento.propertybased.exercise1

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}


class SumTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {


  /**
    * @return The two numbers a and b added together
    */
  def sum(a: Int, b: Int): Int = a + b

  behavior of "sum"

  // Traditional example based test
  it should "correctly add numbers" in {
    sum(1,2) shouldBe 3
    sum(123,0) shouldBe 123
    sum(123,-100) shouldBe 23
  }


  // Property based tests

  it should "execute without crashing" in {
    // This is an example of how to create a test with takes two randomly generated numbers as an argument
    // This test itself does not validate anything, however sometimes we just want to verify that a method terminated and no exceptions are thrown
    forAll { (a: Int, b: Int) =>
      sum(a, b)
    }
  }


  // Example how to generate number in a range
  it should "generate numbers in a range" in {
    val numberGenerator = Gen.chooseNum(1, 10)
    forAll(numberGenerator) { a: Int =>
      a should be >= 1
      a should be <= 10
    }
  }


  it should "be cummutative" in {
    ???
  }


  it should "be associative" in {
    ???
  }

  it should "allow adding 0 (idenity property)" in {
    ???
  }

  it should "be return 0 when adding the inverse" in {
    ???
  }

  it should "return a positive outcome when two positive numbers are added" in {
    ???
  }

}
