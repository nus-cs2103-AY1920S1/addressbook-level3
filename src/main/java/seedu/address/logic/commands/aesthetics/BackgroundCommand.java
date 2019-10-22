package seedu.address.logic.commands.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.aesthetics.Background;
import seedu.address.ui.DisplayPaneType;

/**
 * Edits the details of an existing user in the address book.
 */
public class BackgroundCommand extends Command {

    public static final String COMMAND_WORD = "bg";
    public static final String MESSAGE_SUCCESS = "Background has been set!";
    public static final String MESSAGE_USAGE = "\n" + COMMAND_WORD + ": Sets the background of this application "
            + "using either CSS colour names, hexadecimal alphanumeric characters representing rgb colours or " 
            + "a file path specifying the image to be used.\n\n"
            + "Parameter: COLOUR or PATH [wrap/]\n\n"
            + "Example: background turquoise\n"
            + "Example background #00FF00"
            + "Example background /Users/Bob/bg.jpg wrap/cover";
    private static final String MESSAGE_NO_CHANGE = "The colour, path, or wrap settings you've keyed in is "
            + "no different from what has already been set in your current settings! As such, there's nothing " 
            + "for me to update :)";

    private Background background;

    public BackgroundCommand(Background background) {
        this.background = background;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Background previousBackground = model.getBackground();
        Background newBackground = background;

        StringBuilder updateMessage = new StringBuilder();

        if (previousBackground.isBackgroundColour() &&
                (newBackground.isEmpty()
                        && !newBackground.getBgSize().isEmpty() || !newBackground.getBgRepeat().isEmpty())) {
            throw new CommandException(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT);
        }

        background.merge(previousBackground);

        if (previousBackground.equals(newBackground)) {
            throw new CommandException(MESSAGE_NO_CHANGE);
        }
        model.setBackground(newBackground);

        System.out.println("new" + newBackground + newBackground.getBgSize() + newBackground.getBgRepeat());
        System.out.println("old" + previousBackground + previousBackground.getBgSize() + previousBackground.getBgRepeat());

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

        return new CommandResult(MESSAGE_SUCCESS + "\n" + updateMessage.toString());
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BACKGROUND;
    }
}
