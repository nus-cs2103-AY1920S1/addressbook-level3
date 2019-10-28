package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.UiManager;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS_CONTACTS = "Contacts List Sorted in Lexicographical Order";

    public static final String MESSAGE_SUCCESS_CLAIMS = "Claims List Sorted in Lexicographical Order";

    public static final String MESSAGE_SUCCESS_INCOMES = "Incomes List Sorted in Lexicographical Order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "sorts the current list in lexicographical";

    public static final String MESSAGE_FAILURE = "Sort doesn't work here";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (UiManager.getState().equals("contacts")) {
            model.sortFilteredContactList();
            return new CommandResult(MESSAGE_SUCCESS_CONTACTS, false, false, false, false, false);
        } else if (UiManager.getState().equals("claims")) {
            model.sortFilteredClaimList();
            return new CommandResult(MESSAGE_SUCCESS_CLAIMS, false, false, false, false, false);
        } else if (UiManager.getState().equals("incomes")) {
            model.sortFilteredIncomeList();
            return new CommandResult(MESSAGE_SUCCESS_INCOMES, false, false, false, false, false);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
