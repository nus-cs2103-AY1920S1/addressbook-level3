package seedu.address.logic.parser.navbar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.TravelPalParser;
import seedu.address.logic.parser.diary.EnterDiaryParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inventory.EnterInventoryParser;
import seedu.address.logic.parser.sidebar.EnterBookingsParser;
import seedu.address.logic.parser.sidebar.EnterDayPageParser;
import seedu.address.logic.parser.sidebar.EnterExpenseManagerParser;
import seedu.address.logic.parser.sidebar.EnterItineraryPageParser;
import seedu.address.logic.parser.sidebar.EnterTripManagerParser;

/**
 * Wrapping {@link PageParser} used in {@link TravelPalParser} for {@link PageParser} that support
 * the common TravelPal side navbar navigation operations.
 */
public class NavbarViewParser implements PageParser {

    /** Concatenation of all NavbarCommand enum types. */
    public static final String MESSAGE_COMMAND_TYPES = "Available navigation commands :"
            + Arrays.stream(NavbarCommand.values())
                    .map(navbarCommandEnum -> navbarCommandEnum.toString().toLowerCase())
                    .reduce("", (firstType, secondType) -> {
                        return firstType + " " + secondType;
                    })
            + "\n";

    private static final String MESSAGE_NAVIGATE_SAME_PAGE = "You are already at %1$s page!";

    private NavbarCommand precludedPage;

    public NavbarViewParser(NavbarCommand precludedPage) {
        this.precludedPage = precludedPage;
    }

    public NavbarViewParser() {
    }

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        NavbarCommand commandType;

        try {
            commandType = NavbarCommand.valueOf(command);

            if (precludedPage != null && commandType == precludedPage) {
                throw new IllegalArgumentException(String.format(MESSAGE_NAVIGATE_SAME_PAGE, precludedPage));
            }
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
        case DIARY:
            return new EnterDiaryParser().parse(arguments);
        case EXPENSE:
            return new EnterExpenseManagerParser().parse(arguments);
        case INVENTORY:
            return new EnterInventoryParser().parse(arguments);
        case BOOKINGS:
            return new EnterBookingsParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
