package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;
import seedu.address.model.JokeList;

/**
 * This commands asks Elisa to give user a joke
 * */

public class JokeCommand extends Command {

    public static final String COMMAND_WORD = "joke";

    private JokeList jokeList;

    public JokeCommand(JokeList jokeList) {
        this.jokeList = jokeList;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new CommandResult(jokeList.getJoke());
    }

}
