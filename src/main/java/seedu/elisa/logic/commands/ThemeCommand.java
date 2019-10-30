package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Changes the theme.
 */
public class ThemeCommand extends Command {

    private String theme;
    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_SUCCESS = "Oh you don't like the color? Lets switch it up!";

    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new ThemeCommandResult(MESSAGE_SUCCESS, theme);
    }
}
