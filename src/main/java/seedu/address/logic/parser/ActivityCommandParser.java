package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ACTIVITY_MISSING_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;

import seedu.address.logic.commands.ActivityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class ActivityCommandParser implements Parser<ActivityCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ActivityCommand}
     * and returns a {@code ActivityCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format,
     *                        or has missing compulsory arguments.
     */

    public ActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_PARTICIPANT);

        String title = argMultimap.getValue(PREFIX_TITLE).orElse("");

        if (title.equals("")) {
            throw new ParseException(MESSAGE_ACTIVITY_MISSING_TITLE);
        }

        List<String> participants = argMultimap.getAllValues(PREFIX_PARTICIPANT);

        return new ActivityCommand(title, participants);
    }

}
