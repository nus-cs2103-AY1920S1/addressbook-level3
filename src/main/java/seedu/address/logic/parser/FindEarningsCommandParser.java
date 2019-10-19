package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindEarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.earnings.ClassIdContainKeywordPredicate;

/**
 * Parses input arguments and creates a new FindEarningsCommand object
 */
public class FindEarningsCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEarningsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEarningsCommand.MESSAGE_USAGE));
        }

        String[] classIdKeywords = trimmedArgs.split("\\s+");

        return new FindEarningsCommand(new ClassIdContainKeywordPredicate(Arrays.asList(classIdKeywords)));
    }

}
