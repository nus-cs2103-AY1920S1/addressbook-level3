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
import seedu.address.logic.parser.currency.EditCurrencyParser;
import seedu.address.logic.parser.diary.DiaryParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.expense.ExpenseManagerParser;
import seedu.address.logic.parser.expense.edit.EditExpenseParser;
import seedu.address.logic.parser.inventory.InventoryViewParser;
import seedu.address.logic.parser.itinerary.dayview.DayViewParser;
import seedu.address.logic.parser.itinerary.dayview.edit.EditDayParser;
import seedu.address.logic.parser.itinerary.eventview.EventViewParser;
import seedu.address.logic.parser.itinerary.eventview.edit.EditEventParser;
import seedu.address.logic.parser.itinerary.overallview.ItineraryViewParser;
import seedu.address.logic.parser.navbar.NavbarCommand;
import seedu.address.logic.parser.navbar.NavbarViewParser;
import seedu.address.logic.parser.trips.TripManagerParser;
import seedu.address.logic.parser.trips.edit.EditTripParser;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.appstatus.PageType;

/**
 * Parses user input.
 */
public class TravelPalParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)", Pattern.DOTALL);

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

        PageParser<Command> commonParser;

        try {
            Command commonCommand = new CommonParser().parse(commandWord, arguments);
            return commonCommand;
        } catch (ParseException ex) {
            logger.info("User command executed was not a common command");
        }

        PageType currentPage = pageStatus.getPageType();

        switch (currentPage) {
        case TRIP_MANAGER:
            return new TripManagerParser().parse(commandWord, arguments);
        case ADD_TRIP:
            return new EditTripParser().parse(commandWord, arguments);
        case ADD_DAY:
            return new EditDayParser().parse(commandWord, arguments);
        case ADD_EVENT:
            return new EditEventParser().parse(commandWord, arguments);
        case ITINERARY:
            return parseNavbarPageCommand(commandWord, arguments, new ItineraryViewParser(), NavbarCommand.ITINERARY);
        case OVERALL_VIEW:
            return parseNavbarPageCommand(commandWord, arguments, new DayViewParser(), NavbarCommand.DAYS);
        case EVENT_PAGE:
            return parseNavbarPageCommand(commandWord, arguments, new EventViewParser());
        case PRETRIP_INVENTORY:
            return parseNavbarPageCommand(commandWord, arguments, new InventoryViewParser(), NavbarCommand.INVENTORY);
        case EXPENSE_MANAGER:
            return parseNavbarPageCommand(commandWord, arguments, new ExpenseManagerParser(), NavbarCommand.EXPENSE);
        case ADD_EXPENSE:
            return new EditExpenseParser().parse(commandWord, arguments);
        case ADD_CURRENCY:
            return new EditCurrencyParser().parse(commandWord, arguments);
        case DIARY:
            return parseNavbarPageCommand(commandWord, arguments, new DiaryParser(), NavbarCommand.DIARY);
        case BOOKINGS:
            return parseNavbarPageCommand(commandWord, arguments, new BookingsParser(), NavbarCommand.BOOKINGS);
        case CONTACTS_MANAGER:
        default:
            throw new ParseException(UNKNOWN_PAGE_MESSAGE);
        }
    }

    /**
     * Tries to use the {@link NavbarViewParser} to parse the {@code commandWord} and {@code arguments},
     * failing which, the {@code altPageParser} is used.
     * This method, with {@link NavbarCommand} and {@link NavbarViewParser} handles parsing for pages
     * where it is possible to execute navbar navigation commands.
     *
     * @param commandWord The String command word to parse.
     * @param arguments The String arguments to parse.
     * @param altPageParser The alternative {@link PageParser} to use if NavbarViewParser fails.
     * @param precludedPage The {@link NavbarCommand} page to preclude from navigating to.
     * @return The parsed {@link Command}.
     * @throws ParseException If the alternate page parser fails to parse the given commandWord or arguments.
     */
    private Command parseNavbarPageCommand(String commandWord, String arguments,
            PageParser altPageParser, NavbarCommand precludedPage) throws ParseException {
        try {
            NavbarViewParser navbarViewParser = new NavbarViewParser(precludedPage);
            Command command = navbarViewParser.parse(commandWord, arguments);
            return command;
        } catch (ParseException ex) {
            logger.info("User command executed was not a navbar command.");
            return altPageParser.parse(commandWord, arguments);
        }
    }

    /**
     * Parser for navigation bar for pages not navigable to by the bar and does not require a
     * precluding page.
     *
     * @param commandWord The String command word to parse.
     * @param arguments The String arguments to parse.
     * @param altPageParser The alternative {@link PageParser} to use if NavbarViewParser fails.
     * @return The parsed {@link Command}.
     * @throws ParseException If the alternate page parser fails to parse the given commandWord or arguments.
     */
    private Command parseNavbarPageCommand(String commandWord, String arguments,
                                           PageParser altPageParser) throws ParseException {
        try {
            NavbarViewParser navbarViewParser = new NavbarViewParser();
            Command command = navbarViewParser.parse(commandWord, arguments);
            return command;
        } catch (ParseException ex) {
            logger.info("User command executed was not a navbar command.");
            return altPageParser.parse(commandWord, arguments);
        }
    }

}
