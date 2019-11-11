package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.system.logic.commands.outofsession.FindParticipationCommand;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.participation.PersonOrCompetitionNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindParticipationCommand object
 */
public class FindParticipationCommandParser implements Parser<FindParticipationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindParticipationCommand
     * and returns a FindParticipationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindParticipationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindParticipationCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindParticipationCommand(
            new PersonOrCompetitionNameContainsKeywordsPredicate(Arrays.asList(nameKeywords))
        );
    }

}
