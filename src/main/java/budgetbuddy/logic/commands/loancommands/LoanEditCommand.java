package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;

import java.time.LocalDate;
import java.util.Optional;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.CollectionUtil;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.exceptions.DuplicateLoanException;
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;
import budgetbuddy.model.person.Person;

/**
 * Edits a loan.
 */
public class LoanEditCommand extends Command {

    public static final String COMMAND_WORD = "loan edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a loan.\n"
            + "Parameters: "
            + "<loan number> "
            + String.format("[%sPERSON] ", PREFIX_PERSON)
            + String.format("[%sAMOUNT] ", PREFIX_AMOUNT)
            + String.format("[%sDESCRIPTION] ", PREFIX_DESCRIPTION)
            + String.format("[%sDATE]\n", PREFIX_DATE)
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "4.30";

    public static final String MESSAGE_SUCCESS = "Loan %1$d edited.";
    public static final String MESSAGE_UNEDITED = "At least one field must be provided for editing.";
    public static final String MESSAGE_LOAN_NOT_FOUND = "The loan targeted for editing could not be found.";
    public static final String MESSAGE_DUPLICATE_LOAN = "The edited loan already exists in the list.";

    private final Index targetLoanIndex;
    private final LoanEditDescriptor loanEditDescriptor;

    public LoanEditCommand(Index targetLoanIndex, LoanEditDescriptor loanEditDescriptor) {
        requireAllNonNull(targetLoanIndex, loanEditDescriptor);
        this.targetLoanIndex = targetLoanIndex;
        this.loanEditDescriptor = new LoanEditDescriptor(loanEditDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        if (!loanEditDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_UNEDITED);
        }

        LoansManager loansManager = model.getLoansManager();

        Loan editedLoan;
        try {
            Loan targetLoan = loansManager.getLoan(targetLoanIndex);
            editedLoan = createEditedLoan(targetLoan, loanEditDescriptor);
            loansManager.editLoan(targetLoanIndex, editedLoan);
        } catch (LoanNotFoundException e) {
            throw new CommandException(MESSAGE_LOAN_NOT_FOUND);
        } catch (DuplicateLoanException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, targetLoanIndex.getOneBased()), CommandCategory.LOAN);
    }

    /**
     * Creates and returns a {@code Loan} with the details of {@code loanToEdit},
     * edited with {@code loanEditDescriptor}.
     */
    private static Loan createEditedLoan(Loan loanToEdit, LoanEditDescriptor loanEditDescriptor) {
        assert loanToEdit != null;

        Person updatedPerson = loanEditDescriptor.getPerson().orElse(loanToEdit.getPerson());
        Direction updatedDirection = loanEditDescriptor.getDirection().orElse(loanToEdit.getDirection());
        Amount updatedAmount = loanEditDescriptor.getAmount().orElse(loanToEdit.getAmount());
        LocalDate updatedDate = loanEditDescriptor.getDate().orElse(loanToEdit.getDate());
        Description updatedDescription = loanEditDescriptor.getDescription().orElse(loanToEdit.getDescription());
        Status updatedStatus = loanEditDescriptor.getStatus().orElse(loanToEdit.getStatus());

        return new Loan(updatedPerson, updatedDirection, updatedAmount,
                updatedDate, updatedDescription, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanEditCommand)) {
            return false;
        }

        LoanEditCommand otherCommand = (LoanEditCommand) other;
        return targetLoanIndex.equals(otherCommand.targetLoanIndex)
                && loanEditDescriptor.equals(otherCommand.loanEditDescriptor);
    }

    /**
     * Stores the details to edit the loan with. Each non-empty field value will replace the
     * corresponding field value of the loan.
     */
    public static class LoanEditDescriptor {
        private Person person;
        private Direction direction;
        private Amount amount;
        private LocalDate date;
        private Description description;
        private Status status;

        public LoanEditDescriptor() {}

        public LoanEditDescriptor(LoanEditDescriptor toCopy) {
            setPerson(toCopy.person);
            setDirection(toCopy.direction);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setDescription(toCopy.description);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if any field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(person, direction, amount, date, description, status);
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Optional<Person> getPerson() {
            return Optional.ofNullable(person);
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public Optional<Direction> getDirection() {
            return Optional.ofNullable(direction);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof LoanEditDescriptor)) {
                return false;
            }

            LoanEditDescriptor e = (LoanEditDescriptor) other;
            return getPerson().equals(e.getPerson())
                    && getDirection().equals(e.getDirection())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getDescription().equals(e.getDescription())
                    && getStatus().equals(e.getStatus());
        }
    }
}
