package seedu.address.ui.textfield;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

/**
 * A supported command input for autofill.
 */
public class AutofillSupportedCommand {
    private List<Prefix> required;
    private List<Prefix> optional;
    private String command;

    AutofillSupportedCommand(String commandWord, List<Prefix> requiredPrefixes, List<Prefix> optionalPrefixes) {
        this.command = commandWord;

        required = List.copyOf(requiredPrefixes);

        optional = List.copyOf(optionalPrefixes);
    }

    public boolean isRequired(Prefix p) {
        return required.contains(p);
    }

    public String getCommand() {
        return command;
    }

    /**
     * Returns a list of missing prefixes to add to the auto completion suggestions.
     * @param input The string to check for prefixes.
     */
    public List<Prefix>[] getMissingPrefixes(String input) {
        ArrayList<Prefix> prefixes = new ArrayList<>(required);
        prefixes.addAll(optional);
        Prefix[] prefixArray = prefixes.toArray(new Prefix[]{});

        // tokenize to find prefixes
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(input, prefixArray);

        ArrayList<Prefix> missingReq = new ArrayList<>();
        ArrayList<Prefix> missingOpt = new ArrayList<>();

        // find missing required prefixes
        required.forEach(p -> {
            if (argumentMultimap.getValue(p).isEmpty()) {
                missingReq.add(p);
            }
        });

        // find missing optional prefixes
        optional.forEach(p -> {
            if (argumentMultimap.getValue(p).isEmpty()) {
                missingOpt.add(p);
            }
        });
        return (List<Prefix>[]) new List[]{missingReq, missingOpt};
    }
}
