package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CRITERIA;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCriteriaCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePolicyTagCommand object
 */
public class DeleteCriteriaCommandParser implements Parser<DeleteCriteriaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCriteriaCommandParser
     * and returns a DeleteCriteriaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCriteriaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CRITERIA);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteCriteriaCommand.MESSAGE_USAGE),
                    ive);
        }

        Object[] criteriaValues = argMultimap.getAllValues(PREFIX_CRITERIA).toArray();
        String[] criteria = Arrays.copyOf(criteriaValues, criteriaValues.length, String[].class);

        for (int i = 0; i < criteria.length; i++) {
            criteria[i] = criteria[i].toLowerCase();
        }

        return new DeleteCriteriaCommand(index, criteria);
    }

}
