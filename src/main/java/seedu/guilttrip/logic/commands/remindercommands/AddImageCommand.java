package seedu.guilttrip.logic.commands.remindercommands;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;


/**|
 * Adds an image to reminder message.
 */
public class AddImageCommand extends Command {
    public static final String COMMAND_WORD = "addImage";
    public static final String IMAGE_ADDED_MESSAGE = "Image successfully added to popup";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds image to popup of reminderExamined.\n"
            + "Parameters: \n"
            + PREFIX_DESC + "IMAGE NAME "
            + PREFIX_COORDINATES + "COORDINATES "
            + "Example: " + COMMAND_WORD
            + PREFIX_DESC + " Potato"
            + PREFIX_COORDINATES + " 1,2 \n";
    public static final String REMINDER_POPUP_DISABLED = "Please enable pop up before viewing or editing.";

    public final String imageName;
    public final int xCoordinate;
    public final int yCoordinate;
    public AddImageCommand (String imageName, int xCoordinate, int yCoordinate) {
        this.imageName = imageName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.getReminderSelected().willDisplayPopUp()) {
            throw new CommandException(REMINDER_POPUP_DISABLED);
        }
        try {
            model.getReminderSelected().getMessage().placeImage(imageName, xCoordinate, yCoordinate);
            return new CommandResult(IMAGE_ADDED_MESSAGE);
        } catch (java.io.IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof AddImageCommand)) {
            return false;
        } else {
            AddImageCommand otherCommand = (AddImageCommand) other;
            return this.imageName.equals(otherCommand.imageName)
                    && this.xCoordinate == otherCommand.xCoordinate
                    && this.yCoordinate == otherCommand.yCoordinate;
        }
    }
}
