package seedu.address.calendar.parser;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

import java.util.stream.Stream;

class ParserUtil {
    static final String MESSAGE_DUPLICATED_ARG = "Duplicated arguments detected.";

    static boolean hasMultiplePrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
