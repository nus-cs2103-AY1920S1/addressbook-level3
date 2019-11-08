package com.typee.logic.interactive.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /** Prefixes mapped to their respective arguments**/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
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
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(prefix));
    }

    /**
     * Removes the argument mapped to the input {@code Prefix}.
     *
     * @param prefix Prefix to be removed from the multimap.
     */
    public void clearValues(Prefix prefix) {
        argMultimap.remove(prefix);
    }

    /**
     * Returns true if the input {@code ArgumentMultimap} is disjoint with the calling {@code ArgumentMultimap}.
     * If the {@code ArgumentMultimaps} have no {@code Prefixes} in common, they are considered to be disjoint.
     *
     * @param argumentMultimap {@code ArgumentMultimap} to check exclusivity with.
     * @return true if the {@code ArgumentMultimaps} are disjoint.
     */
    public boolean isDisjointWith(ArgumentMultimap argumentMultimap) {
        HashMap<Prefix, List<String>> copy = new HashMap<>(argMultimap);
        Set<Prefix> keys = copy.keySet();
        Set<Prefix> newKeys = argumentMultimap.argMultimap.keySet();
        keys.retainAll(newKeys);
        return keys.isEmpty();
    }

    /**
     * Returns true if the {@code ArgumentMultimap} is empty.
     *
     * @return true if empty.
     */
    public boolean isEmpty() {
        return argMultimap.isEmpty();
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }
}
