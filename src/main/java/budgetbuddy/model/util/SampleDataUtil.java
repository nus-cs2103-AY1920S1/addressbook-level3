package budgetbuddy.model.util;

import java.time.LocalDate;
import java.util.Arrays;
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
 * Contains utility methods for populating {@code BudgetBuddy} with sample data.
 */
public class SampleDataUtil {

    public static List<Loan> getSampleLoans() {
        return Arrays.asList(
                new Loan(new Person(new Name("John")),
                        Direction.OUT, new Amount(420), LocalDate.now(),
                        new Description("Paid for his lunch."), Status.UNPAID),
                new Loan(new Person(new Name("Mary")),
                        Direction.IN, new Amount(10000), LocalDate.of(2019, 9, 23),
                        new Description("Owe them for that party."), Status.UNPAID),
                new Loan(new Person(new Name("Bill")),
                        Direction.OUT, new Amount(65674), LocalDate.of(2018, 12, 3),
                        new Description("Bought his laptop for him..."), Status.UNPAID),
                new Loan(new Person(new Name("Adam")),
                        Direction.OUT, new Amount(8920), LocalDate.of(2019, 6, 9),
                        new Description("Lent him for a game."), Status.PAID),
                new Loan(new Person(new Name("Kris")),
                        Direction.IN, new Amount(12134), LocalDate.of(2017, 7, 29),
                        new Description("She's a kind soul."), Status.PAID),
                new Loan(new Person(new Name("Vladimir")),
                        Direction.IN, new Amount(345), LocalDate.of(2019, 8, 14),
                        new Description("Hopefully he's forgotten by now."), Status.UNPAID),
                new Loan(new Person(new Name("Charles")),
                        Direction.OUT, new Amount(56), LocalDate.of(2011, 1, 31),
                        new Description("Don't know if I should ask for this back."), Status.UNPAID));
    }

    public static LoansManager getSampleLoansManager() {
        return new LoansManager(getSampleLoans());
    }
}
