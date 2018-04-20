package nl.trivento.propertybased.exercise2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransitiveDependencies {


    /**
     *
     */
    public static Set<Character> findTransitiveDependencies(char key, Map<Character, Set<Character>> dependencies) {
        return findTransitiveDependencies(key, new HashSet<>(), dependencies);
    }

    private static Set<Character> findTransitiveDependencies(char currentKey, Set<Character> acc, Map<Character, Set<Character>> dependencies) {
        if (dependencies.containsKey(currentKey)) {
            Set<Character> directDependencies = dependencies.get(currentKey);

            Set<Character> indirectDependencies = new HashSet<>();
            for (char key: directDependencies) {
                indirectDependencies.addAll(findTransitiveDependencies(key, directDependencies, dependencies));
            }
            indirectDependencies.addAll(directDependencies);
            return indirectDependencies;
        } else {
            return acc;
        }
    }
}
