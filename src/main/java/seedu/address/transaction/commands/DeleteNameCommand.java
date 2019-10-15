package seedu.address.transaction.commands;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Deletes a transaction to the transaction list according to the person.
 */
public class DeleteNameCommand extends DeleteCommand {
    private Person person;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Transaction} according to person.
     */
    public DeleteNameCommand(Person person) {
        this.person = person;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.deleteAllTransactionOfPerson(person);
        return new CommandResult(TransactionMessages.deletedTransactionsOfPerson(person));
    }
}
