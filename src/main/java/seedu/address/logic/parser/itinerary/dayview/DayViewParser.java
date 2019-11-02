package seedu.address.logic.parser.itinerary.dayview;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.logic.commands.itinerary.days.EnterEditDayCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.navbar.NavbarViewParser;

/**
 * Parser class responsible for directing commands belonging to the day view.
 */
public class DayViewParser implements PageParser<Command> {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterDayCommand.COMMAND_WORD + " "
            + EnterEditDayCommand.COMMAND_WORD + " | "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        DayViewCommand commandType;
        try {
            commandType = DayViewCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case GOTO:
            return new EnterDayParser().parse(arguments);
        case EDIT:
            return new EnterEditDayParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
