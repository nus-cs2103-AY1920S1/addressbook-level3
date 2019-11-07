package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BORROWER;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;
import seedu.address.model.loan.LoanList;

/**
 * Edits the details of an existing Borrower in the borrower record.
 */
public class EditBorrowerCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the serving borrower.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Invalid parameters will be ignored.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_BORROWER_SUCCESS = "Edited Borrower: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided."
            + "\n"
            + MESSAGE_USAGE;

    private final EditBorrowerDescriptor editBorrowerDescriptor;

    /**
     * @param editBorrowerDescriptor details to edit the borrower with
     */
    public EditBorrowerCommand(EditBorrowerDescriptor editBorrowerDescriptor) throws CommandException {
        requireNonNull(editBorrowerDescriptor);

        this.editBorrowerDescriptor = new EditBorrowerDescriptor(editBorrowerDescriptor);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isServeMode()) {
            throw new CommandException(MESSAGE_NOT_IN_SERVE_MODE);
        }

        Borrower borrowerToEdit = model.getServingBorrower();
        Borrower editedBorrower = createEditedBorrower(borrowerToEdit, editBorrowerDescriptor);

        if (model.hasDuplicatedBorrower(editedBorrower)) {
            throw new CommandException(MESSAGE_DUPLICATE_BORROWER);
        }

        model.setBorrower(borrowerToEdit, editedBorrower);
        model.setServingBorrower(editedBorrower);

        undoCommand = new EditBorrowerCommand(getBorrowerDescriptor(borrowerToEdit));
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_EDIT_BORROWER_SUCCESS, editedBorrower));

        return commandResult;
    }

    /**
     * Returns a {@code EditBorrowerDescriptor} from {@code Borrower}.
     *
     */
    private EditBorrowerDescriptor getBorrowerDescriptor(Borrower borrower) {
        EditBorrowerDescriptor borrowerDescriptor = new EditBorrowerDescriptor();
        borrowerDescriptor.setId(borrower.getBorrowerId());
        borrowerDescriptor.setName(borrower.getName());
        borrowerDescriptor.setEmail(borrower.getEmail());
        borrowerDescriptor.setPhone(borrower.getPhone());

        return borrowerDescriptor;
    }

    /**
     * Creates and returns a {@code Borrower} with the details of {@code borrowerToEdit}
     * edited with {@code editBorrowerDescriptor}.
     */
    private static Borrower createEditedBorrower(Borrower borrowerToEdit,
                                                 EditBorrowerDescriptor editBorrowerDescriptor) {
        requireNonNull(borrowerToEdit);

        Name updatedName = editBorrowerDescriptor.getName().orElse(borrowerToEdit.getName());
        Phone updatedPhone = editBorrowerDescriptor.getPhone().orElse(borrowerToEdit.getPhone());
        Email updatedEmail = editBorrowerDescriptor.getEmail().orElse(borrowerToEdit.getEmail());
        BorrowerId borrowerId = borrowerToEdit.getBorrowerId();
        LoanList currentLoanList = borrowerToEdit.getCurrentLoanList();
        LoanList returnedLoanList = borrowerToEdit.getReturnedLoanList();

        return new Borrower(updatedName, updatedPhone, updatedEmail, borrowerId, currentLoanList, returnedLoanList);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBorrowerCommand)) {
            return false;
        }

        // state check
        EditBorrowerCommand e = (EditBorrowerCommand) other;
        return editBorrowerDescriptor.equals(e.editBorrowerDescriptor);
    }

    /**
     * Stores the details to edit the borrower with. Each non-empty field value will replace the
     * corresponding field value of the borrower.
     */
    public static class EditBorrowerDescriptor {
        public static final String COMMAND_WORD = "edit";
        private Name name;
        private Phone phone;
        private Email email;
        private BorrowerId id;

        public EditBorrowerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBorrowerDescriptor(EditBorrowerDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setId(toCopy.id);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setId(BorrowerId id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBorrowerDescriptor)) {
                return false;
            }

            // state check
            EditBorrowerDescriptor e = (EditBorrowerDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail());
        }
    }
}
