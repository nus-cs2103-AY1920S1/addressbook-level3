package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Optional;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.CollectionUtil;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Direction;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Description;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.stub.Date;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Edits a loan.
 */
public class EditLoanCommand extends Command {

    public static final String COMMAND_WORD = "loan edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a loan.\n"
            + "Parameters: "
            + "<person number>.<loan number> "
            + String.format("[%sAMOUNT]", PREFIX_AMOUNT) + " "
            + String.format("[%sDESCRIPTION]", PREFIX_DESCRIPTION) + " "
            + String.format("[%sDATE]", PREFIX_DATE) + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "1.1 "
            + PREFIX_AMOUNT + "4.30";

    public static final String MESSAGE_SUCCESS = "Loan edited: %1$s";

    private final Index targetPersonIndex;
    private final Index targetLoanIndex;
    private final EditLoanDescriptor editLoanDescriptor;

    public EditLoanCommand(Index targetPersonIndex, Index targetLoanIndex, EditLoanDescriptor editLoanDescriptor) {
        requireAllNonNull(targetPersonIndex, targetLoanIndex, editLoanDescriptor);
        this.targetPersonIndex = targetPersonIndex;
        this.targetLoanIndex = targetLoanIndex;
        this.editLoanDescriptor = new EditLoanDescriptor(editLoanDescriptor);
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());

        LoansManager loansManager = model.getLoansManager();
        Person targetPerson = loansManager.getPersonsList().get(targetPersonIndex.getZeroBased());
        Loan targetLoan = targetPerson.getLoans().get(targetLoanIndex.getZeroBased());
        Loan editedLoan = createEditedLoan(targetLoan, editLoanDescriptor);

        loansManager.editLoan(targetPerson, targetLoan, editedLoan);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedLoan));
    }

    /**
     * Creates and returns a {@code Loan} with the details of {@code loanToEdit},
     * edited with {@code editLoanDescriptor}.
     */
    private static Loan createEditedLoan(Loan loanToEdit, EditLoanDescriptor editLoanDescriptor) {
        assert loanToEdit != null;

        Person updatedPerson = editLoanDescriptor.getPerson().orElse(loanToEdit.getPerson());
        Direction updatedDirection = editLoanDescriptor.getDirection().orElse(loanToEdit.getDirection());
        Amount updatedAmount = editLoanDescriptor.getAmount().orElse(loanToEdit.getAmount());
        Date updatedDate = editLoanDescriptor.getDate().orElse(loanToEdit.getDate());
        Description updatedDescription = editLoanDescriptor.getDescription().orElse(loanToEdit.getDescription());
        Status updatedStatus = editLoanDescriptor.getStatus().orElse(loanToEdit.getStatus());

        return new Loan(updatedPerson, updatedDirection, updatedAmount,
                updatedDate, updatedDescription, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditLoanCommand)) {
            return false;
        }

        EditLoanCommand otherCommand = (EditLoanCommand) other;
        return targetPersonIndex.equals(otherCommand.targetPersonIndex)
                && targetLoanIndex.equals(otherCommand.targetLoanIndex)
                && editLoanDescriptor.equals(otherCommand.editLoanDescriptor);
    }

    /**
     * Stores the details to edit the loan with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditLoanDescriptor {
        private Person person;
        private Direction direction;
        private Amount amount;
        private Date date;
        private Description description;
        private Status status;

        public EditLoanDescriptor() {}

        public EditLoanDescriptor(EditLoanDescriptor toCopy) {
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

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
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

            if (!(other instanceof EditLoanDescriptor)) {
                return false;
            }

            EditLoanDescriptor e = (EditLoanDescriptor) other;
            return getPerson().equals(e.getPerson())
                    && getDirection().equals(e.getDirection())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getDescription().equals(e.getDescription())
                    && getStatus().equals(e.getStatus());
        }
    }
}
