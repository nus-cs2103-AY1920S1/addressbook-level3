package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.EnqueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.ReferenceId;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class EnqueueCommandParser implements Parser<EnqueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnqueueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnqueueCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.parsePatientReferenceId(argMultimap.getPreamble());
        return new EnqueueCommand(referenceId);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
