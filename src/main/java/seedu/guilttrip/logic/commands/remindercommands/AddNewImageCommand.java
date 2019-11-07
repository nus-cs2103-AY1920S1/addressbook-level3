package seedu.guilttrip.logic.commands.remindercommands;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_BOOL;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PARAM;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;

/**
 * Adds image to generalReminder pop up message. Loads message from system files (not in program).
 * Option to save image in program.
 */
public class AddNewImageCommand extends Command {
    public static final String COMMAND_WORD = "addNewImage";
    public static final String IMAGE_ADDED_MESSAGE = "Image successfully added to popup";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds image to popup of reminderExamined.\n"
            + "Parameters: \n"
            + PREFIX_DESC + "IMAGE NAME "
            + PREFIX_PARAM + "IMAGE PATH "
            + PREFIX_COORDINATES + "COORDINATES "
            + PREFIX_BOOL + "SAVE PICTURE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Potato "
            + PREFIX_PARAM + "C:\\Users\\Hong\\Pictures\\potato.jpg "
            + PREFIX_COORDINATES + "1,2 "
            + PREFIX_BOOL + "True \n";

    public static final String REMINDER_POPUP_DISABLED = "Please enable pop up before viewing or editing.";
    public static final String MESSAGE_CONSTRAINTS = "Please enter a valid image path.\n"
            + "Image name should only contain alpha-numerical characters"
            + "Please enter either true or false for boolean value.";

    public final String imageName;
    public final String imagePath;
    public final int xCoordinate;
    public final int yCoordinate;
    public final boolean willSave;

    public AddNewImageCommand (String imageName, String imagePath, int xCoordinate, int yCoordinate, boolean willSave) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.willSave = willSave;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.getReminderSelected().willDisplayPopUp()) {
            throw new CommandException(REMINDER_POPUP_DISABLED);
        }
        try {
            model.getReminderSelected().getMessage().placeNewImage(imageName, imagePath, xCoordinate, yCoordinate, willSave);
            String successMessage = IMAGE_ADDED_MESSAGE;
            if (willSave) {
                successMessage += " and saved";
            }
            return new CommandResult(successMessage);
        } catch (java.io.IOException e) {
            throw new CommandException(e.getMessage() + "filepath: " + imagePath);
        }
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof AddImageCommand)) {
            return false;
        } else {
            AddNewImageCommand otherCommand = (AddNewImageCommand) other;
            return this.imageName.equals(otherCommand.imageName)
                    && this.imagePath.equals(otherCommand.imagePath)
                    && this.xCoordinate == otherCommand.xCoordinate
                    && this.yCoordinate == otherCommand.yCoordinate
                    && this.willSave == otherCommand.willSave;
        }
    }
}
