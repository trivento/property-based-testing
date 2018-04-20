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
  def genCharacter: Gen[Char] = Gen.choose('A', 'Z')

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
    dependencies <- Gen.listOf(genCharacter)
  } yield character -> dependencies.toSet


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
  def genDependencyMap: Gen[Map[Char, Set[Char]]] = Gen.mapOf(genDependencyTuple)

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


  it should "always return the direct dependencies in the given result (dependency map with single entry)" in {
    val genDirectDependencies: Gen[(Char, (Char, Set[Char]))] = for {
      key <- genCharacter
      dependencies <- Gen.listOf(genCharacter)
    } yield   {
      val directDependencies = key -> (dependencies.toSet - key)
      (key, directDependencies)
    }

    forAll(genDirectDependencies) {  case (key, directDependencies) =>
      val result = TransitiveDependencies.findTransitiveDependencies(key, Map(directDependencies))
      result should contain allElementsOf Map(directDependencies).getOrElse(key, Set.empty)
    }
  }

  it should "always return the direct dependencies in the given result" in {
    val genDirectDependencies: Gen[(Char, Map[Char, Set[Char]])] = for {
      key <- genCharacter
      dependenciesForKey <- Gen.listOf(genCharacter)
      otherDependencies <- genDependencyMap
    } yield   {
      val directDependencies = dependenciesForKey.toSet - key
      val completeMap = otherDependencies + (key -> directDependencies)
      (key, completeMap)
    }

    forAll(genDirectDependencies) {  case (key, dependencyMap) =>
      val result = TransitiveDependencies.findTransitiveDependencies(key, dependencyMap)
      result should contain allElementsOf dependencyMap.getOrElse(key, Set.empty)
    }
  }

  it should "always return the direct and indirect dependencies" in {
    val gen = for {
      k1 <- genCharacter
      k2 <- genCharacter
      k3 <- genCharacter
      map <- genDependencyMap
    } yield {
      // k1 depends on at least k2
      val k1Dependencies = map.getOrElse(k1, Set.empty) + k2
      // k2 depends on at least k3
      val k2Dependencies = map.getOrElse(k2, Set.empty) + k3
      val combinedMap = map + (k1 -> k1Dependencies) + (k2 -> k2Dependencies)
      (k1, k2, combinedMap)
    }

    forAll(gen) { case (k1, k2, map) =>
      val result = TransitiveDependencies.findTransitiveDependencies(k1, map)
      whenever(map.contains(k1) && map(k1).contains(k2) && map.contains(k2)) {
        val directDependencies = map(k1)
        // We know that k1, depends on k2
        val indirectDependencies = map(k2)

        val minimumDependencySet = directDependencies ++ indirectDependencies

        result should contain allElementsOf minimumDependencySet
      }
    }
  }
}
