package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.model.Model;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Removes an activity from the activity list.
 */
public class DeleteActivityCommand extends DeleteCommand {
    public static final String SECOND_COMMAND_WORD = "activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activity identified by the index number used in the displayed activity list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    private final Index targetIndex;
    private final Activity toDelete;

    public DeleteActivityCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        toDelete = null;
    }

    public DeleteActivityCommand(Activity activity) {
        toDelete = activity;
        targetIndex = null;
    }

    public Index getTargetIndex() {
        return targetIndex;
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
        model.deleteActivity(activityToDelete);
        return new CommandResult(
                String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete),
                new ResultInformation[] {
                        new ResultInformation(
                                activityToDelete,
                                indexOfActivity,
                                String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, "")
                        )
                },
                new UiFocus[] { UiFocus.ACTIVITY, UiFocus.INFO }
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
