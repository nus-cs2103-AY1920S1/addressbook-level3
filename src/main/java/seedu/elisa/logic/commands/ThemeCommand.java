package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Changes the theme.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_INVALID = "Oh dumb dumb, choose only 'theme white' or 'theme black'";
    public static final String MESSAGE_SUCCESS = "Oh you don't like the color? Lets switch it up!";

    private String theme;

    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        switch(theme.trim()) {
        case "white":
        case "black":
            return new ThemeCommandResult(MESSAGE_SUCCESS, theme);
        default:
            throw new CommandException(MESSAGE_INVALID);
        }
    }
}
