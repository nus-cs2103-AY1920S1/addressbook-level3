package seedu.guilttrip.logic.commands.remindercommands;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;

/**
 * Writes message in generalReminder popup.
 */
public class WriteMessageCommand extends Command {
    public static final String COMMAND_WORD = "writeMessage";
    public static final String IMAGE_ADDED_MESSAGE = "Message successfully written to popup";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Write message in popup of reminderExamined.\n"
            + "Parameters: "
            + PREFIX_DESC + "MESSAGE."
            + PREFIX_COORDINATES + "X_COORDINATE, Y_COORDINATE\n"
            + " (You can include certain macros in your message to include key details) \n"
            + "MACRO  : MEANING\n"
            + "#eTyp# : ENTRY TYPE\n"
            + "#cat#  : CATEGORY\n"
            + "#desc# : ENTRY DESCRIPTION\n"
            + "#amt#  : ENTRY AMOUNT\n"
            + "#date# : ENTRY DATE\n"
            + "#tags# : ENTRY TAGS\n"
            + "#xAmt# : AMOUNT SPENT (only applicable for budget)\n"
            + "#p#    : BUDGET PERIOD (only applicable for budget)\n"
            + "#start#: BUDGET START DATE (only applicable for budget) \n"
            + "#end#  : BUDGET END DATE (only applicable for budget) \n"
            + "#freq# : AUTOEXPENSE FREQ (only applicable for AutoExpense)\n"
            + "#LT#   : AUTOEXPENSE LAST EXPENDITURE MADE (only applicable for AutoExpense)\n";

    public static final String REMINDER_POPUP_DISABLED = "Please enable pop up before viewing or editing.";
    public final int xCoordinate;
    public final int yCoordinate;

    public final String message;
    public WriteMessageCommand(String message, int xCoordinate, int yCoordinate) {
        this.message = message;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.getReminderSelected().willDisplayPopUp()) {
            throw new CommandException(REMINDER_POPUP_DISABLED);
        }
        model.getReminderSelected().getMessage().placeNote(message, xCoordinate, yCoordinate);
        return new CommandResult(IMAGE_ADDED_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof WriteMessageCommand)) {
            return false;
        } else {
            WriteMessageCommand otherCommand = (WriteMessageCommand) other;
            return this.message.equals(otherCommand.message)
                    && this.xCoordinate == otherCommand.xCoordinate
                    && this.yCoordinate == otherCommand.yCoordinate;
        }
    }
}
