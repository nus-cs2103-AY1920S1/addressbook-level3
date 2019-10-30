package budgetbuddy.model.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import budgetbuddy.model.LoansManager;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static List<Loan> getSampleLoans() {
        return Arrays.asList(
                new Loan(new Person(new Name("Example Person 1")),
                        Direction.OUT, new Amount(420), new Date(),
                        new Description("Paid for their stuff."), Status.UNPAID),
                new Loan(new Person(new Name("Example Person 2")),
                        Direction.IN, new Amount(10000), new Date(),
                        new Description("Owe them for that party."), Status.UNPAID));
    }

    public static LoansManager getSampleLoansManager() {
        return new LoansManager(getSampleLoans());
    }
}
