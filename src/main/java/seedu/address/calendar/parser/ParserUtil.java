package seedu.address.calendar.parser;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class ParserUtil {
    static final String MESSAGE_ARG_DUPLICATED = "Duplicated arguments detected.";
    static final String MESSAGE_ARG_EXTRA = "Extra/unrecognised arguments or extra characters detected %s.";
    private static final String EXTRA_ARG_PATTERN = "(\\d*+\\s++.*)|(\\S*+\\s++.*)";
    private static final Pattern EXTRA_ARG_FORMAT = Pattern.compile(EXTRA_ARG_PATTERN);

    static boolean hasMultiplePrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    static boolean hasCharInValue(String userInput) {
        Matcher matcher = EXTRA_ARG_FORMAT.matcher(userInput);
        return matcher.matches();
    }
}
