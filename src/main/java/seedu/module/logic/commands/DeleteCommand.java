package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.predicate.SameModuleCodePredicate;

/**
 * Deletes a Module identified using it's displayed index from the Module book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Module identified by the Module code in the displayed Module list.\n"
            + "Parameters: MODULE CODE\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted module: %1$s";
    public static final String MESSAGE_MODULE_NOT_FOUND =
            "This module is not found within the list of tracked modules.";

    private final SameModuleCodePredicate predicate;

    public DeleteCommand(SameModuleCodePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TrackedModule trackedModuleToDelete = model.findTrackedModule(predicate).orElseThrow(()
            -> new CommandException(MESSAGE_MODULE_NOT_FOUND));

        model.deleteModule(trackedModuleToDelete);
        model.showAllTrackedModules();
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, trackedModuleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && predicate.equals(((DeleteCommand) other).predicate)); // state check
    }
}
