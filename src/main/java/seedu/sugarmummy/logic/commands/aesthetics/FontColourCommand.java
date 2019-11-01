package seedu.sugarmummy.logic.commands.aesthetics;

import static java.util.Objects.requireNonNull;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Edits the details of an existing user in the address book.
 */
public class FontColourCommand extends Command {

    public static final String COMMAND_WORD = "fontcolour";
    public static final String MESSAGE_SUCCESS = "Font colour has been set!";
    public static final String MESSAGE_CURRENT_FONT_COLOUR = "Your current font colour is: ";
    public static final String MESSAGE_USAGE = "\n" + COMMAND_WORD + ": Sets the font colour of this application "
            + "using either CSS colour names or hexadecimal alphanumeric characters representing rgb colours.\n\n"
            + "Parameter: COLOUR\n\n"
            + "Example: fontcolour turquoise\n"
            + "Example: fontcolour #00FF00"
            + "Example: fontcolour";
    private static final String MESSAGE_NO_CHANGE = "The colour that you've keyed in is no different from "
            + "what has already been set in your current settings! As such, there's nothing for me to update :)";

    private Colour fontColour;

    public FontColourCommand() {
    }

    public FontColourCommand(Colour fontColour) {
        this.fontColour = fontColour;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Colour previousColour = model.getFontColour();

        if (this.fontColour == null) {
            return new CommandResult(MESSAGE_CURRENT_FONT_COLOUR + previousColour.toString());
        }

        Colour newColour = fontColour;
        if (previousColour.equals(newColour)) {
            throw new CommandException(MESSAGE_NO_CHANGE);
        }
        model.setFontColour(newColour);
        String updateMessage = "Colour has been changed from " + previousColour + " to " + newColour + ".";
        return new CommandResult(MESSAGE_SUCCESS + " " + updateMessage);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.COLOUR;
    }
}
