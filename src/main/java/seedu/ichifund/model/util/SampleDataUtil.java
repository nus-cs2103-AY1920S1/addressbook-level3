package seedu.ichifund.model.util;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.loan.Name;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Contains utility methods for populating {@code FundBook} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
            new Transaction(new Description("Lunch at Menya Kokoro"), new Amount("14.00"), new Category("Food"),
                    new Date(new Day("2"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Joker movie ticket"), new Amount("12.00"),
                    new Category("Entertainment"), new Date(new Day("2"), new Month("11"), new Year("2019")),
                    new TransactionType("exp"), new RepeaterUniqueId("")),
            new Transaction(new Description("Dinner at Marche"), new Amount("21.03"), new Category("Food"),
                    new Date(new Day("2"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Allowance"), new Amount("1337"), new Category("Allowance"),
                    new Date(new Day("1"), new Month("11"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Grab to school"), new Amount("21.03"), new Category("Transportation"),
                    new Date(new Day("31"), new Month("10"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("LiHo honey green tea"), new Amount("2.80"), new Category("Food"),
                    new Date(new Day("31"), new Month("10"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Lunch at school"), new Amount("4.80"), new Category("Food"),
                    new Date(new Day("31"), new Month("10"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Grab back home"), new Amount("21.01"), new Category("Transportation"),
                    new Date(new Day("30"), new Month("10"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId(""))
        };
    }

    public static Repeater[] getSampleRepeaters() {
        return new Repeater[] {
            new Repeater(new RepeaterUniqueId("0"), new Description("Phone bills"), new Amount("65.00"),
                    new Category("Utilities"), new TransactionType("exp"), new MonthOffset("1"),
                    new MonthOffset("-1"), new Date(new Day("1"), new Month("10"), new Year("2019")),
                    new Date(new Day("1"), new Month("12"), new Year("2019"))),
            new Repeater(new RepeaterUniqueId("1"), new Description("Transport Concession"), new Amount("88.00"),
                    new Category("Transportation"), new TransactionType("exp"), new MonthOffset("3"),
                    new MonthOffset("-1"), new Date(new Day("3"), new Month("10"), new Year("2019")),
                    new Date(new Day("1"), new Month("12"), new Year("2019")))
        };
    }

    public static Budget[] getSampleBudgets() {
        return new Budget[] {
            new Budget(new Description("Lose weight gain moolah"), new Amount("300"), new Month("11"),
                    new Year("2019"), new Category("Food")),
            new Budget(new Description("Grab less spend less"), new Amount("200"), null, null,
                    new Category("Transportation")),
            new Budget(new Description("Play hard pay hard"), new Amount("50"), new Month("11"),
                    new Year("2019"), new Category("Entertainment"))
        };
    }

    public static Loan[] getSampleLoans() {
        return new Loan[] {
            new Loan(new LoanId("0"),
                    new Amount("420.69"), new Name("Felix Kjellberg"),
                    new Date(new Day("1"), new Month("2"), new Year("2019")),
                    new Date(new Day("2"), new Month("5"), new Year("2020")),
                    new Description("For Trees Donation")),
            new Loan(new LoanId("0"),
                    new Amount("23"), new Name("Mr. Schlansky"),
                    new Date(new Day("1"), new Month("1"), new Year("2017")),
                    new Date(new Day("1"), new Month("1"), new Year("2019")),
                    new Description("Borrowed for Nandos"))
        };
    }

    public static ReadOnlyFundBook getSampleFundBook() {
        FundBook sampleFundBook = new FundBook();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleFundBook.addTransaction(sampleTransaction);
        }

        for (Repeater sampleRepeater : getSampleRepeaters()) {
            sampleFundBook.addRepeater(sampleRepeater);
            sampleFundBook.createRepeaterTransactions(sampleRepeater);
        }
        sampleFundBook.setCurrentRepeaterUniqueId(new RepeaterUniqueId(getSampleRepeaters().length + ""));

        for (Budget sampleBudget : getSampleBudgets()) {
            sampleFundBook.addBudget(sampleBudget);
        }

        for (Loan sampleLoan : getSampleLoans()) {
            sampleFundBook.addLoan(sampleLoan);
        }
        sampleFundBook.setCurrentLoanId(new LoanId(getSampleLoans().length + ""));

        return sampleFundBook;
    }

}
