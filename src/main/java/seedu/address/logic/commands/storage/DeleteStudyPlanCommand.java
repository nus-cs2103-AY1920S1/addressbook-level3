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
public class DeleteStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "removeplan";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing a study plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the study plan identified by the index number used in the displayed study plan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STUDYPLAN_SUCCESS = "Deleted StudyPlan: %1$s";
    public static final String MESSAGE_NO_MORE_STUDYPLAN = "You don't have any study plan currently. Create now!";


    private final Index targetIndex;

    public DeleteStudyPlanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<StudyPlan> lastShownList = model.getFilteredStudyPlanList();

        if (targetIndex.getZeroBased() > StudyPlan.getTotalNumberOfStudyPlans()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
        }

        StudyPlan studyPlanToDelete = null;
        for (StudyPlan studyPlan : lastShownList) {
            if (studyPlan.getIndex() == targetIndex.getZeroBased()) {
                studyPlanToDelete = studyPlan;
            }
        }
        if (studyPlanToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
        }

        model.deleteStudyPlan(studyPlanToDelete);

        // if the deleted study plan is active, the first study plan in the list will be made active automatically
        if (model.getActiveStudyPlan().equals(studyPlanToDelete)) {
            boolean isSuccessful = model.activateFirstStudyPlan();
            if (!isSuccessful) {
                return new CommandResult(MESSAGE_NO_MORE_STUDYPLAN, true, false);
            } else {
                model.addToHistory();
                return new CommandResult(String.format(MESSAGE_DELETE_STUDYPLAN_SUCCESS, studyPlanToDelete),
                        true, false);
            }
        }

        // delete the corresponding study plan commit manager
        model.deleteStudyPlanCommitManagerByIndex(studyPlanToDelete.getIndex());

        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_DELETE_STUDYPLAN_SUCCESS, studyPlanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteStudyPlanCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteStudyPlanCommand) other).targetIndex)); // state check
    }
}
