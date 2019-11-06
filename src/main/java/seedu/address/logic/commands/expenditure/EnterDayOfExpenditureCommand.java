package seedu.address.logic.commands.expenditure;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.PlannedExpenditure;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;
import seedu.address.model.itinerary.day.Day;

/**
 * Placeholder.
 */
public class EnterDayOfExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the overview of a day.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_DAY_SUCCESS = "Here is your day!: %1$s!";
    public static final String MESSAGE_MISC_EXPENDITURE = "The expenditure is not associated with an event";

    private final Index indexToEnter;

    public EnterDayOfExpenditureCommand(Index indexToEnter) {
        this.indexToEnter = indexToEnter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Expenditure> lastShownExpenseList = model.getPageStatus().getTrip().getExpenditureList().internalList;
            List<Day> lastShownDayList = model.getPageStatus().getTrip().getDayList().internalList;

            if (indexToEnter.getZeroBased() >= lastShownExpenseList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
            }

            // References preserved by PageStatus
            Expenditure expenditureToShow = lastShownExpenseList.get(indexToEnter.getZeroBased());
            if (expenditureToShow instanceof PlannedExpenditure) {
                assert expenditureToShow.getDayNumber().isPresent();
                Day dayToEnter = lastShownDayList.get(expenditureToShow.getDayNumber().get().getValue() - 1);
                model.setPageStatus(model.getPageStatus()
                        .withNewPageType(PageType.EVENT_PAGE)
                        .withNewDay(dayToEnter));
                return new CommandResult(String.format(MESSAGE_ENTER_DAY_SUCCESS, dayToEnter), true);
            } else {
                throw new ExpenditureNotFoundException();
            }



        } catch (ExpenditureNotFoundException ex) {
            throw new CommandException(MESSAGE_MISC_EXPENDITURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterDayOfExpenditureCommand;
    }

}
