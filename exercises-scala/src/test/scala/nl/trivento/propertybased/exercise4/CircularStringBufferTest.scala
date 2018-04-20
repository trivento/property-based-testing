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
  def genCapacity: Gen[Int] = ???

  /**
    * Generator for Add
    */
  def genAdd: Gen[Add] = ???

  /**
    * Generator for add actions.
    * Does it make sense to generate an empty list?
    */
  def genAddActions: Gen[List[Add]] = ???

  /**
    * Generator for either an add or a remove action.
    */
  def genAction: Gen[Action] = ???

  /**
    * Generator for a list with any sequence of actions
    */
  def genActions: Gen[List[Action]] = ???

  behavior of "circular string buffer"

  // Add your tests here


}
