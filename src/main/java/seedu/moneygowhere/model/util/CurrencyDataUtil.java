package seedu.moneygowhere.model.util;

import seedu.moneygowhere.model.currency.Currency;

/**
 * Contains utility methods for populating currency list with sample data.
 */
public class CurrencyDataUtil {

    /**
     * Gets sample currencies.
     * @return a list of currencies.
     */
    public static Currency[] getSampleCurrencies() {
        return new Currency[] {
            new Currency("SGD", "$", 1.00),
            new Currency("USD", "$", 0.73),
            new Currency("EUR", "€", 0.66),
            new Currency("MYR", "RM", 3.07),
            new Currency("INR", "₹", 51.92),
            new Currency("IDR", "Rp", 10304.01),
            new Currency("MMK", "K", 1119.53)
        };
    }

    public static Currency getDefaultCurrency() {
        return new Currency("SGD", "$", 1.00);
    }
}
