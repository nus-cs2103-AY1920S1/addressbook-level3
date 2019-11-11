package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalExpenses.RUM;
import static seedu.address.testutil.TypicalExpenses.VODKA;

import org.junit.jupiter.api.Test;

import seedu.address.model.exchangedata.ExchangeDataSingleton;

public class CurrencyConversionTest {

    @Test
    public void isSameCurrencyConverted() {
        //Convert Back to SGD
        Amount expectedValue;
        Amount computedValue;

        // USD -> SGD
        expectedValue = new Amount(
            String.format("%.2f", RUM.getAmount().getValue() / RUM.getCurrency().getRate()));
        computedValue = RUM.getConvertedAmount();

        assertTrue(expectedValue.equals(computedValue));

        // SGD -> SGD
        expectedValue = new Amount(
            String.format("%.2f", VODKA.getAmount().getValue() / VODKA.getCurrency().getRate()));
        computedValue = VODKA.getConvertedAmount();

        assertTrue(expectedValue.equals(computedValue));

        // USD -> SGD -> MYR
        expectedValue = new Amount(
            String.format("%.2f", (
                RUM.getAmount().getValue() / RUM.getCurrency().getRate())
                * ExchangeDataSingleton.getInstance().getRates().getRate("MYR")));
        computedValue = RUM.getConvertedAmount(new Currency("MYR"));

        assertTrue(expectedValue.equals(computedValue));
    }

}
