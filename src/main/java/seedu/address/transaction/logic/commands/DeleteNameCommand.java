package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_BY_PERSON;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION_OF_PERSON;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.util.CommandResult;

/**
 * Deletes a transaction to the transaction list according to the person.
 */
public class DeleteNameCommand extends DeleteCommand {
    private final Person person;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Transaction} according to person.
     */
    public DeleteNameCommand(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    @Override
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel)
            throws NoSuchPersonException {
        requireNonNull(model);
        requireNonNull(personModel);
        if (!model.hasTransactionWithName(person.getName().toString())) {
            throw new NoSuchPersonException(String.format(MESSAGE_NO_SUCH_TRANSACTION_OF_PERSON,
                    person.getName().toString()));
        }
        model.deleteAllTransactionOfPerson(person);
        return new CommandResult(String.format(MESSAGE_DELETE_BY_PERSON, person.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNameCommand // instanceof handles nulls
                && person.equals(((DeleteNameCommand) other).person));
    }
}
