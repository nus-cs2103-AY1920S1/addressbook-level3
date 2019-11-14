package seedu.address.logic.parser.itinerary.eventview;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.itinerary.events.DeleteEventCommand;
import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.logic.commands.itinerary.events.EnterEditEventCommand;
import seedu.address.logic.commands.itinerary.events.ShowEventDetailsCommand;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for {@link seedu.address.ui.itinerary.EventsPage}.
 */
public class EventViewParser implements PageParser<Command> {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateEventCommand.COMMAND_WORD + " "
            + DeleteEventCommand.COMMAND_WORD + " "
            + EnterEditEventCommand.COMMAND_WORD + " "
            + EnterTripManagerCommand.COMMAND_WORD + " "
            + EnterDayPageCommand.COMMAND_WORD + " "
            + EnterItineraryPageCommand.COMMAND_WORD + " "
            + ShowEventDetailsCommand.COMMAND_WORD;

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
        case SHOW:
            return new ShowEventDetailsParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
