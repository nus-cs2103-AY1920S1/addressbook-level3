package seedu.address.logic.parser.itinerary.overallview;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.itinerary.dayview.EnterDayParser;
import seedu.address.logic.parser.itinerary.eventview.DeleteEventParser;
import seedu.address.logic.parser.itinerary.eventview.EnterCreateEventParser;
import seedu.address.logic.parser.itinerary.eventview.EnterEditEventParser;
import seedu.address.logic.parser.itinerary.eventview.EventViewCommand;
import seedu.address.logic.parser.sidebar.EnterDayPageParser;
import seedu.address.logic.parser.sidebar.EnterItineraryPageParser;
import seedu.address.logic.parser.sidebar.EnterTripManagerParser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

public class ItineraryViewParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterTripManagerCommand.COMMAND_WORD + " "
            + EnterDayPageCommand.COMMAND_WORD + " "
            + EnterItineraryPageCommand.COMMAND_WORD;
    @Override
    public Command parse(String command, String arguments) throws ParseException {
        ItineraryViewCommand commandType;
        try {
            commandType = ItineraryViewCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case HOME:
            return new EnterTripManagerParser().parse(arguments);
        case DAYS:
            return new EnterDayPageParser().parse(arguments);
        case ITINERARY:
            return new EnterItineraryPageParser().parse(arguments);
        case GOTO:
            return new EnterDayParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
