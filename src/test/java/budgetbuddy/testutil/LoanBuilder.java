package budgetbuddy.testutil;

import java.util.Date;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * A utility class to help with building Loan objects.
 */
public class LoanBuilder {

    public static final Person DEFAULT_PERSON = TypicalPersons.ALICE;
    public static final Direction DEFAULT_DIRECTION = Direction.OUT;
    public static final Amount DEFAULT_AMOUNT = new Amount(100L);
    public static final Description DEFAULT_DESCRIPTION = new Description("The description.");
    public static final Date DEFAULT_DATE = new Date();
    public static final Status DEFAULT_STATUS = Status.UNPAID;

    private Person person;
    private Direction direction;
    private Amount amount;
    private Description description;
    private Date date;
    private Status status;

    public LoanBuilder() {
        this.person = DEFAULT_PERSON;
        this.direction = DEFAULT_DIRECTION;
        this.amount = DEFAULT_AMOUNT;
        this.description = DEFAULT_DESCRIPTION;
        this.date = DEFAULT_DATE;
        this.status = DEFAULT_STATUS;
    }

    public LoanBuilder(Loan toCopy) {
        this.person = toCopy.getPerson();
        this.direction = toCopy.getDirection();
        this.amount = toCopy.getAmount();
        this.description = toCopy.getDescription();
        this.date = toCopy.getDate();
        this.status = toCopy.getStatus();
    }

    /**
     * Sets the {@code Person} of the {@code Loan} that we are building.
     */
    public LoanBuilder withPerson(String person) {
        this.person = new Person(new Name(person));
        return this;
    }

    /**
     * Sets the {@code Direction} of the {@code Loan} that we are building.
     */
    public LoanBuilder withDirection(String direction) {
        this.direction = Direction.valueOf(direction);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Loan} that we are building.
     */
    public LoanBuilder withAmount(Long amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Loan} that we are building.
     */
    public LoanBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Loan} that we are building.
     */
    public LoanBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Loan} that we are building.
     */
    public LoanBuilder withStatus(String status) {
        this.status = Status.valueOf(status);
        return this;
    }

    public Loan build() {
        return new Loan(person, direction, amount, date, description, status);
    }

}
