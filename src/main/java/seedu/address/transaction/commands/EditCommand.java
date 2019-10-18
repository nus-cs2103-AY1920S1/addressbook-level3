package seedu.address.transaction.commands;

import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_TRANSACTION_EDITED;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.commons.util.CollectionUtil;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Edits a transaction in the transaction list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    private static int id;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private int index;
    private EditTransactionDescriptor editTransactionDescriptor;

    /**
     * Creates an EditCommand to edit the specified {@code Transaction}
     */
    public EditCommand(int index, EditTransactionDescriptor editTransactionDescriptor) {
        this.index = index;
        this.id = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException, ParseException {
        Transaction transactionToEdit;
        try {
            transactionToEdit = model.findTransactionInFilteredListByIndex(index);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NO_SUCH_TRANSACTION);
        }
        logger.info("trans to edit: " + transactionToEdit.toString());
        Transaction editedTransaction = createdEditedTransaction(transactionToEdit,
                editTransactionDescriptor, personModel);

        //allow for duplicates
        /*if (transactionToEdit.equals(editedTransaction) && model.hasTransaction(editedTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }*/

        try {
            LocalDate.parse(editedTransaction.getDate(), Transaction.DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new ParseException("Please input the correct date format. DD-MMM-YYYY (eg. 02-Sep-2019).");
        }
        model.setTransaction(transactionToEdit, editedTransaction);
        return new CommandResult(String.format(MESSAGE_TRANSACTION_EDITED, editedTransaction));
    }

    /**
     * Creates the edited transaction according to new inputted attributes.
     * @param transactionToEdit Transaction to be edited.
     * @param editTransactionDescriptor New Transaction descripter with edited attributes.
     * @param personModel model of address book.
     * @return Edited transaction.
     */
    private static Transaction createdEditedTransaction(Transaction transactionToEdit,
                                                        EditTransactionDescriptor editTransactionDescriptor,
                                                        seedu.address.person.model.Model personModel)
            throws NoSuchPersonException {

        String updatedDate = editTransactionDescriptor.getDate().orElse(transactionToEdit.getDate());
        String updatedDescription =
                editTransactionDescriptor.getDescription().orElse(transactionToEdit.getDescription());
        String updatedCategory = editTransactionDescriptor.getCategory().orElse(transactionToEdit.getCategory());
        double updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Person updatedPerson = personModel
                .getPersonByName(editTransactionDescriptor.getName().orElse(transactionToEdit.getName()));;
        /*try {
            updatedPerson = personModel
                    .getPersonByName(editTransactionDescriptor.getName().orElse(transactionToEdit.getName()));
        } catch (PersonNotFoundException e) {
            throw new NoSuchPersonException(MESSAGE_NO_SUCH_PERSON);
        }*/
        boolean updatedIsReimbursed =
                editTransactionDescriptor.getIsReimbursed().orElse(transactionToEdit.getIsReimbursed());
        return new Transaction(updatedDate, updatedDescription, updatedCategory, updatedAmount,
                updatedPerson, id, updatedIsReimbursed);
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private String date;
        private String description;
        private String category;
        private Double amount;
        private String name;
        private boolean isReimbursed;


        public EditTransactionDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setDate(toCopy.date);
            setDescription(toCopy.description);
            setCategory(toCopy.category);
            setAmount(toCopy.amount);
            setName(toCopy.name);
            setIsReimbursed(toCopy.isReimbursed);
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

        public void setAmount(Double amount) {
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

        public void setIsReimbursed(boolean isReimbursed) {
            this.isReimbursed = isReimbursed;
        }

        public Optional<Boolean> getIsReimbursed() {
            return Optional.ofNullable(isReimbursed);
        }

        public EditTransactionDescriptor build() {
            return this;
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
                    && getName().equals(e.getName())
                    && getIsReimbursed().equals(e.getIsReimbursed());
        }

    }
}
