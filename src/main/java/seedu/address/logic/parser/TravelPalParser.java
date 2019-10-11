package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.common.HelpCommand;
import seedu.address.logic.parser.bookings.BookingsParser;
import seedu.address.logic.parser.common.CommonParser;
import seedu.address.logic.parser.contacts.ContactsParser;
import seedu.address.logic.parser.diary.DiaryParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.expense.ExpenseParser;
import seedu.address.logic.parser.inventory.InventoryParser;
import seedu.address.logic.parser.itinerary.dayview.DayViewParser;
import seedu.address.logic.parser.itinerary.dayview.edit.EditDayParser;
import seedu.address.logic.parser.itinerary.eventview.EventViewParser;
import seedu.address.logic.parser.itinerary.eventview.edit.EditEventParser;
import seedu.address.logic.parser.itinerary.overallview.ItineraryViewParser;
import seedu.address.logic.parser.preferences.PreferencesParser;
import seedu.address.logic.parser.trips.TripManagerParser;
import seedu.address.logic.parser.trips.edit.EditTripParser;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.appstatus.PageType;

/**
 * Parses user input.
 *
 */
public class TravelPalParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /** Message to display when the command for the page has not been implemented. */
    private static final String UNKNOWN_PAGE_MESSAGE = "The commands for the page have not been implemented yet!";

    /** Logger associated with TravelPalParser instance. */
    private static final Logger logger = LogsCenter.getLogger(TravelPalParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param pageStatus the PageStatus instance with which to parse this input
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, PageStatus pageStatus) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toUpperCase();
        final String arguments = matcher.group("arguments");

        PageParser commonParser;

        try {
            Command commonCommand = new CommonParser().parse(commandWord, arguments);
            return commonCommand;
        } catch (ParseException ex) {
            logger.info("User command executed was not a common command");
        }

        PageType currentPage = pageStatus.getPageType();

        switch (currentPage) {
        case PREFERENCES:
            return new PreferencesParser().parse(commandWord, arguments);
        case TRIP_MANAGER:
            return new TripManagerParser().parse(commandWord, arguments);
        case ADD_TRIP:
            return new EditTripParser().parse(commandWord, arguments);
        case ADD_DAY:
            return new EditDayParser().parse(commandWord, arguments);
        case ADD_EVENT:
            return new EditEventParser().parse(commandWord, arguments);
        case ITINERARY:
            return new ItineraryViewParser().parse(commandWord, arguments);
        case OVERALL_VIEW:
            return new DayViewParser().parse(commandWord, arguments);
        case EVENT_PAGE:
            return new EventViewParser().parse(commandWord, arguments);
        case PRETRIP_INVENTORY:
            return new InventoryParser().parse(commandWord, arguments);
        case EXPENSE_MANAGER:
            return new ExpenseParser().parse(commandWord, arguments);
        case DIARY:
            return new DiaryParser().parse(commandWord, arguments);
        case CONTACTS_MANAGER:
            return new ContactsParser().parse(commandWord, arguments);
        case BOOKINGS:
            return new BookingsParser().parse(commandWord, arguments);
        default:
            throw new ParseException(UNKNOWN_PAGE_MESSAGE);
        }
    }
}
