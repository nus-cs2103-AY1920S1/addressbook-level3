package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.budget.Budget;
import seedu.address.model.BudgetList;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;
import seedu.address.model.rates.Rates;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ExpenseList} with sample data.
 */
public class SampleDataUtil {
    private static ExchangeData exchangeRates = getSampleExchangeData();

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Coffee"), new Amount("1.8"),
                new Currency("SGD", getRate("SGD")),
                new Date("1245"), new Tag("food")),
            new Expense(new Name("Textbook"), new Amount("23.50"),
                new Currency("CHF", getRate("CHF")),
                new Date("930"), new Tag("education")),
            new Expense(new Name("Earphone"), new Amount("45"),
                new Currency("USD", getRate("USD")),
                new Date("10/12/2019 1800"), new Tag("utility")),
            new Expense(new Name("Hang out"), new Amount("50"),
                new Currency("SGD", getRate("SGD")),
                new Date("15/12/2019 2100"), new Tag("")),
            new Expense(new Name("Travel to Paris"), new Amount("50000"),
                new Currency("KRW", getRate("EUR")),
                new Date("25/12/2019 800"), new Tag("travel")),
            new Expense(new Name("Gift for duke"), new Amount("30"),
                new Currency("SGD", getRate("SGD")),
                new Date("1/11/2019"), new Tag("gifts")),
            new Expense(new Name("Gift for duchess"), new Amount("100"),
                new Currency("EUR", getRate("EUR")),
                new Date("8/12/2019"), new Tag("gifts")),
            new Expense(new Name("Phone charger"), new Amount("30"),
                new Currency("MYR", getRate("MYR")),
                new Date("8/8/2019"), new Tag("utility")),
            new Expense(new Name("Television"), new Amount("433"),
                new Currency("MXN", getRate("MXN")),
                new Date("13/5/2019"), new Tag("")),
            new Expense(new Name("Japanese mocha"), new Amount("990"),
                new Currency("JPY", getRate("JPY")),
                new Date("19/4/2019"), new Tag("food")),
            new Expense(new Name("Philippine Coffee"), new Amount("6435"),
                new Currency("PHP", getRate("PHP")),
                new Date("22/3/2019"), new Tag("food")),
            new Expense(new Name("Another Gift for duke"), new Amount("30"),
                new Currency("SGD", getRate("SGD")),
                new Date("1/11/2019"), new Tag("gifts"))
        };
    }

    private static double getRate(String country) {
        return exchangeRates.getRates().getRate(country);
    }
    public static ExchangeData getSampleExchangeData() {
        Rates rates = new Rates();
        rates.addRate("CAD", 0.9631651648);
        rates.addRate("HKD", 5.764322831);
        rates.addRate("ISK", 91.6266526343);
        rates.addRate("PHP", 37.5228573308);
        rates.addRate("DKK", 4.9140301256);
        rates.addRate("HUF", 217.0492665921);
        rates.addRate("CZK", 16.8585147668);
        rates.addRate("GBP", 0.5652173913);
        rates.addRate("RON", 3.1299085707);
        rates.addRate("SEK", 7.0585410774);
        rates.addRate("IDR", 10335.5193054003);
        rates.addRate("INR", 52.0745905413);
        rates.addRate("BRL", 3.0295994212);
        rates.addRate("RUB", 46.791620075);
        rates.addRate("HRK", 4.8923238834);
        rates.addRate("JPY", 79.7803065185);
        rates.addRate("THB", 22.2350851806);
        rates.addRate("CHF", 0.7236729593);
        rates.addRate("EUR", 0.6577649148);
        rates.addRate("MYR", 3.0723541406);
        rates.addRate("BGN", 1.2864566204);
        rates.addRate("TRY", 4.2785634414);
        rates.addRate("CNY", 5.1979872394);
        rates.addRate("NOK", 6.6853910412);
        rates.addRate("NZD", 1.1466157995);
        rates.addRate("ZAR", 10.8420048675);
        rates.addRate("USD", 0.7349207393);
        rates.addRate("MXN", 14.0761691771);
        rates.addRate("SGD", 1.0);
        rates.addRate("AUD", 1.0682102217);
        rates.addRate("ILS", 2.5971189897);
        rates.addRate("KRW", 861.0405840952);
        rates.addRate("PLN", 2.8144445175);
        ExchangeData exchangeData = new ExchangeData(
            new Date(
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            ), new Currency("SGD"), rates);
        return exchangeData;
    }

    public static ReadOnlyExpenseList getSampleExpenseList() {
        ExpenseList sampleEl = new ExpenseList();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleEl.addExpense(sampleExpense);
        }
        return sampleEl;
    }

    public static Budget[] getSampleBudgets() {
        return new Budget[] {
            new Budget(new Name("Japan Travel"), new Amount("100000.00"), new Amount("100000.00"),
                new Currency("JPY"), new Date("12/12/2019"), new Date("18/12/2019"), getJapanTravelExpenseList()),
            new Budget(new Name("Business Budget"), new Amount("2000.00"), new Amount("2000.00"),
                new Currency("USD"), new Date("10/5/2019"), new Date("18/5/2019"), getBusinessBudgetExpenseList()),
            new Budget(new Name("January 19 Budget"), new Amount("500.00"), new Amount("1000.00"),
                new Currency("SGD"), new Date("1/1/2019"), new Date("31/1/2019"), getJanuaryBudgetExpenseList()),
            new Budget(new Name("Wedding Budget"), new Amount("40000.00"), new Amount("40000.00"),
                new Currency("SGD"), new Date("5/7/2019"), new Date("12/7/2019"), getWeddingBudgetExpenseList()),
            new Budget(new Name("Family Travel in SG"), new Amount("600.00"), new Amount("600.00"),
                new Currency("SGD"), new Date("24/6/2019"), new Date("24/6/2019"), getFamilyTravelExpenseList()),
        };
    }

    public static ExpenseList getJapanTravelExpenseList() {
        ExpenseList JapanTravel = new ExpenseList();
        JapanTravel.addExpense(new Expense(new Name("Japanese Coffee"), new Amount("1000"),
            new Currency("JPY", getRate("JPY")), new Date("12/12/2019"), new Tag("food")));
        JapanTravel.addExpense(new Expense(new Name("Japanese Ramen"), new Amount("7900"),
            new Currency("JPY", getRate("JPY")), new Date("13/12/2019"), new Tag("food")));
        JapanTravel.addExpense(new Expense(new Name("Bus fare"), new Amount("800"),
            new Currency("JPY", getRate("JPY")), new Date("13/12/2019"), new Tag("transport")));
        JapanTravel.addExpense(new Expense(new Name("Hotel"), new Amount("5000"),
            new Currency("JPY", getRate("JPY")), new Date("12/12/2019"), new Tag("accommodation")));
        JapanTravel.addExpense(new Expense(new Name("Temple and Shrine"), new Amount("12000"),
            new Currency("JPY", getRate("JPY")), new Date("14/12/2019"), new Tag("adventure")));
        JapanTravel.addExpense(new Expense(new Name("Mountain climbinb"), new Amount("17000"),
            new Currency("JPY", getRate("JPY")), new Date("12/12/2019"), new Tag("adventure")));
        return JapanTravel;
    }

    public static final ExpenseList getWeddingBudgetExpenseList() {
        ExpenseList WeddingBudget = new ExpenseList();
        WeddingBudget.addExpense(new Expense(new Name("Balloons"), new Amount("500"),
            new Currency("SGD", getRate("SGD")), new Date("6/7/2019"), new Tag("decoration")));
        WeddingBudget.addExpense(new Expense(new Name("Flowers"), new Amount("700"),
            new Currency("SGD", getRate("SGD")), new Date("7/7/2019"), new Tag("decoration")));
        WeddingBudget.addExpense(new Expense(new Name("Hotel"), new Amount("985"),
            new Currency("SGD", getRate("SGD")), new Date("7/7/2019"), new Tag("accomodation")));
        WeddingBudget.addExpense(new Expense(new Name("Photoshoot"), new Amount("1200"),
            new Currency("SGD", getRate("SGD")), new Date("9/7/2019"), new Tag("")));
        WeddingBudget.addExpense(new Expense(new Name("Attire"), new Amount("700"),
            new Currency("SGD", getRate("SGD")), new Date("10/7/2019"), new Tag("rent")));
        return WeddingBudget;
    }

    public static final ExpenseList getJanuaryBudgetExpenseList() {
        ExpenseList JanuaryBudget = new ExpenseList();
        JanuaryBudget.addExpense(new Expense(new Name("Toys for child"), new Amount("50"),
            new Currency("SGD", getRate("SGD")), new Date("3/1/2019"), new Tag("toy")));
        JanuaryBudget.addExpense(new Expense(new Name("Expensive chicken rice"), new Amount("40"),
            new Currency("SGD", getRate("SGD")), new Date("7/1/2019"), new Tag("food")));
        JanuaryBudget.addExpense(new Expense(new Name("Table"), new Amount("120"),
            new Currency("SGD", getRate("SGD")), new Date("4/1/2019"), new Tag("furniture")));
        JanuaryBudget.addExpense(new Expense(new Name("Laptop"), new Amount("1500"),
            new Currency("SGD", getRate("SGD")), new Date("13/1/2019"), new Tag("")));
        JanuaryBudget.addExpense(new Expense(new Name("Phone bill"), new Amount("40"),
            new Currency("SGD", getRate("SGD")), new Date("23/1/2019"), new Tag("bill")));
        JanuaryBudget.addExpense(new Expense(new Name("Phone card"), new Amount("20"),
            new Currency("SGD", getRate("SGD")), new Date("16/1/2019"), new Tag("")));
        JanuaryBudget.addExpense(new Expense(new Name("Chair"), new Amount("100"),
            new Currency("SGD", getRate("SGD")), new Date("18/1/2019"), new Tag("furniture")));
        return JanuaryBudget;
    }

    public static final ExpenseList getBusinessBudgetExpenseList() {
        ExpenseList BusinessBudget = new ExpenseList();
        BusinessBudget.addExpense(new Expense(new Name("Business lunch"), new Amount("150"),
            new Currency("USD", getRate("USD")), new Date("12/5/2019"), new Tag("food")));
        BusinessBudget.addExpense(new Expense(new Name("Travel"), new Amount("30"),
            new Currency("USD", getRate("USD")), new Date("13/5/2019"), new Tag("travel")));
        BusinessBudget.addExpense(new Expense(new Name("Supplier expenditure"), new Amount("200"),
            new Currency("USD", getRate("USD")), new Date("15/5/2019"), new Tag("")));
        BusinessBudget.addExpense(new Expense(new Name("Bribery"), new Amount("50"),
            new Currency("USD", getRate("USD")), new Date("11/5/2019"), new Tag("")));
        BusinessBudget.addExpense(new Expense(new Name("Business Taxes"), new Amount("1150"),
            new Currency("USD", getRate("USD")), new Date("11/5/2019"), new Tag("tax")));
        return BusinessBudget;
    }

    public static final ExpenseList getFamilyTravelExpenseList() {
        ExpenseList FamilyTravelBudget = new ExpenseList();
        FamilyTravelBudget.addExpense(new Expense(new Name("Family lunch"), new Amount("70"),
            new Currency("SGD", getRate("SGD")), new Date("24/6/2019"), new Tag("food")));
        FamilyTravelBudget.addExpense(new Expense(new Name("Taxi"), new Amount("40"),
            new Currency("SGD", getRate("SGD")), new Date("24/6/2019"), new Tag("transport")));
        FamilyTravelBudget.addExpense(new Expense(new Name("Tickets to Gardens by the Bay"), new Amount("150"),
            new Currency("SGD", getRate("SGD")), new Date("24/6/2019"), new Tag("tickets")));
        FamilyTravelBudget.addExpense(new Expense(new Name("Jurong East Swimming Pool"), new Amount("10"),
            new Currency("SGD", getRate("SGD")), new Date("24/6/2019"), new Tag("tickets")));
        FamilyTravelBudget.addExpense(new Expense(new Name("Singapore Zoo tickets"), new Amount("40"),
            new Currency("SGD", getRate("SGD")), new Date("24/6/2019"), new Tag("tickets")));
        return FamilyTravelBudget;
    }

    public static ReadOnlyBudgetList getSampleBudgetList() {
        BudgetList sampleBl = new BudgetList();
        for (Budget sampleBudget : getSampleBudgets()) {
            sampleBl.addBudget(sampleBudget);
        }
        return sampleBl;
    }

}
