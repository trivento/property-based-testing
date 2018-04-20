package nl.trivento.propertybased.exercise3

import org.scalacheck.{Gen, Shrink}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class RangeSeqTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 1000)

  implicit val shrinkDoubleToWholeNumber: Shrink[Double] = Shrink{ d =>
    val normalDoubleShrink: Shrink[Double] = Shrink.shrinkFractional
    normalDoubleShrink.shrink(d).flatMap { shrunk =>
      Try(shrunk.toInt.toDouble).toOption
    }.distinct
  }

  behavior of "RangeSeq"

  //=================================================EXAMPLE-BASED-TEST=================================================
  it should "return a list starting from 1 to 10 with increments of 1 " in {
    val rangeList = RangeSeq(1.0, 10.0, 1.0).iterator.toList
    rangeList shouldBe List(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
  }

  it should "find each element (i.e., step) in the RangeSeq" in {
    val rangeList = RangeSeq(1.0, 10.0, 1.0)
    for(d <- rangeList){
      rangeList.contains(d) shouldBe true
    }
  }

  it should "return an empty list when the 'start' and 'end' are equal" in {
    val rangeList = RangeSeq(1.0, 1.0, 1.0).iterator.toList
    rangeList shouldBe List.empty
  }

  //=================================================PROPERTY-BASED-TEST================================================

  // Implement your property based tests here

}
