package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.system.logic.commands.outofsession.FindCompetitionCommand;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.competition.CompetitionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCompetitionCommand object
 */
public class FindCompetitionCommandParser implements Parser<FindCompetitionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCompetitionCommand
     * and returns a FindCompetitionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCompetitionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCompetitionCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCompetitionCommand(new CompetitionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
