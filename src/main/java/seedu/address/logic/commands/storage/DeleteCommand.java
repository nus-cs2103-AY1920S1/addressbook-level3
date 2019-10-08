package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Deletes a study plan identified using it's displayed index from the module planner.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the study plan identified by the index number used in the displayed study plan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STUDYPLAN_SUCCESS = "Deleted StudyPlan: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<StudyPlan> lastShownList = model.getFilteredStudyPlanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
        }

        StudyPlan studyPlanToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteStudyPlan(studyPlanToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDYPLAN_SUCCESS, studyPlanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
