package seedu.planner.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.Model;
import seedu.planner.model.accommodation.Accommodation;

/**
 * Lists all accommodations in the planner.
 * @author 1nefootstep
 */
public class ListAccommodationCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final String MESSAGE_SUCCESS = "Listed all accommodations";

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD + " "
            + SECOND_COMMAND_WORD);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Accommodation> lastShownList = model.getFilteredAccommodationList();
        int accommodationListSize = lastShownList.size();
        ResultInformation[] resultInformation = new ResultInformation[accommodationListSize];
        for (int i = 0; i < accommodationListSize; i++) {
            resultInformation[i] = new ResultInformation(lastShownList.get(i), Index.fromZeroBased(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, resultInformation,
                new UiFocus[]{UiFocus.ACCOMMODATION, UiFocus.INFO});
    }
}
