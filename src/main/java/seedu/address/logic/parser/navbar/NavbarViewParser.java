package seedu.address.logic.parser.navbar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.diary.EnterDiaryParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.sidebar.EnterDayPageParser;
import seedu.address.logic.parser.sidebar.EnterItineraryPageParser;
import seedu.address.logic.parser.sidebar.EnterTripManagerParser;

public class NavbarViewParser implements PageParser {

    /** Concatenation of all NavbarCommand enum types. */
    public static String MESSAGE_COMMAND_TYPES = "Available navigation commands :"
            + Arrays.stream(NavbarCommand.values())
                    .map(navbarCommandEnum -> navbarCommandEnum.toString())
                    .reduce("", (firstType, secondType) -> {
                        return firstType + " " + secondType;
                    })
            + "\n";

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        NavbarCommand commandType;
        try {
            commandType = NavbarCommand.valueOf(command);
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
        //Add your navbar command type here, and in the enum.
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
