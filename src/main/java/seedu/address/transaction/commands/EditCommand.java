package seedu.address.transaction.commands;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import seedu.address.person.commons.util.CollectionUtil;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Edits a transaction to the transaction list.
 */
public class EditCommand extends Command {
    private static int id;
    private int index;
    private EditTransactionDescriptor editTransactionDescriptor;
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This transaction is already recorded.";

    /**
     * Creates an EditCommand to add the specified {@code Transaction}
     */
    public EditCommand(int index, EditTransactionDescriptor editTransactionDescriptor) {
        this.index = index;
        this.id = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException {
        TransactionMessages transactionMessages = new TransactionMessages();
        Transaction transactionToEdit = model.findTransactionByIndex(index);

        Transaction editedTransaction = createdEditedTransaction(transactionToEdit, editTransactionDescriptor, personModel);

        if (!transactionToEdit.equals(editedTransaction) && model.hasTransaction(editedTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }
        if (!personModel.hasPerson(editedTransaction.getPerson())) {
            //personModel.addPerson(editedTransaction.getPerson());
            throw new NoSuchPersonException(TransactionMessages.NO_SUCH_PERSON);
        }
        model.setTransaction(transactionToEdit, editedTransaction);
        return new CommandResult(TransactionMessages.editedTransaction(editedTransaction));
    }

    private static Transaction createdEditedTransaction(Transaction transactionToEdit,
                                                        EditTransactionDescriptor editTransactionDescriptor,
                                                        seedu.address.person.model.Model personModel) {

        String updatedDate = editTransactionDescriptor.getDate().orElse(transactionToEdit.getDate());
        String updatedDescription = editTransactionDescriptor.getDescription().orElse(transactionToEdit.getDescription());
        String updatedCategory = editTransactionDescriptor.getCategory().orElse(transactionToEdit.getCategory());
        double updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Person updatedPerson =
                personModel.getPersonByName(editTransactionDescriptor.getName().orElse(transactionToEdit.getName()));
        return new Transaction(updatedDate, updatedDescription, updatedCategory, updatedAmount, updatedPerson, id);
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private String date;
        private String description;
        private String category;
        private double amount;
        private String name;
        private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy",
                Locale.ENGLISH);


        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setDate(toCopy.date);
            setDescription(toCopy.description);
            setCategory(toCopy.category);
            setAmount(toCopy.amount);
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, description, category, amount, name);
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Optional<String> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Optional<String> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public Optional<Double> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            // state check
            EditTransactionDescriptor e = (EditTransactionDescriptor) other;

            return getDate().equals(e.getDate())
                    && getDescription().equals(e.getDescription())
                    && getCategory().equals(e.getCategory())
                    && getAmount().equals(e.getAmount())
                    && getName().equals(e.getName());
        }
    }

}
