package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_MODULE_IN_APPLICATION;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.finder.Finder;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.storage.Storage;

/**
 * Deletes a person identified using its displayed index from T.A.rence.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "deleteModule";

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "deletemod", "delmodule", "delmod"};

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    public static final String MESSAGE_CONFIRM_DELETE_NONEMPTY_MODULE = "WARNING: Module %1$s "
            + "contains %2$d tutorial(s).\n"
            + "Are you sure you want to delete it? (y/n)";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by either the index number used in the displayed module list,\n"
            + "or the specified module code.\n"
            + "Parameters:\n"
            + PREFIX_MODULE + "MODULE CODE\n"
            + "MODULE INDEX\n"
            + "Examples:\n"
            + COMMAND_WORD + " "
            + "1\n"
            + COMMAND_WORD + " "
            + PREFIX_MODULE + "GER1000\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

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
            ModCode modCode = targetModCode.get();
            if (!model.hasModuleOfCode(modCode)) {
                List<ModCode> similarModCodes = new Finder(model).findSimilarModCodes(modCode);
                if (similarModCodes.size() == 0) {
                    throw new CommandException(MESSAGE_INVALID_MODULE_IN_APPLICATION);
                }

                String suggestedCorrections = createSuggestedCommands(similarModCodes, model);
                model.storePendingCommand(new SelectSuggestionCommand());
                return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Module",
                        modCode) + suggestedCorrections);
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
                    moduleToDelete.getModCode(),
                    moduleToDelete.getTutorials().size()));
        }

        model.deleteTutorialsFromModule(moduleToDelete);
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Generates and stores {@code DeleteModuleCommand}s from a list of {@code ModCode}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @return string representing the suggested {@code ModCode}s and their corresponding indexes for user selection.
     */
    private String createSuggestedCommands(List<ModCode> similarModCodes, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            suggestedCommands.add(new DeleteModuleCommand(similarModCode));
            s.append(index).append(". ").append(similarModCode).append("\n");
            index++;
        }
        String suggestedCorrections = s.toString();
        model.storeSuggestedCommands(suggestedCommands, suggestedCorrections);
        return suggestedCorrections;
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
                && targetIndex.equals(((DeleteModuleCommand) other).targetIndex)
                && targetModCode.equals(((DeleteModuleCommand) other).targetModCode)); // state check
    }
}
