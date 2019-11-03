package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Deletes a tutorial identified using its displayed index from T.A.rence.
 */
public class DeleteTutorialCommand extends Command {

    public static final String COMMAND_WORD = "deleteTutorial";

    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS = "Deleted Tutorial: %1$s";

    public static final String MESSAGE_CONFIRM_DELETE_NONEMPTY_TUTORIAL = "WARNING: Tutorial %1$s "
            + "contains %2$d student(s). Are you sure you want to delete it?\n"
            + "(y/n)";

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "deletetut", "deleteclass", "deltutorial", "deltut", "delclass", "deletetutorial"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the index number used in the displayed tutorial list.\n"
            + "or the specified tutorial name and module code.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME\n"
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + "1\n"
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1\n"
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "PC1431\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private final Optional<Index> targetIndex;
    private final Optional<ModCode> targetModCode;
    private final Optional<TutName> targetTutName;

    public DeleteTutorialCommand(Index targetIndex) {
        this.targetIndex = Optional.of(targetIndex);
        this.targetModCode = Optional.empty();
        this.targetTutName = Optional.empty();
    }

    public DeleteTutorialCommand(ModCode modCode, TutName tutName) {
        this.targetIndex = Optional.empty();
        this.targetModCode = Optional.of(modCode);
        this.targetTutName = Optional.of(tutName);
    }

    public DeleteTutorialCommand(TutName tutName) {
        this.targetIndex = Optional.empty();
        this.targetModCode = Optional.empty();
        this.targetTutName = Optional.of(tutName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();
        Tutorial tutorialToDelete = null;

        if (targetIndex.isPresent()) {
            if (targetIndex.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
            }
            tutorialToDelete = lastShownList.get(targetIndex.get().getZeroBased());
        } else if (targetModCode.isPresent()) {
            ModCode modCode = targetModCode.get();
            TutName tutName = targetTutName.get();
            if (!model.hasTutorialInModule(modCode, tutName)) {
                // find tutorials with same name and similar modcodes, and similar names and same modcode
                List<ModCode> similarModCodes = getSimilarModCodesWithTutorial(modCode, tutName, model);
                List<TutName> similarTutNames = getSimilarTutNamesWithModule(modCode, tutName, model);
                if (similarModCodes.size() == 0 && similarTutNames.size() == 0) {
                    throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
                }

                String suggestedCorrections = createSuggestedCommands(similarModCodes, modCode,
                        similarTutNames, tutName, model);
                model.storePendingCommand(new SelectSuggestionCommand());
                return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                        modCode.toString() + " " + tutName.toString()) + suggestedCorrections);
            }
            for (Tutorial tutorial : lastShownList) {
                if (tutorial.getTutName().equals(targetTutName.get())
                    && tutorial.getModCode().equals(targetModCode.get())) {
                    tutorialToDelete = tutorial;
                    break;
                }
            }
        } else {
            int numberOfTutorialsOfName = model.getNumberOfTutorialsOfName(targetTutName.get());
            if (numberOfTutorialsOfName == 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_APPLICATION);
            } else if (numberOfTutorialsOfName > 1) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_MULTIPLE);
            }
            for (Tutorial tutorial : lastShownList) {
                if (tutorial.getTutName().equals(targetTutName.get())) {
                    tutorialToDelete = tutorial;
                    break;
                }
            }
        }

        requireNonNull(tutorialToDelete);
        if (!tutorialToDelete.getStudents().isEmpty()) {
            model.storePendingCommand(new DeleteTutorialVerifiedCommand(tutorialToDelete));
            return new CommandResult(String.format(MESSAGE_CONFIRM_DELETE_NONEMPTY_TUTORIAL,
                    tutorialToDelete,
                    tutorialToDelete.getStudents().size()));
        }

        model.deleteStudentsFromTutorial(tutorialToDelete);
        model.deleteTutorial(tutorialToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Generates and stores {@code AddStudentCommand}s from a list of {@code ModCode}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @return string representing the suggested {@code ModCode}s and their corresponding indexes for user selection.
     */
    private String createSuggestedCommands(List<ModCode> similarModCodes, ModCode originalModCode,
                                           List<TutName> similarTutNames, TutName originalTutName, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            suggestedCommands.add(new DeleteTutorialCommand(similarModCode, originalTutName));
            s.append(index).append(". ").append(similarModCode).append(", ").append(originalTutName).append("\n");
            index++;
        }
        for (TutName similarTutName: similarTutNames) {
            DeleteTutorialCommand newCommand = new DeleteTutorialCommand(originalModCode, similarTutName);
            if (suggestedCommands.stream()
                    .anyMatch(existingCommand -> existingCommand.equals(newCommand))) {
                continue;
            }
            suggestedCommands.add(newCommand);
            s.append(index).append(". ").append(originalModCode).append(", ").append(similarTutName).append("\n");
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
        // TODO: Need to consider case where targetIndex is not specified
        return other == this // short circuit if same object
                || (other instanceof DeleteTutorialCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTutorialCommand) other).targetIndex)
                && targetTutName.equals(((DeleteTutorialCommand) other).targetTutName)
                && targetModCode.equals(((DeleteTutorialCommand) other).targetModCode)); // state check
    }
}
