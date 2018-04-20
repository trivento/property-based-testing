package nl.trivento.propertybased.exercise2

import org.scalacheck.{Gen, Shrink}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}


class TransitiveDependenciesTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  behavior of "findTransitiveDependencies"

  //=================================================EXAMPLE-BASED-TEST=================================================

  it should "find all dependencies using an example based test" in {
    val dependencies = Map('A' -> Set('B', 'C'),
      'B' -> Set('C', 'E'),
      'C' -> Set('G'),
      'D' -> Set('A', 'F'),
      'E' -> Set('F'),
      'F' -> Set('H'))
    TransitiveDependencies.findTransitiveDependencies('A', dependencies) shouldBe Set('B','C','E','F','G','H')
    TransitiveDependencies.findTransitiveDependencies('B', dependencies) shouldBe Set('C', 'E', 'F', 'G', 'H')
    TransitiveDependencies.findTransitiveDependencies('C', dependencies) shouldBe Set('G')
    TransitiveDependencies.findTransitiveDependencies('D', dependencies) shouldBe Set('A', 'B', 'C', 'E', 'F', 'G', 'H')
    TransitiveDependencies.findTransitiveDependencies('E', dependencies) shouldBe Set('F', 'H')
    TransitiveDependencies.findTransitiveDependencies('F', dependencies) shouldBe Set('H')
    TransitiveDependencies.findTransitiveDependencies('H', dependencies) shouldBe Set.empty
  }

  //=================================================PROPERTY-BASED-TEST=================================================
  /**
    ***********************************************
    * Remember to generate a test case we need:   *
    *  1). random test data                       *
    *  2). a property to verify                   *
    ***********************************************
    */

  // We do not want scalacheck to change values of our characters.
  // Having this shrink in scope ensures only chars 'A' to 'Z' are used.
  // Feel free to see what happens if you comment this line and a test fails.
  implicit val doNotShrinkCharacters: Shrink[Char] = Shrink.shrinkAny

  // 1). Random test data
  /**
    * A Generator that generates the characters A to Z
    * Hint: Have a look at some of the predefined methods in [[org.scalacheck.Gen]]
    */
  def genCharacter: Gen[Char] = ???

  "genCharacter" should "only generate characters in the range from A to Z" in {
    forAll(genCharacter) { char =>
      char should be >= 'A'
      char should be <= 'Z'
    }
  }


  /**
    * A Generator that generates a tuple with one character with a (possibly empty) set of dependencies
    */
  def genDependencyTuple: Gen[(Char, Set[Char])] = for {
    character <- genCharacter
    dependencies <- Gen.fail[Set[Char]] // TODO replace Gen.fail with a proper generator
  } yield ???


  "genDependencyTuple" should "only generate characters in the range from A to Z" in {
    forAll(genDependencyTuple){ case (char, setOfChars) =>
      char should be >= 'A'
      char should be <= 'Z'
      setOfChars.foreach { c =>
        c should be >= 'A'
        c should be <= 'Z'
      }
    }
  }

  /**
    * A Generator that generates a map of dependencies, it may be empty
    */
  def genDependencyMap: Gen[Map[Char, Set[Char]]] = ???

  "genDependencyMap" should "only generate characters in the range from A to Z" in {
    forAll(genDependencyMap) { map =>
      map.foreach { case (char, setOfChars) =>
        char should be >= 'A'
        char should be <= 'Z'
        setOfChars.foreach { c =>
          c should be >= 'A'
          c should be <= 'Z'
        }
      }
    }
  }


  // 2). Properties to verify
  it should "TODO think of some properties to test" in {
    ???
  }

}
