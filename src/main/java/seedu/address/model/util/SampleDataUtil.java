package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.budget.ReadOnlyBudgetList;
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

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Coffee"), new Amount("1.8"), new Currency("SGD"),
                new Date("1245"),
                getTagSet("food")),
            new Expense(new Name("Textbook"), new Amount("23.50"), new Currency("SGD"),
                new Date("930"),
                getTagSet("education", "school")),
            new Expense(new Name("Earphone"), new Amount("45"), new Currency("SGD"),
                new Date("10/12/2019 1800"), getTagSet("utility")),
            new Expense(new Name("Hang out"), new Amount("50"), new Currency("SGD"),
                new Date("15/12/2019 2100"),
                getTagSet("entertainment")),
            new Expense(new Name("Travel to Paris"), new Amount("850"), new Currency("SGD"),
                new Date("25/12/2019 800"),
                getTagSet("travel")),
            new Expense(new Name("Gift for duke"), new Amount("30"), new Currency("SGD"),
                new Date("1/11/2019"),
                getTagSet("relationship"))
        };
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

    public static ReadOnlyBudgetList getSampleBudgetList() {
        BudgetList sampleB1 = new BudgetList();
        sampleB1.addBudget(new Budget(new Name("Japan Travel"), new Amount("2000.00"), new Amount("2000.00"),
            new Currency("USD"), new Date("12/12/2019"), new Date("18/12/2019"), new ExpenseList()));
        return sampleB1;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }
}
