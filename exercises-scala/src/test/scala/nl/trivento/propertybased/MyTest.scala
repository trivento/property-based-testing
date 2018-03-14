package nl.trivento.propertybased

import org.scalatest.FlatSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class MyTest extends FlatSpec with GeneratorDrivenPropertyChecks {

  "all integers" should "be positive" in {
    forAll { i: Int =>
      assert(i >= 0)
    }
  }
}
