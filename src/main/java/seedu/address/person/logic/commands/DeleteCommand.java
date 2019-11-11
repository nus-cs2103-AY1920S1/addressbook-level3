package seedu.address.person.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.person.commons.core.Messages;
import seedu.address.person.commons.core.index.Index;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.CommandResult;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.transaction.logic.Logic transactionLogic,
                                 seedu.address.reimbursement.logic.Logic reimbursementLogic) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        checkIfPersonHasTransactionRecords(transactionLogic, personToDelete);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
    }

    /**
     * Checks if the person to be deleted has a transaction record in the transaction list.
     * Does not allow for deletion if the person still has a record.
     * @param transactionLogic Logic of transaction tab
     * @param personToDelete Person to be deleted
     * @throws CommandException If the person has a transaction record associated to it.
     */
    private static void checkIfPersonHasTransactionRecords(seedu.address.transaction.logic.Logic transactionLogic,
                                                           Person personToDelete)
                                                            throws CommandException {
        for (int i = 0; i < transactionLogic.getTransactionList().size(); i++) {
            if (transactionLogic.getTransactionList().get(i).getPerson().equals(personToDelete)) {
                throw new CommandException(TransactionMessages.MESSAGE_PERSON_CANNOT_BE_DELETED);
            }
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
