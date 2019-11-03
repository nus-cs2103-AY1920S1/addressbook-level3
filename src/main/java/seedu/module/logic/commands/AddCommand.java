package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.predicate.SameModuleCodePredicate;

/**
 * Adds module to be tracked.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Module to the ModuleBook. "
            + "Parameters: "
            + "module code\n"
            + "Example: " + COMMAND_WORD + " "
            + "cs2103";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the module book";
    public static final String MESSAGE_MODULE_NOT_FOUND = "This module is not found within the archive.";

    private final SameModuleCodePredicate predicate;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddCommand(SameModuleCodePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredArchivedModuleList(Model.PREDICATE_SHOW_ALL_MODULES);

        ArchivedModule archivedModule = model.findArchivedModule(predicate).orElseThrow(()
            -> new CommandException(MESSAGE_MODULE_NOT_FOUND));

        TrackedModule toAdd = new TrackedModule(archivedModule);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        model.showAllTrackedModules();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && predicate.equals(((AddCommand) other).predicate));
    }
}
