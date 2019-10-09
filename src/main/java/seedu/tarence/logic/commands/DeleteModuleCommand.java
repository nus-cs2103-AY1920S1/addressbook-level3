package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;

/**
 * Deletes a person identified using its displayed index from T.A.rence.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "deleteMod";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_CONFIRM_DELETE_NONEMPTY_MODULE = "WARNING: Module %1$s "
            + "contains %2$d tutorial(s). Are you sure you want to delete it?\n"
            + "(y/n)";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by either the index number used in the displayed module list,\n"
            + "or the specified module code.\n"
            + "Examples: " + COMMAND_WORD + " 1"
            + COMMAND_WORD + " m/GER1000";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "deletemod", "delmodule", "delmod"};

    private final Optional<Index> targetIndex;
    private final Optional<ModCode> targetModCode;

    public DeleteModuleCommand(Index targetIndex) {
        this.targetIndex = Optional.of(targetIndex);
        this.targetModCode = Optional.empty();
    }

    public DeleteModuleCommand(ModCode modCode) {
        this.targetIndex = Optional.empty();
        this.targetModCode = Optional.of(modCode);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Module> lastShownList = model.getFilteredModuleList();
        Module moduleToDelete = null;

        if (targetIndex.isPresent()) {
            if (targetIndex.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            }
            moduleToDelete = lastShownList.get(targetIndex.get().getZeroBased());
        } else {
            if (!model.hasModuleOfCode(targetModCode.get())) {
                throw new CommandException(Messages.MESSAGE_INVALID_MODULE_IN_APPLICATION);
            }

            for (Module module : lastShownList) {
                if (module.getModCode().equals(targetModCode.get())) {
                    moduleToDelete = module;
                    break;
                }
            }
        }

        requireNonNull(moduleToDelete);
        if (!moduleToDelete.getTutorials().isEmpty()) {
            model.storePendingCommand(new DeleteModuleVerifiedCommand(moduleToDelete));
            return new CommandResult(String.format(MESSAGE_CONFIRM_DELETE_NONEMPTY_MODULE,
                    moduleToDelete,
                    moduleToDelete.getTutorials().size()));
        }

        model.deleteTutorialsFromModule(moduleToDelete);
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteModuleCommand) other).targetIndex)); // state check
    }
}
