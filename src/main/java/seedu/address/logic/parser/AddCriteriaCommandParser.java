package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CRITERIA;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCriteriaCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCriteriaCommand object
 */
public class AddCriteriaCommandParser implements Parser<AddCriteriaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCriteriaCommand
     * and returns a AddCriteriaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCriteriaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CRITERIA);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT,
                            AddCriteriaCommand.MESSAGE_USAGE),
                    ive);
        }

        Object[] criteriaValues = argMultimap.getAllValues(PREFIX_CRITERIA).toArray();
        String[] criteria = Arrays.copyOf(criteriaValues, criteriaValues.length, String[].class);
        return new AddCriteriaCommand(index, criteria);
    }

}
