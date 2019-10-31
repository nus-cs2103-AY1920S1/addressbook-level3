package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.model.Model;
import seedu.address.model.accommodation.Accommodation;

/**
 * Lists all accommodations in the planner.
 */
public class ListAccommodationCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final String MESSAGE_SUCCESS = "Listed all accommodations";

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
