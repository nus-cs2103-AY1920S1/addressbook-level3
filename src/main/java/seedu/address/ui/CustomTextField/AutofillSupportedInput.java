package seedu.address.ui.CustomTextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

public class AutofillSupportedInput {
    Map<Prefix, Boolean> requiredPrefixToIsPresentMapping;
    Map<Prefix, Boolean> optionalPrefixToIsPresentMapping;
    String command;

    AutofillSupportedInput(String commandWord, List<Prefix> requiredPrefixes, List<Prefix> optionalPrefixes) {
        this.command = commandWord;

        requiredPrefixToIsPresentMapping = new HashMap<>();
        for (Prefix p : requiredPrefixes) {
            requiredPrefixToIsPresentMapping.put(p, false);
        }

        optionalPrefixToIsPresentMapping = new HashMap<>();
        for (Prefix p : optionalPrefixes) {
            optionalPrefixToIsPresentMapping.put(p, false);
        }
    }

    /**
     * Returns a String[] of size 3 if there are prefixes missing, otherwise return null.
     * The String at index 0 is the completion text
     * The String at index 1 is a string containing the missing required prefixes with the argument's description.
     * The String in index 2 is a string containing the missing optional prefixes with the argument's description.
     * @param input The string to check for prefixes.
     * @param delimiter The delimiter used when joining the prefixes in the string.
     */
    public String[] completion(String input, String delimiter) {
        ArrayList<Prefix> prefixes = new ArrayList<>(requiredPrefixToIsPresentMapping.keySet());
        prefixes.addAll(optionalPrefixToIsPresentMapping.keySet());
        Prefix[] prefixArray = prefixes.toArray(new Prefix[]{});
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(input, prefixArray);
        requiredPrefixToIsPresentMapping.replaceAll((p, v) -> argumentMultimap.getValue(p).isPresent());
        optionalPrefixToIsPresentMapping.replaceAll((p, v) -> argumentMultimap.getValue(p).isPresent());

        String prefixSuggestion = requiredPrefixToIsPresentMapping.entrySet().stream()
                .filter(x -> !x.getValue()) // missing prefixes
                .map(x -> {
                    Prefix pre = x.getKey();
                    return pre.getPrefix() + "<" + pre.getDescriptionOfArgument() + ">";
                }) // [ p/ <desc>]
                .collect(Collectors.joining(delimiter));

        String optionalPrefixes = optionalPrefixToIsPresentMapping.entrySet().stream()
                .filter(x -> !x.getValue()) // missing prefixes
                .map(x -> {
                    Prefix pre = x.getKey();
                    return pre.getPrefix() + "<" + pre.getDescriptionOfArgument() + ">";
                }) // [ p/ <desc>]
                .collect(Collectors.joining(delimiter));

        String completion = requiredPrefixToIsPresentMapping.entrySet().stream()
                .filter(x -> !x.getValue()) // missing prefixes
                .map(x -> x.getKey().getPrefix())
                .collect(Collectors.joining(" "));

        return new String[]{completion, prefixSuggestion, optionalPrefixes};
    }
}