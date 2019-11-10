package seedu.ichifund.logic.commands.loan;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.isAnyNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.model.Model.PREDICATE_SHOW_ALL_LOANS;

import java.util.List;
import java.util.Optional;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.loan.Name;

/**
 * Edits a loan in IchiFund.
 */
public class EditLoanCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a loan in IchiFund.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_DAY + "START_DAY "
            + PREFIX_START_MONTH + "START_MONTH "
            + PREFIX_START_YEAR + "START_YEAR "
            + PREFIX_END_DAY + "END_DAY "
            + PREFIX_END_MONTH + "END_MONTH "
            + PREFIX_END_YEAR + "END_YEAR "

            + "\n\nConstraints:\n"
            + "- Loan end must not occur before loan start.\n"
            + "- Loan start and deadline can span any amount.\n\n"

            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Current Student Loan "
            + PREFIX_AMOUNT + "99999.99 "
            + PREFIX_NAME + "DBS SG Bank "
            + PREFIX_START_DAY + "1 "
            + PREFIX_START_MONTH + "1 "
            + PREFIX_START_YEAR + "2020 "
            + PREFIX_END_DAY + "30 "
            + PREFIX_END_MONTH + "12 "
            + PREFIX_END_YEAR + "2021";

    public static final String MESSAGE_EDIT_LOAN_SUCCESS = "Edited loan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be modified, no Edits Occured.";
    public static final String MESSAGE_DUPLICATE_LOAN = "This loan already exists in IchiFund";

    private final Index index;
    private final EditLoanDescriptor editLoanDescriptor;

    /**
     * Creates an EditLoanCommand to edit the specified {@code Loan}
     */
    public EditLoanCommand(Index index, EditLoanDescriptor editLoanDescriptor) {
        requireNonNull(index);
        requireNonNull(editLoanDescriptor);

        this.index = index;
        this.editLoanDescriptor = editLoanDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Loan> lastShownList = model.getFilteredLoanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_INDEX);
        }

        Loan loanToEdit = lastShownList.get(index.getZeroBased());

        LoanId updatedLoanId = loanToEdit.getLoanId();

        Description updatedDescription = editLoanDescriptor.getDescription()
                .orElse(loanToEdit.getDescription());

        Amount updatedAmount = editLoanDescriptor.getAmount()
                .orElse(loanToEdit.getAmount());

        Name updatedName = editLoanDescriptor.getName()
                .orElse(loanToEdit.getName());

        Date updatedStartDate = editLoanDescriptor.getStartDate()
                .orElse(loanToEdit.getStartDate());

        Date updatedEndDate = editLoanDescriptor.getEndDate()
                .orElse(loanToEdit.getEndDate());


        Loan editedLoan = new Loan(updatedLoanId, updatedAmount, updatedName,
                updatedStartDate, updatedEndDate, updatedDescription);

        if (editedLoan.equals(loanToEdit)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        if (model.hasLoan(editedLoan)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }

        model.setLoan(loanToEdit, editedLoan);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);

        return new CommandResult(String.format(MESSAGE_EDIT_LOAN_SUCCESS, editedLoan));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditLoanCommand // instanceof handles nulls
                && editLoanDescriptor.equals(((EditLoanCommand) other).editLoanDescriptor));
    }

    /**
     * Stores the details to edit the loan with. Each non-empty field value will replace the
     * corresponding field value of the loan.
     */
    public static class EditLoanDescriptor {
        private Description description;
        private Amount amount;
        private Name name;
        private Date startDate;
        private Date endDate;

        public EditLoanDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditLoanDescriptor(EditLoanDescriptor toCopy) {
            setDescription(toCopy.description);
            setAmount(toCopy.amount);
            setName(toCopy.name);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Date> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Optional<Date> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return isAnyNonNull(description, amount, name, startDate, endDate);
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof EditLoanDescriptor)) {
                return false;
            }
            // state check
            EditLoanDescriptor e = (EditLoanDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getAmount().equals(e.getAmount())
                    && getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate());
        }
    }
}
