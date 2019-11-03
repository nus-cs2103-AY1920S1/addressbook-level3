package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.module.model.Model;

/**
 * Lists all Modules in the Module book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tracked Modules";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.showAllTrackedModules();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
