package seedu.sugarmummy.logic.commands.aesthetics;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

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
    public static final String ALTERNATIVE_COMMAND_WORD = "fontcolor";
    public static final String MESSAGE_SUCCESS = "Font colour has been set!";
    public static final String MESSAGE_CURRENT_FONT_COLOUR = "Your current font colour is: ";
    public static final String MESSAGE_USAGE = "\n" + COMMAND_WORD + ": Sets the font colour of this application "
            + "using either CSS colour names or hexadecimal alphanumeric characters representing rgb colours.\n\n"
            + "Parameter: COLOUR\n\n"
            + "Example: fontcolour turquoise\n"
            + "Example: fontcolour #00FF00"
            + "Example: fontcolour\n\n"
            + "This command may be combined with the background command using the prefix [bg/]."
            + "Example: fontcolour white bg/black\n"
            + "Example: fontcolour white bg//Users/bob/black.png s/cover";

    public static final String MESSAGE_NO_CHANGE = "The colour that you've keyed in is no different from "
            + "what has already been set in your current settings! As such, there's nothing for me to update :)";

    public static final String MESSAGE_COLOURS_TOO_CLOSE = "Oops! The font colour you have keyed in has colours "
            + "that are too close to the dominant colour of the current background. Please try changing your "
            + "background first or selecting a different font colour. Alternatively you may combine the fontcolour "
            + "command for the new font colour with a background command, using the prefix [bg/].\n"
            + "eg. fontcolour white bg/black\n"
            + "eg. fontcolour white bg//Users/bob/black.png s/cover";

    private Colour fontColour;
    private BackgroundCommand backgroundCommand;

    public FontColourCommand() {
    }

    public FontColourCommand(Colour fontColour, BackgroundCommand backgroundCommand) {
        this(fontColour);
        this.backgroundCommand = backgroundCommand;
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

        Colour bgColourToCompare = backgroundCommand == null
                ? model.getBackground().getDominantColour()
                : backgroundCommand.getBackground().getDominantColour();

        if (bgColourToCompare.isCloseTo(fontColour)) {
            throw new CommandException(MESSAGE_COLOURS_TOO_CLOSE);
        }

        Colour newColour = fontColour;
        if (previousColour.equals(newColour)) {
            if (backgroundCommand == null) {
                throw new CommandException(MESSAGE_NO_CHANGE);
            } else {
                if (backgroundCommand.getBackground().equals(model.getBackground())) {
                    throw new CommandException(MESSAGE_NO_CHANGE);
                }
            }
        }

        model.setFontColour(newColour);
        String updateMessage = "Colour has been changed from " + previousColour + " to " + newColour + ".\n";

        if (backgroundCommand != null) {
            if (!backgroundCommand.getBackground().equals(model.getBackground())) {
                updateMessage += (backgroundCommand.execute(model).getFeedbackToUser());
            } else {
                updateMessage += "Background: " + BackgroundCommand.MESSAGE_NO_CHANGE;
            }
        }

        return new CommandResult(MESSAGE_SUCCESS + " " + updateMessage.trim());
    }

    public Colour getFontColour() {
        return fontColour;
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return backgroundCommand != null ? DisplayPaneType.COLOUR_AND_BACKGROUND : DisplayPaneType.COLOUR;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            if (other instanceof FontColourCommand) {
                if ((this.fontColour == null && ((FontColourCommand) other).fontColour == null)
                        && (this.backgroundCommand == null && ((FontColourCommand) other).backgroundCommand == null)) {
                    return true;
                }

                if ((this.fontColour != null && ((FontColourCommand) other).fontColour == null)
                        || (this.fontColour == null && ((FontColourCommand) other).fontColour != null)
                        || (this.backgroundCommand != null && ((FontColourCommand) other).backgroundCommand == null)
                        || (this.backgroundCommand == null && ((FontColourCommand) other).backgroundCommand != null)) {
                    return false;
                }

                if ((this.fontColour != null && ((FontColourCommand) other).fontColour != null)
                        && this.backgroundCommand == null && ((FontColourCommand) other).backgroundCommand == null) {
                    return this.fontColour.equals(((FontColourCommand) other).fontColour);
                } else if ((this.fontColour == null && ((FontColourCommand) other).fontColour == null)
                        && this.backgroundCommand != null && ((FontColourCommand) other).backgroundCommand != null) {
                    return this.backgroundCommand.equals(((FontColourCommand) other).backgroundCommand);
                }

                assert this.fontColour != null;
                assert this.backgroundCommand != null;
                return this.fontColour.equals(((FontColourCommand) other).fontColour)
                        && this.backgroundCommand.equals(((FontColourCommand) other).backgroundCommand);
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return fontColour == null && backgroundCommand == null
                ? 0
                : fontColour == null
                        ? backgroundCommand.hashCode()
                        : backgroundCommand == null
                                ? fontColour.hashCode()
                                : Objects.hash(fontColour, backgroundCommand);
    }

    @Override
    public String toString() {
        return "FontColourCommand with attributes:\n"
                + "fontColour: " + fontColour + "\n"
                + "backgroundCommand: " + backgroundCommand;
    }

}
