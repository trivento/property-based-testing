package nl.trivento.propertybased

import org.scalacheck.Gen
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

/**
  * for more info see: http://www.scalatest.org/user_guide/writing_scalacheck_style_properties
  */
class Examples extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  // The parameters in the PropertyCheckConfiguration can be used to configure how many times a test should succeed.
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 10)

  behavior of "Example"

  it should "generate primitive values" in {
    forAll { (i: Int, d: Double, s: String) =>
      println(i, d, s)
    }
  }

  it should "Generates numbers within the given inclusive range" in {
    val numberGenerator = Gen.chooseNum(1, 10)
    forAll(numberGenerator) { a: Int =>
      println(a)
    }
  }

  it should "generate tuples" in {

    // Gen.chooseNum is like Gen.choose, however Gen.chooseNum has a higher change
    // to generate the minimum value, maximum value, 0, 1 and -1 (if in range)
    // Gen.choose has a uniform distribution (each number has the same change to be generated)
    val numberGen1 = Gen.chooseNum(0, Int.MaxValue / 2)
    val numberGen2 = Gen.choose(0, Int.MaxValue / 2)

    val tupleGen: Gen[(Int, Int)] = for {
      numberA <- numberGen1
      numberB <- numberGen2
    } yield (numberA, numberB)

    // when generating tuples the keyword 'case' is needed
    forAll(tupleGen) { case (numberA, numberB) =>
      println(numberA, numberB)
    }
  }

  it should "wrap Gen[Int] in a List" in {
    val numberGen: Gen[Int] = Gen.choose(0, 10)
    val listOfInts: Gen[List[Int]] = Gen.listOf(numberGen)

    forAll(listOfInts) { case (list) =>
      println(list)
    }
  }

  it should "place a condition on the generated test data" in {
    val numberGen = Gen.choose(1, 20)
    forAll(numberGen){ (number) =>

      /**
        * If the generated test data does not match the condition the test is discarded and a new set of parameters will be generated.
        * This can be used to ensure that assumptions hold.
        */
      whenever(number > 10){
        println(number)
    }
    }
  }



}