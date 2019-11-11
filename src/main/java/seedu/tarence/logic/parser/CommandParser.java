package seedu.tarence.logic.parser;

import java.util.stream.Stream;

import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Framework for all {@code Parser}s meant for user inputs. Contains methods to check if specified prefixes are
 * present or absent.
 */
public abstract class CommandParser<T extends Command> implements Parser<Command> {

    public abstract Command parse(String args) throws ParseException;

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean areAllPrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
