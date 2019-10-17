package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ImportReplaceCommand extends Command implements MutatorCommand {
    public static final String COMMAND_WORD = "import-replace";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
