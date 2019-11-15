package seedu.pluswork.logic.commands.multiline;

import static java.util.Objects.requireNonNull;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.model.Model;

public class YesCommand extends Command {
    public static final String COMMAND_WORD = "yes";

    public static final String MESSAGE_SUCCESS = "continue";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YesCommand);
    }
}
