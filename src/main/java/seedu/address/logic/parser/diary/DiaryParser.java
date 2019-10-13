package seedu.address.logic.parser.diary;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.itinerary.dayview.EnterDayParser;
import seedu.address.logic.parser.itinerary.overallview.ItineraryViewCommand;
import seedu.address.logic.parser.navbar.NavbarViewParser;
import seedu.address.logic.parser.sidebar.EnterDayPageParser;
import seedu.address.logic.parser.sidebar.EnterItineraryPageParser;
import seedu.address.logic.parser.sidebar.EnterTripManagerParser;

/**
 * Placeholder javadoc.
 */
public class DiaryParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterTripManagerCommand.COMMAND_WORD + " "
            + EnterDayPageCommand.COMMAND_WORD + " "
            + EnterItineraryPageCommand.COMMAND_WORD + " "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        DiaryCommand commandType;
        try {
            commandType = DiaryCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case FLIP:
            return new FlipDiaryParser().parse(arguments);
        case EDIT:
            return new EditDiaryParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
