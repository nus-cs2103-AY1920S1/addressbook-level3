package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.SortCommand.MESSAGE_FAILURE_FILTER_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.SortFilter;
import seedu.address.ui.UiManager;

/**
 * Sorts the current list in reverse order
 */
//@@author {lawncegoh}
public class SortReverseCommand extends Command {

    public static final String COMMAND_WORD = "reverse";

    public static final String MESSAGE_SUCCESS_CONTACTS = "Contacts List Sorted in Reverse Order";

    public static final String MESSAGE_SUCCESS_CLAIMS = "Claims List Sorted in Reverse Order";

    public static final String MESSAGE_SUCCESS_INCOMES = "Incomes List Sorted in Reverse Order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " sorts the current list in reverse order";

    public static final String MESSAGE_FAILURE = "Sort doesn't work here";

    public static final String MESSAGE_FAILURE_FILTER = "Filter does not exist";

    public static final String MESSAGE_FAILURE_FILTER_DATE = "Date filter is not valid for contacts";

    private static final String MESSAGE_SUCCESS_CLAIMS_DATE =
            "Claims list sorted from Newest Entry to Oldest in Reverse Order";

    private static final String MESSAGE_SUCCESS_CLAIMS_STATUS =
            "Claims list sorted from Rejected to Approved to Pending";

    private static final String MESSAGE_SUCCESS_INCOMES_DATE =
            "Incomes list sorted from Newest Entry to Oldest in Reverse Order";

    private SortFilter gotoFilter;

    /**
     * Constructs a reverse sort command
     * @param sortFilter 2 different types of filters
     * @return
     * @throws CommandException
     */
    public SortReverseCommand(SortFilter sortFilter) {
        requireNonNull(sortFilter);
        try {
            if (sortFilter == null) {
                throw new ParseException("error");
            }
            gotoFilter = sortFilter;
        } catch (ParseException e) {
            e.getMessage();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch(gotoFilter.getIndex()) {

        case 1 : //case of sorting by name

            if (UiManager.getState().equals("contacts")) {
                model.sortReverseFilteredContactListByName();
                return new CommandResult(MESSAGE_SUCCESS_CONTACTS, false,
                        false, false, false, false);
            } else if (UiManager.getState().equals("claims")) {
                model.sortReverseFilteredClaimListByName();
                return new CommandResult(MESSAGE_SUCCESS_CLAIMS, false,
                        false, false, false, false);
            } else if (UiManager.getState().equals("incomes")) {
                model.sortReverseFilteredIncomeListByName();
                return new CommandResult(MESSAGE_SUCCESS_INCOMES, false,
                        false, false, false, false);
            } else {
                throw new CommandException(MESSAGE_FAILURE);
            }

        case 2 : //case of sorting by date

            if (UiManager.getState().equals("claims")) {
                model.sortReverseFilteredClaimListByDate();
                return new CommandResult(MESSAGE_SUCCESS_CLAIMS_DATE, false,
                        false, false, false, false);
            } else if (UiManager.getState().equals(("incomes"))) {
                model.sortReverseFilteredIncomeListByDate();
                return new CommandResult(MESSAGE_SUCCESS_INCOMES_DATE, false,
                        false, false, false, false);
            } else {
                throw new CommandException(MESSAGE_FAILURE_FILTER_DATE);
            }

        case 3: //case of sorting claims by status

            if (UiManager.getState().equals("claims")) {
                model.sortReverseFilteredClaimListByStatus();
                return new CommandResult(MESSAGE_SUCCESS_CLAIMS_STATUS, false, false,
                        false, false, false);
            } else {
                throw new CommandException(MESSAGE_FAILURE_FILTER_STATUS);
            }

        default :
            throw new CommandException(MESSAGE_FAILURE_FILTER);
        }
    }

    /**
     * Checks if 2 sortreversecommands are equal
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortReverseCommand // instanceof handles nulls
                && gotoFilter.equals(((SortReverseCommand) other).gotoFilter)); // state check
    }
}
