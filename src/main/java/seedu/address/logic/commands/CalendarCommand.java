package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.ui.feature.Feature;

/**
 * Allows user to navigate the date of the calendar and view training and performance details on a
 * specified date.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = "calendar" + ": Navigates the calendar feature. "
            + "The calendar command has 2 possible variations.\n"
            + "1) Specify month and year to view calendar for particular month.\nParameters: "
            + "MMYYYY\nExample: calendar 062019\n2) Specify day, month and year to view details for "
            + "particular date.\nParameters: DDMMYYYY\nExample: calendar 09062019";

    public static final String MESSAGE_SUCCESS_1 = "Viewing details for: ";
    public static final String MESSAGE_SUCCESS_2 = "Viewing calendar for: ";
    public static final String MESSAGE_INVALID_DATE = "You have provided an invalid date.";

    private final AthletickDate date;

    public CalendarCommand(AthletickDate date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (date.getType()) {
        case 1:
            String resultMsg =
                    MESSAGE_SUCCESS_1 + date.toString();
            return new CommandResult(resultMsg, new Feature("calendar"), date, model);
        case 2:
            String resultMsg2 = MESSAGE_SUCCESS_2 + date.getMth() + " " + date.getYear();
            return new CommandResult(resultMsg2, new Feature("calendar"), date, model);
        default:
            throw new CommandException(MESSAGE_INVALID_DATE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarCommand // instanceof handles nulls
                && date.equals(((CalendarCommand) other).date));
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
    @Override
    public String toString() {
        return "Calendar Command";
    }
}
