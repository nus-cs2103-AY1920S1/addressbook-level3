package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

/**
 * Lists all activities in the planner.
 */
public class ListActivityCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final String MESSAGE_SUCCESS = "Listed all activities";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();
        int activityListSize = lastShownList.size();
        ResultInformation[] resultInformation = new ResultInformation[activityListSize];
        for (int i = 0; i < activityListSize; i++) {
            resultInformation[i] = new ResultInformation(lastShownList.get(i), Index.fromZeroBased(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, resultInformation, new UiFocus[]{UiFocus.ACTIVITY, UiFocus.INFO});
    }
}
