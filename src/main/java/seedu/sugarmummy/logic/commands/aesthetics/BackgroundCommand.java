package seedu.sugarmummy.logic.commands.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT;

import java.util.Objects;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Edits the details of an existing user in the address book.
 */
public class BackgroundCommand extends Command {

    public static final String COMMAND_WORD = "bg";
    public static final String MESSAGE_SUCCESS = "Background has been set!";
    public static final String MESSAGE_CURRENT_BACKGROUND = "Your current background is: ";
    public static final String MESSAGE_USAGE = "\n" + COMMAND_WORD + ": Sets the background of this application "
            + "using either CSS colour names, hexadecimal alphanumeric characters representing rgb colours or "
            + "a file path specifying the image to be used.\n\n"
            + "Currently known background size inputs: auto, cover, contain\n"
            + "Currently known background repeat inputs: repeat-x, repeat-y, repeat, space, round, no-repeat\n\n"
            + "Parameter: COLOUR or PATH [s/BACKGROUND SIZE] [r/BACKGROUND REPEAT]\n\n"
            + "Example: background turquoise\n"
            + "Example: background #00FF00\n"
            + "Example: background /Users/Bob/bg.jpg s/cover\n"
            + "Example: background r/no-repeat\n"
            + "Example: background\n\n"
            + "This command may be commbined with the fontcolour command, using the prefix [fontcolour/].\n"
            + "Example: bg white fontcolour/black\n"
            + "Example: bg /Users/bob/black.png s/cover fontcolor/white";

    public static final String MESSAGE_NO_CHANGE = "The colour, path, or wrap settings you've keyed in is "
            + "no different from what has already been set in your current settings! As such, there's nothing "
            + "for me to update :)";

    public static final String MESSAGE_COLOURS_TOO_CLOSE = "Oops! The background you have keyed in has dominant "
            + "colours that are too close to the current font colour. Please try changing your font colour first or "
            + "selecting a different background. Alternatively you may combine the background "
            + "command for the new background with a fontcolour command, using the prefix [fontcolour/].\n"
            + "Example: bg white fontcolour/black\n"
            + "Example: bg /Users/bob/black.png s/cover fontcolor/white";

    private Background background;
    private FontColourCommand fontColourCommand;

    public BackgroundCommand() {
    }

    public BackgroundCommand(Background background, FontColourCommand fontColourCommand) {
        this(background);
        this.fontColourCommand = fontColourCommand;
    }

    public BackgroundCommand(Background background) {
        this.background = background;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Background previousBackground = model.getBackground();

        if (this.background == null) {

            StringBuilder sb = new StringBuilder();

            if (previousBackground != null) {
                sb.append(previousBackground.toString());
            }

            if (!previousBackground.isBackgroundColour()) {
                sb.append("\nBackground-size: ").append(previousBackground.getBgSize());
                sb.append("\nBackground-repeat: ").append(previousBackground.getBgRepeat());
            }

            return new CommandResult(MESSAGE_CURRENT_BACKGROUND + sb.toString());
        }

        Colour fontColourToCompare = fontColourCommand == null
                ? model.getFontColour()
                : fontColourCommand.getFontColour();

        if (fontColourToCompare.isCloseTo(background.getDominantColour())) {
            throw new CommandException(MESSAGE_COLOURS_TOO_CLOSE);
        }

        Background newBackground = background;

        StringBuilder updateMessage = new StringBuilder();

        if (previousBackground.isBackgroundColour()
                && (!newBackground.isEmpty()
                && (!newBackground.getBgSize().isEmpty() || !newBackground.getBgRepeat().isEmpty()))) {
            throw new CommandException(String.format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT, MESSAGE_USAGE));
        }

        background.merge(previousBackground);

        if (previousBackground.equals(newBackground)) {
            if (fontColourCommand == null) {
                throw new CommandException(MESSAGE_NO_CHANGE);
            } else {
                if (fontColourCommand.getFontColour().equals(model.getFontColour())) {
                    throw new CommandException(MESSAGE_NO_CHANGE);
                }
            }
        }

        model.setBackground(newBackground);

        if (!newBackground.isBackgroundColour() && !previousBackground.isBackgroundColour()) {
            if (!newBackground.getBackgroundPicPath().equals(previousBackground.getBackgroundPicPath())) {
                updateMessage.append("- Background has been changed from ").append(previousBackground)
                        .append(" to ").append(newBackground).append(".\n");
            }
            if (!newBackground.getBgSize().equals(previousBackground.getBgSize())) {
                updateMessage.append("- Background size has been changed from ").append(previousBackground.getBgSize())
                        .append(" to ").append(newBackground.getBgSize()).append(".\n");
            }
            if (!newBackground.getBgRepeat().equals(previousBackground.getBgRepeat())) {
                updateMessage.append("- Background repeat has been changed from ")
                        .append(previousBackground.getBgRepeat())
                        .append(" to ").append(newBackground.getBgRepeat()).append(".\n");
            }
        } else {
            updateMessage.append("- Background has been changed from ").append(previousBackground)
                    .append(" to ").append(newBackground).append(".\n");
        }

        if (fontColourCommand != null) {
            if (!fontColourCommand.getFontColour().equals(model.getFontColour())) {
                updateMessage.append(fontColourCommand.execute(model).getFeedbackToUser());
            } else {
                updateMessage.append("Font Colour: ").append(FontColourCommand.MESSAGE_NO_CHANGE);
            }
        }

        return new CommandResult(MESSAGE_SUCCESS + "\n" + updateMessage.toString().trim());
    }

    public Background getBackground() {
        return background;
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return fontColourCommand != null ? DisplayPaneType.COLOUR_AND_BACKGROUND : DisplayPaneType.BACKGROUND;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            if (other instanceof BackgroundCommand) {
                if ((this.background == null && ((BackgroundCommand) other).background == null)
                        && (this.fontColourCommand == null && ((BackgroundCommand) other).fontColourCommand == null)) {
                    return true;
                }

                if ((this.background != null && ((BackgroundCommand) other).background == null)
                        || (this.background == null && ((BackgroundCommand) other).background != null)
                        || (this.fontColourCommand != null && ((BackgroundCommand) other).fontColourCommand == null)
                        || (this.fontColourCommand == null && ((BackgroundCommand) other).fontColourCommand != null)) {
                    return false;
                }

                if ((this.background != null && ((BackgroundCommand) other).background != null)
                        && this.fontColourCommand == null && ((BackgroundCommand) other).fontColourCommand == null) {
                    return this.background.equals(((BackgroundCommand) other).background);
                } else if ((this.background == null && ((BackgroundCommand) other).background == null)
                        && this.fontColourCommand != null && ((BackgroundCommand) other).fontColourCommand != null) {
                    return this.fontColourCommand.equals(((BackgroundCommand) other).fontColourCommand);
                }

                assert this.background != null;
                assert this.fontColourCommand != null;

                return this.background.equals(((BackgroundCommand) other).background)
                        && this.fontColourCommand.equals(((BackgroundCommand) other).fontColourCommand);
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return background == null && fontColourCommand == null
                ? 0
                : background == null
                        ? fontColourCommand.hashCode()
                        : fontColourCommand == null
                                ? background.hashCode()
                                : Objects.hash(background, fontColourCommand);
    }

    @Override
    public String toString() {
        return "BackgroundCommnand with attributes:\n"
                + "background: " + background + "\n"
                + "fontColourCommand: " + fontColourCommand;
    }

}
