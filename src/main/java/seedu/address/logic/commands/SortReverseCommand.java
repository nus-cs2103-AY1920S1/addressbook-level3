package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.UiManager;

public class SortReverseCommand extends Command {

    public static final String COMMAND_WORD = "reverse";

    public static final String MESSAGE_SUCCESS_CONTACTS = "Contacts List Sorted in Reverse Order";

    public static final String MESSAGE_SUCCESS_CLAIMS = "Claims List Sorted in Reverse Order";

    public static final String MESSAGE_SUCCESS_INCOMES = "Incomes List Sorted in Reverse Order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "sorts the current list in reverse order";

    public static final String MESSAGE_FAILURE = "Sort doesn't work here";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (UiManager.getState().equals("contacts")) {
            model.sortReverseFilteredContactList();
            return new CommandResult(MESSAGE_SUCCESS_CONTACTS, false, false, false, false, false);
        } else if (UiManager.getState().equals("claims")) {
            model.sortReverseFilteredClaimList();
            return new CommandResult(MESSAGE_SUCCESS_CLAIMS, false, false, false, false, false);
        } else if (UiManager.getState().equals("incomes")) {
            model.sortReverseFilteredIncomeList();
            return new CommandResult(MESSAGE_SUCCESS_INCOMES, false, false, false, false, false);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}