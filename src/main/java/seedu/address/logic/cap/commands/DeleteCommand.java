package seedu.address.logic.cap.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.module.ModuleCode;
import seedu.address.model.common.Module;

/**
 * Deletes a module identified using it's module code from the modulo.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the index number used in the displayed module list.\n"
            + "Parameters: Module Code\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final ModuleCode moduleCode;

    public DeleteCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();
        Module moduleToDelete;

        for (Module module : lastShownList) {
            if (module.getModuleCode().equals(moduleCode)) {
                moduleToDelete = module;
                model.deleteModule(moduleToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
            }
        }

        throw new CommandException(Messages.MESSAGE_MODULE_DOES_NOT_EXIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && moduleCode.equals(((DeleteCommand) other).moduleCode)); // state check
    }
}
