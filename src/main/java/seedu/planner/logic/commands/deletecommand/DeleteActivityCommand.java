package seedu.planner.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;

//@@author KxxMxxx
/**
 * Removes an activity from the activity list.
 */
public class DeleteActivityCommand extends DeleteCommand {
    public static final String SECOND_COMMAND_WORD = "activity";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Deletes the activity identified by the index "
                    + "number used in the displayed activity list.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " INDEX(must be a positive integer)",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 3"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<INDEX>"
    );

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    private final Index targetIndex;
    private final Activity toDelete;
    private final boolean isUndoRedo;

    public DeleteActivityCommand(Index targetIndex, boolean isUndoRedo) {
        this.targetIndex = targetIndex;
        toDelete = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo AddActivityEvent and create DeleteActivityEvent
    public DeleteActivityCommand(Index targetIndex, Activity activity) {
        requireNonNull(activity);
        toDelete = activity;
        this.targetIndex = targetIndex;
        this.isUndoRedo = true;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public Activity getToDelete() {
        return toDelete;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();
        Activity activityToDelete;

        if (toDelete != null) {
            activityToDelete = toDelete;
        } else if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        } else {
            activityToDelete = lastShownList.get(targetIndex.getZeroBased());
        }
        Index indexOfActivity = findIndexOfActivity(model, activityToDelete);

        if (toDelete == null && !isUndoRedo) {
            //Not due to undo method of AddActivityEvent or redo method of DeleteActivityEvent
            DeleteActivityCommand newCommand = new DeleteActivityCommand(indexOfActivity,
                    activityToDelete);
            updateEventStack(newCommand, model);
        }
        model.deleteActivity(activityToDelete);

        return new CommandResult(
                String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete),
                new ResultInformation[]{
                    new ResultInformation(
                            activityToDelete,
                                indexOfActivity,
                                String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, "")
                        )
                },
                new UiFocus[]{UiFocus.ACTIVITY, UiFocus.INFO}
        );
    }

    /**
     * Returns the index of activity in the model.
     * Precondition: the {@code activity} must have not been deleted before this.
     */
    private Index findIndexOfActivity(Model model, Activity activity) {
        Optional<Index> indexOfActivity = model.getActivityIndex(activity);
        if (indexOfActivity.isEmpty()) {
            throw new AssertionError("Activity should not have been deleted before this.");
        }
        return indexOfActivity.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteActivityCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteActivityCommand) other).targetIndex)); // state check
    }
}
