package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ALL_FLAG_CONSTRAINTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.Flag.RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReturnCommand object.
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReturnCommand
     * and returns a ReturnCommand object for execution.
     *
     * @param userInput User input.
     * @return ReturnCommand object for execution.
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    @Override
    public ReturnCommand parse(String userInput) throws ParseException {
        if (onlyAllFlagPresent(userInput)) { // -all present
            return new ReturnCommand(true);
        }

        try { // parse by index instead
            Index index = ParserUtil.parseIndex(userInput);
            return new ReturnCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE), pe);
        }
    }

    private static boolean onlyAllFlagPresent(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_FLAG);
        if (argumentMultimap.getValue(PREFIX_FLAG).isEmpty()) {
            return false;
        }

        Optional<Flag> allFlag = parseAllFlag(argumentMultimap.getAllValues(PREFIX_FLAG));

        if (allFlag.isPresent() && !argumentMultimap.getPreamble().isEmpty()) { // if there are things before -all
            throw new ParseException(MESSAGE_ALL_FLAG_CONSTRAINTS);
        }

        return allFlag.isPresent();
    }

    private static Optional<Flag> parseAllFlag(Collection<String> flags) throws ParseException {
        requireNonNull(flags);

        if (flags.isEmpty()) {
            return Optional.empty();
        }

        Set<Flag> flagSet;
        try {
            flagSet = ParserUtil.parseFlags(flags);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS, ReturnCommand.COMMAND_WORD));
        }

        if (flagSet.isEmpty() || flagSet.contains(Flag.AVAILABLE) || flagSet.contains(Flag.LOANED)
                || flagSet.contains(Flag.OVERDUE)) {
            throw new ParseException(
                    String.format(RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS, ReturnCommand.COMMAND_WORD));
        }

        return Optional.of(new ArrayList<>(flagSet).get(0));
    }
}
