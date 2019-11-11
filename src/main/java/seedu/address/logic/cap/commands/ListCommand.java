package seedu.address.logic.cap.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.cap.Model.PREDICATE_SHOW_ALL_MODULES;

import seedu.address.model.cap.Model;

/**
 * Lists all modules to the user, often needed the find command.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all modules.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
