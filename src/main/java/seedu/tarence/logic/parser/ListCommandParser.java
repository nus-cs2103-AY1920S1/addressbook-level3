package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.ListCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.student.StudentsInTutorialPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    public static final boolean SHOW_ALL_STUDENTS = true;
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand(SHOW_ALL_STUDENTS);
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListCommand(new StudentsInTutorialPredicate(index));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), pe);
        }
    }
}
