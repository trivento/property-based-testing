package nl.trivento.propertybased.exercise2

/**
  * The findTransitiveDependencies function should calculate the full set of dependencies.
  * One of the insidious things about dependencies is that they are transitive.
  * Meaning that, if 'A' depends on 'B' and 'B' depends on 'C', then 'A' also depends on 'C'.
  *
  * Below you see an example:
  *
  * Map('A' -> Set('B', 'C'),
  *     'B' -> Set('C', 'E'),
  *     'C' -> Set('G'),
  *     'D' -> Set('A', 'F'),
  *     'E' -> Set('F'),
  *     'F' -> Set('H'))
  *
  * 'A' has two direct dependencies, namely 'B' and 'C'.
  * Next to that it has the following indirect dependencies: 'E', 'F', 'G' and 'H'
  *
  * So the full set of dependencies for 'A' are 'B', 'C', 'E', 'F', 'G' and 'H'
  *
  */
object TransitiveDependencies {


  def findTransitiveDependencies(key: Char, dependencies: Map[Char, Set[Char]]): Set[Char] = {
    def loop(key: Char, acc: Set[Char]): Set[Char] = {
      dependencies.get(key) match {
        case Some(directDependencies) if directDependencies.diff(acc).isEmpty => acc
        case Some(directDependencies) =>
          val indirectDependencies: Set[Char] = directDependencies.flatMap(key => loop(key, acc ++ directDependencies))
          directDependencies ++ indirectDependencies
        case None => acc
      }
    }
    loop(key, acc = Set.empty)
  }
}
