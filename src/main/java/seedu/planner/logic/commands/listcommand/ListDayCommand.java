package seedu.planner.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.day.ActivityWithTime;

/**
 * Lists all activities in the planner.
 * @author 1nefootstep
 */
public class ListDayCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "day";

    public static final String MESSAGE_SUCCESS = "Listed all activities in day";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Lists the activities within day DAY_INDEX.",
            COMMAND_WORD + " ("
                    + SECOND_COMMAND_WORD
                    + " <DAY_INDEX>",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD + " "
            + SECOND_COMMAND_WORD,
            "<INDEX>"
    );

    private final Index dayIndex;

    public ListDayCommand(Index dayIndex) {
        this.dayIndex = dayIndex;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<ActivityWithTime> activityWithTimeList = model.getFilteredItinerary().get(dayIndex.getZeroBased())
                .getListOfActivityWithTime();
        int listSize = activityWithTimeList.size();
        ResultInformation[] resultInformation = new ResultInformation[listSize];
        for (int i = 0; i < listSize; i++) {
            resultInformation[i] = new ResultInformation(activityWithTimeList.get(i), Index.fromZeroBased(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, resultInformation, new UiFocus[]{UiFocus.ACTIVITY, UiFocus.INFO});
    }
}
