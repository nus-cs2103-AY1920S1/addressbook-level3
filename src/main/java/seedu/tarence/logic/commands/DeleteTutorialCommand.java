package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Deletes a tutorial identified using its displayed index from T.A.rence.
 * TODO: disallow deleting non-empty tutorials?
 */
public class DeleteTutorialCommand extends Command {

    public static final String COMMAND_WORD = "deleteTutorial";

    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS = "Deleted Tutorial: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the index number used in the displayed tutorial list.\n"
            + "or the specified tutorial name and module code.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1"
            + COMMAND_WORD + "tn/tut1"
            + COMMAND_WORD + "tn/Lab 1 m/PC1431";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "deletetut", "deleteclass", "deltutorial", "deltut", "delclass"};

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

        if (targetIndex.isPresent()) {
            if (targetIndex.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
            }

            Tutorial tutorialToDelete = lastShownList.get(targetIndex.get().getZeroBased());
            model.deleteTutorial(tutorialToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete));
        }

        if (targetModCode.isPresent()) {
            if (!model.hasTutorialInModule(targetModCode.get(), targetTutName.get())) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
            }
            for (Tutorial tutorial : lastShownList) {
                if (tutorial.getTutName().equals(targetTutName.get())
                    && tutorial.getModCode().equals(targetModCode.get())) {
                    model.deleteTutorial(tutorial);
                    return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorial));
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
                    model.deleteTutorial(tutorial);
                    return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorial));
                }
            }
        }

        return null;
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
                || (other instanceof DeleteTutorialCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTutorialCommand) other).targetIndex)); // state check
    }
}
