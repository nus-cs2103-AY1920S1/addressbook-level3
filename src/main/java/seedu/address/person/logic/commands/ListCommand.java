package seedu.address.person.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.person.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.person.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model, seedu.address.transaction.logic.Logic transactionLogic,
                                 seedu.address.reimbursement.logic.Logic reimbursementLogic,
                                 seedu.address.cashier.logic.Logic cashierLogic) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
