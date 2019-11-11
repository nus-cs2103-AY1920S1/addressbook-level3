package seedu.tarence.logic.commands;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.Module;
import seedu.tarence.storage.Storage;

/**
 * Represents a followup to {@code DeletedModuleCommand} where the {@code Module} to be deleted has been verified as
 * a valid one in the application.
 */
public class DeleteModuleVerifiedCommand extends Command {

    private Module moduleToDelete;

    DeleteModuleVerifiedCommand(Module moduleToDelete) {
        this.moduleToDelete = moduleToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.deleteTutorialsFromModule(moduleToDelete);
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                moduleToDelete));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    @Override
    public boolean needsInput() {
        return true;
    }

    @Override
    public boolean needsCommand(Command command) {
        return command instanceof ConfirmYesCommand || command instanceof ConfirmNoCommand;
    }
}
