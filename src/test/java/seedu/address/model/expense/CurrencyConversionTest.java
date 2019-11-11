package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalExpenses.RUM;
import static seedu.address.testutil.TypicalExpenses.VODKA;

import org.junit.jupiter.api.Test;

import seedu.address.model.commons.Amount;
import seedu.address.model.commons.Currency;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.Tag;
import seedu.address.model.exchangedata.ExchangeDataSingleton;

public class CurrencyConversionTest {

    @Test
    public void isSameCurrencyConverted() {
        //Convert Back to SGD
        String expectedValue;
        String computedValue;

        // USD -> SGD
        expectedValue = String.format("%.2f", RUM.getAmount().getValue() / RUM.getCurrency().getRate());
        computedValue = String.format("%.2f", RUM.getConvertedAmount());

        assertTrue(expectedValue.equals(computedValue));

        // SGD -> SGD
        expectedValue = String.format("%.2f", VODKA.getAmount().getValue() / VODKA.getCurrency().getRate());
        computedValue = String.format("%.2f", VODKA.getConvertedAmount());

        assertTrue(expectedValue.equals(computedValue));

        // USD -> SGD -> MYR
        expectedValue = String.format("%.2f", (
            RUM.getAmount().getValue() / RUM.getCurrency().getRate())
            * ExchangeDataSingleton.getInstance().getRates().getRate("MYR"));
        computedValue = String.format("%.2f", RUM.getConvertedAmount(new Currency("MYR")));

        assertTrue(expectedValue.equals(computedValue));

        // SGD -> EUR max value
        expectedValue = String.format("%.2f", 999999999999.0
            / ExchangeDataSingleton.getInstance().getRates().getRate("EUR"));
        computedValue = String.format("%.2f", new Expense(
            new Name("test"),
            new Amount("999999999999"),
            new Currency("SGD", ExchangeDataSingleton.getInstance().getRates().getRate("EUR")),
            new Date("10/11/2019"),
            new Tag("")).getConvertedAmount()
        );

        assertTrue(expectedValue.equals(computedValue));
    }

}
