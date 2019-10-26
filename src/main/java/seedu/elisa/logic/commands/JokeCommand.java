package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * This commands asks Elisa to give user a joke
 * */

public class JokeCommand extends Command {

    public static final String COMMAND_WORD = "joke";

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new CommandResult(model.getJoke());
    }

}
