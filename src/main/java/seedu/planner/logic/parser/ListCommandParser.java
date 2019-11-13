package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.listcommand.ListAccommodationCommand;
import seedu.planner.logic.commands.listcommand.ListActivityCommand;
import seedu.planner.logic.commands.listcommand.ListCommand;
import seedu.planner.logic.commands.listcommand.ListContactCommand;
import seedu.planner.logic.commands.listcommand.ListDayCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private static final Pattern LIST_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        final Matcher matcher = LIST_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");

        switch (type) {
        case ListActivityCommand.SECOND_COMMAND_WORD:
            return new ListActivityCommand();
        case ListContactCommand.SECOND_COMMAND_WORD:
            return new ListContactCommand();
        case ListAccommodationCommand.SECOND_COMMAND_WORD:
            return new ListAccommodationCommand();
        case ListDayCommand.SECOND_COMMAND_WORD:
            return new ListDayCommand(ParserUtil.parseIndex(arguments));
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
