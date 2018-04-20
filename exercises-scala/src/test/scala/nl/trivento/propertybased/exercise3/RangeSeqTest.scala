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

  val genSmallDouble: Gen[Double] = Gen.choose(-1000.0, 1000.0)
  val genSimpleDouble: Gen[Double] = Gen.choose(-100, 100).map(_.toDouble / 10)



  def rangeSeqGenerator(): Gen[(Double, Double, Double)] = {
    val genDouble: Gen[Double] = Gen.frequency(
      1 -> genSimpleDouble,
      4 -> genSimpleDouble
    )

    val gen = for{
      d1 <- genDouble
      d2 <- genDouble
      step <- genDouble
    } yield {
      val start = if(step > 0.0) Math.min(d1, d2) else Math.max(d1, d2)
      val end = if(step > 0.0) Math.max(d1, d2) else Math.min(d1, d2)
      (start, end, step)
    }
    gen.suchThat{ case (start, end, step) =>
      step != 0 &&
        (step < 0.0 || start <= end) &&
        (step > 0.0 || start >= end)
    }
  }

  "Positive step - The contains method" should "return true for all elements in the iterator" in {
    forAll(rangeSeqGenerator()){ case (start, end, step) =>
      val rangeSeq = RangeSeq(start, end, step)
      whenever(rangeSeq.step > 0.0){
        for(d <- rangeSeq){
          rangeSeq.contains(d) shouldBe true
        }
      }
    }
  }

  "Positive step - The apply method" should "return the same element as the iterator" in {
    forAll(rangeSeqGenerator()){ case (start, end, step) =>
      val rangeSeq = RangeSeq(start, end, step)
      whenever(rangeSeq.step > 0.0){
        for((d, index) <- rangeSeq.zipWithIndex){
          rangeSeq.apply(index) shouldBe d
        }
      }
    }
  }

  "Negative step - The contains method" should "return true for all elements in the iterator" in {
    forAll(rangeSeqGenerator()){ case (start, end, step) =>
      val rangeSeq = RangeSeq(start, end, step)
      whenever(rangeSeq.step < 0.0){
        for(d <- rangeSeq){
          rangeSeq.contains(d) shouldBe true
        }
      }
    }
  }

  "Positive step - The size functionality" should "be equal to all steps in the RangeSeq " in {
    forAll(rangeSeqGenerator()){ case (start, end, step) =>
      val rangeSeq = RangeSeq(start, end, step)
      whenever(rangeSeq.step > 0.0){
        var i = 0
        for(d <- rangeSeq){
          i = i + 1
        }
        rangeSeq.length shouldBe i
      }
    }
  }

  "Negative step - The size functionality" should "be equal to all steps in the RangeSeq " in {
    forAll(rangeSeqGenerator()){ case (start, end, step) =>
      val rangeSeq = RangeSeq(start, end, step)
      whenever(rangeSeq.step < 0.0){
        val list = rangeSeq.iterator.toList
        var i = 0
        for(d <- rangeSeq){
          i = i + 1
        }
        rangeSeq.length shouldBe i
      }
    }
  }
}
