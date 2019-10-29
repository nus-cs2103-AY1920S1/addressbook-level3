package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Stores mapping of patterns to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same pattern.
 */
public class ArgumentMultimap {

    /**
     * Patterns mapped to their respective arguments
     **/
    private final Map<Pattern, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code pattern} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param pattern  Pattern key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified pattern key
     */
    public void put(Pattern pattern, String argValue) {
        List<String> argValues = getAllValues(pattern);
        argValues.add(argValue.trim());
        argMultimap.put(pattern, argValues);
    }

    /**
     * Returns the last value of {@code pattern}.
     */
    public Optional<String> getValue(Pattern pattern) {
        List<String> values = getAllValues(pattern);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code pattern}.
     * If the pattern does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Pattern pattern) {
        if (!argMultimap.containsKey(pattern)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(pattern));
    }
}
