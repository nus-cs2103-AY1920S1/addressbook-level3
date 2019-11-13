package budgetbuddy.testutil.loanutil;

import java.time.LocalDate;

import budgetbuddy.logic.commands.loancommands.LoanEditCommand.LoanEditDescriptor;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;

/**
 * A utility class to help with building LoanEditDescriptor objects.
 */
public class LoanEditDescriptorBuilder {

    private LoanEditDescriptor loanEditDescriptor;

    public LoanEditDescriptorBuilder() {
        loanEditDescriptor = new LoanEditDescriptor();
    }

    public LoanEditDescriptorBuilder(LoanEditDescriptor loanEditDescriptor) {
        this.loanEditDescriptor = new LoanEditDescriptor(loanEditDescriptor);
    }

    /**
     * Returns a {@code LoanEditDescriptor} with fields containing {@code loan}'s details.
     */
    public LoanEditDescriptorBuilder(Loan loan) {
        loanEditDescriptor = new LoanEditDescriptor();
        loanEditDescriptor.setPerson(loan.getPerson());
        loanEditDescriptor.setDirection(loan.getDirection());
        loanEditDescriptor.setAmount(loan.getAmount());
        loanEditDescriptor.setDate(loan.getDate());
        loanEditDescriptor.setDescription(loan.getDescription());
        loanEditDescriptor.setStatus(loan.getStatus());
    }

    /**
     * Sets the {@code Person} of the {@code LoanEditDescriptor} that we are building.
     */
    public LoanEditDescriptorBuilder withPerson(String person) {
        loanEditDescriptor.setPerson(new Person(new Name(person)));
        return this;
    }

    /**
     * Sets the {@code Direction} of the {@code LoanEditDescriptor} that we are building.
     */
    public LoanEditDescriptorBuilder withDirection(String direction) {
        loanEditDescriptor.setDirection(Direction.valueOf(direction));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code LoanEditDescriptor} that we are building.
     */
    public LoanEditDescriptorBuilder withAmount(Long amount) {
        loanEditDescriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code LoanEditDescriptor} that we are building.
     */
    public LoanEditDescriptorBuilder withDescription(String description) {
        loanEditDescriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code LoanEditDescriptor} that we are building.
     */
    public LoanEditDescriptorBuilder withDate(LocalDate date) {
        loanEditDescriptor.setDate(date);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code LoanEditDescriptor} that we are building.
     */
    public LoanEditDescriptorBuilder withStatus(String status) {
        loanEditDescriptor.setStatus(Status.valueOf(status));
        return this;
    }

    public LoanEditDescriptor build() {
        return loanEditDescriptor;
    }
}
