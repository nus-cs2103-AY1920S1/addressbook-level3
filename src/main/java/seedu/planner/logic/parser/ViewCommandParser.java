package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.viewcommand.ViewAccommodationCommand;
import seedu.planner.logic.commands.viewcommand.ViewActivityCommand;
import seedu.planner.logic.commands.viewcommand.ViewCommand;
import seedu.planner.logic.commands.viewcommand.ViewContactCommand;
import seedu.planner.logic.commands.viewcommand.ViewHelpCommand;
import seedu.planner.logic.commands.viewcommand.ViewInfoCommand;
import seedu.planner.logic.commands.viewcommand.ViewItineraryCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 * @author 1nefootstep
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    private static final Pattern VIEW_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        final Matcher matcher = VIEW_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");
        switch (type) {
        case ViewAccommodationCommand.SECOND_COMMAND_WORD:
            return new ViewAccommodationCommand(ParserUtil.parseIndex(arguments));
        case ViewActivityCommand.SECOND_COMMAND_WORD:
            return new ViewActivityCommand(ParserUtil.parseIndex(arguments));
        case ViewContactCommand.SECOND_COMMAND_WORD:
            return new ViewContactCommand(ParserUtil.parseIndex(arguments));
        case ViewItineraryCommand.SECOND_COMMAND_WORD:
            return new ViewItineraryCommand();
        case ViewInfoCommand.SECOND_COMMAND_WORD:
            return new ViewInfoCommand();
        case ViewHelpCommand.SECOND_COMMAND_WORD:
            return new ViewHelpCommand();
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }


}
