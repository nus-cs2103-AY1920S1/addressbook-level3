package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

public class NoCommand extends Command {
    public static final String COMMAND_WORD = "no";

    public static final String MESSAGE_SUCCESS = "halt";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
