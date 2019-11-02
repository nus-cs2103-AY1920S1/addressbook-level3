package seedu.sugarmummy.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Stores mapping of prefixes to their respective arguments. Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained. Keys are unique, but the list
 * of argument values may contain duplicate argument values, i.e. the same argument value can be inserted multiple times
 * for the same prefix.
 */
public class ArgumentMultimap {

    /**
     * Prefixes mapped to their respective arguments
     **/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map. If the map previously contained a
     * mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        argMultimap.put(prefix, argValues);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}. If the prefix does not exist or has no values, this will return an empty
     * list. Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }

    /**
     * Returns whether or not there are any prefix-value mappings in this instance of ArgumentMultimap.
     */
    public boolean isEmpty() {
        return argMultimap.size() <= 1;
    }

    /**
     * Returns whether or not prefixes in a given list are the only prefixes in this ArgumentMultimap.
     */
    public boolean containsOnlyPrefixes(Prefix... prefixes) {
        ArrayList<Prefix> keys = new ArrayList<>(Arrays.asList(prefixes));
        keys.add(new Prefix(""));
        Set<Prefix> keySet = argMultimap.keySet();
        for (Prefix prefix : keySet) {
            if (!keys.contains(prefix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not all prefixes in a given list are contained in this ArgumentMultimap.
     */
    public boolean containsAllPrefixes(Prefix...prefixes) {
        Set<Prefix> keySet = argMultimap.keySet();
        for (Prefix prefix : prefixes) {
            if (!keySet.contains(prefix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not a given prefix is unique this ArgumentMultimap.
     */
    public boolean isUniquePrefix(Prefix prefix) {
        Set<Prefix> keySet = argMultimap.keySet();
        if (!keySet.contains(prefix)) {
            return true;
        } else {
            return argMultimap.get(prefix).size() <= 1;
        }
    }

    /**
     * Returns whether or not a given list of prefixes are all unique.
     */
    public boolean prefixesAreUnique(Prefix...prefixes) {
        for (Prefix prefix : prefixes) {
            if (!isUniquePrefix(prefix)) {
                return false;
            }
        }
        return true;
    }

    public List<Prefix> getNonUniquePrefixes(Prefix...prefixes) {
        List<Prefix> nonUniquePrefixes = new ArrayList<>();
        for (Prefix prefix : prefixes) {
            if (!isUniquePrefix(prefix)) {
                nonUniquePrefixes.add(prefix);
            }
        }
        return nonUniquePrefixes;
    }

    @Override
    public String toString() {
        return argMultimap.toString();
    }

}
