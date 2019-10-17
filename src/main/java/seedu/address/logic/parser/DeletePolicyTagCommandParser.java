package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePolicyTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePolicyTagCommand object
 */
public class DeletePolicyTagCommandParser implements Parser<DeletePolicyTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePolicyTagCommandParser
     * and returns a DeletePolicyTagCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePolicyTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT,
                            DeletePolicyTagCommand.MESSAGE_USAGE),
                    ive);
        }

        Object[] tagValues = argMultimap.getAllValues(PREFIX_TAG).toArray();
        String[] tags = Arrays.copyOf(tagValues, tagValues.length, String[].class);
        return new DeletePolicyTagCommand(index, tags);
    }

}
