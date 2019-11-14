package seedu.address.logic.commands.itinerary.days;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.day.Day;

/**
 * Command to enter {@link seedu.address.ui.itinerary.DaysPage}.
 */
public class EnterDayCommand extends Command {
    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the overview of a day.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_DAY_SUCCESS = "Here is your day!: %1$s!";

    private final Index indexToEnter;

    public EnterDayCommand(Index indexToEnter) {
        this.indexToEnter = indexToEnter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Reference to in memory daylist is stored by pageStatus
        List<Day> lastShownList = model.getPageStatus().getTrip().getDayList().internalList;

        // Set when the trip list is first displayed to the user
        SortedList currentSortedDayList = model.getPageStatus().getSortedOccurrencesList();

        int rawZeroBasedIndex = currentSortedDayList.getSourceIndex(indexToEnter.getZeroBased());


        if (rawZeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Day dayToEnter = lastShownList.get(rawZeroBasedIndex);
        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.EVENT_PAGE)
                .withNewDay(dayToEnter));

        return new CommandResult(String.format(MESSAGE_ENTER_DAY_SUCCESS, dayToEnter), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterDayCommand;
    }

}
