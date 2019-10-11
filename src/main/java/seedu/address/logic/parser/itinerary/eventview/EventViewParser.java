package seedu.address.logic.parser.itinerary.eventview;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.itinerary.events.DeleteEventCommand;
import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.logic.commands.itinerary.events.EnterEditEventCommand;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.sidebar.EnterDayPageParser;
import seedu.address.logic.parser.sidebar.EnterItineraryPageParser;
import seedu.address.logic.parser.sidebar.EnterTripManagerParser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

public class EventViewParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateEventCommand.COMMAND_WORD + " "
            + DeleteEventCommand.COMMAND_WORD + " "
            + EnterEditEventCommand.COMMAND_WORD + " "
            + EnterTripManagerCommand.COMMAND_WORD + " "
            + EnterDayPageCommand.COMMAND_WORD + " "
            + EnterItineraryPageCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        EventViewCommand commandType;
        try {
            commandType = EventViewCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case CREATE:
            return new EnterCreateEventParser().parse(arguments);
        case DELETE:
            return new DeleteEventParser().parse(arguments);
        case EDIT:
            return new EnterEditEventParser().parse(arguments);
        case HOME:
            return new EnterTripManagerParser().parse(arguments);
        case DAYS:
            return new EnterDayPageParser().parse(arguments);
        case ITINERARY:
            return new EnterItineraryPageParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
