package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.DeleteAccommodationCommand;
import seedu.planner.logic.commands.DeleteActivityCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.DeleteDayCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Pattern DELETE_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        final Matcher matcher = DELETE_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");

        try {
            Index index = ParserUtil.parseIndex(arguments);

            switch(type) {
            case DeleteAccommodationCommand.SECOND_COMMAND_WORD:
                return new DeleteAccommodationCommand(index);
            case DeleteActivityCommand.SECOND_COMMAND_WORD:
                return new DeleteActivityCommand(index);
            case DeleteContactCommand.SECOND_COMMAND_WORD:
                return new DeleteContactCommand(index);
            case DeleteDayCommand.SECOND_COMMAND_WORD:
                return new DeleteDayCommand(index);
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
