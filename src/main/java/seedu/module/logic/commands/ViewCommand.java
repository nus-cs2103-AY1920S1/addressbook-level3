package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Module;

/**
 * Views a Module identified by the module code. The viewed module could either be a tracked module
 * or an archived module yet to be tracked.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the Module identified by the module code.\n"
            + "Parameters: module code\n"
            + "Example: " + COMMAND_WORD + " cs2103t";

    public static final String MESSAGE_VIEW_MODULE_SUCCESS = "Viewing module: %1$s";
    public static final String MESSAGE_MODULE_NOT_FOUND = "This module is not found within the archive.";


    private final String moduleCode;

    public ViewCommand(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Predicate<Module> findModulePredicate = module -> module.getModuleCode().equals(moduleCode);

        model.updateFilteredArchivedModuleList(Model.PREDICATE_SHOW_ALL_MODULES);

        Module toDisplay = model.getFilteredModuleList().stream()
            .filter(findModulePredicate)
            .map(module -> (Module) module)
            .findFirst()
            .or(() -> model.getFilteredArchivedModuleList().stream()
                .filter(findModulePredicate)
                .findFirst())
            .orElseThrow(()
                -> new CommandException(MESSAGE_MODULE_NOT_FOUND));

        model.setDisplayedModule(toDisplay);
        return new CommandResult(String.format(MESSAGE_VIEW_MODULE_SUCCESS, moduleCode),
            false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && moduleCode.equals(((ViewCommand) other).moduleCode)); // state check
    }
}
