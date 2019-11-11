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
            new Transaction(new Description("Handbags"), new Amount("5278"), new Category("Luxury"),
                    new Date(new Day("2"), new Month("1"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CISCO part time"), new Amount("1700"), new Category("Income"),
                    new Date(new Day("2"), new Month("1"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Plane tickets to Japan"), new Amount("3207"), new Category("Travel"),
                    new Date(new Day("2"), new Month("2"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CISCO part time"), new Amount("1700"), new Category("income"),
                    new Date(new Day("2"), new Month("2"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Hired private jet"), new Amount("12670"), new Category("Travel"),
                    new Date(new Day("2"), new Month("3"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CISCO part time"), new Amount("1700"), new Category("income"),
                    new Date(new Day("2"), new Month("3"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Family dinner at Le Monde"), new Amount("879"), new Category("Food"),
                    new Date(new Day("2"), new Month("4"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CISCO part time"), new Amount("1500"), new Category("income"),
                    new Date(new Day("2"), new Month("4"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Bought earphones"), new Amount("430"), new Category("Audio"),
                    new Date(new Day("2"), new Month("5"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CISCO part time"), new Amount("1500"), new Category("income"),
                    new Date(new Day("2"), new Month("5"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Laptop"), new Amount("2100"), new Category("Education"),
                    new Date(new Day("2"), new Month("6"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CISCO part time"), new Amount("1500"), new Category("income"),
                    new Date(new Day("2"), new Month("6"), new Year("2019")), new TransactionType("in"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("G2000"), new Amount("75.00"), new Category("Clothes"),
                    new Date(new Day("11"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("New chair for dorm"), new Amount("35.00"), new Category("Furniture"),
                    new Date(new Day("10"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Health checkup"), new Amount("120"), new Category("Health"),
                    new Date(new Day("8"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("CLRS"), new Amount("187.00"), new Category("Education"),
                    new Date(new Day("7"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Gifts for xoxo"), new Amount("45.00"), new Category("Gifts"),
                    new Date(new Day("8"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Gold bars"), new Amount("2300"), new Category("Luxury"),
                    new Date(new Day("4"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Grab home"), new Amount("24.30"), new Category("Transportation"),
                    new Date(new Day("9"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Fined for smoking"), new Amount("1000"), new Category("Fines"),
                    new Date(new Day("1"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Lunch at Menya Kokoro"), new Amount("14.00"), new Category("Food"),
                    new Date(new Day("7"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Joker movie ticket"), new Amount("12.00"),
                    new Category("Entertainment"), new Date(new Day("2"), new Month("11"), new Year("2019")),
                    new TransactionType("exp"), new RepeaterUniqueId("")),
            new Transaction(new Description("Dinner at Marche"), new Amount("21.03"), new Category("Food"),
                    new Date(new Day("6"), new Month("11"), new Year("2019")), new TransactionType("exp"),
                    new RepeaterUniqueId("")),
            new Transaction(new Description("Sold my piano"), new Amount("5237"), new Category("Resale"),
                    new Date(new Day("9"), new Month("11"), new Year("2019")), new TransactionType("in"),
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
                    new Amount("420.69"), new Name("felix"),
                    new Date(new Day("1"), new Month("2"), new Year("2019")),
                    new Date(new Day("2"), new Month("5"), new Year("2020")),
                    new Description("For Trees Donation")),
            new Loan(new LoanId("0"),
                    new Amount("23"), new Name("kartike"),
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
