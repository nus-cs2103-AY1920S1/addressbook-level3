package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.InviteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class InviteCommandParser implements Parser<InviteCommand> {

    public InviteCommand parse(String args) throws ParseException {

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
