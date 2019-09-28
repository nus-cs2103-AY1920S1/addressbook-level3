package thrift.model.util;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import thrift.model.transaction.Value;
/**
 * Contains utility methods for dealing with currencies.
 */
public class CurrencyUtil {

    public static Map<String, Double> getCurrencyMap() {
        Map<String, Double> currencyMappings = new HashMap<>();
        currencyMappings.put("SGD", 1.00);
        currencyMappings.put("MYR", 3.03);
        currencyMappings.put("USD", 0.73);
        currencyMappings.put("EUR", 0.66);

        return currencyMappings;
    }

    /**
     * Converts input currency amount from {@link Value#DEFAULT_CURRENCY} denomination
     * to the currency specified in the input.
     *
     * @param currencyMappings Mapping of currency rates scaling from
     *     {@link Value#DEFAULT_CURRENCY}.
     * @param value Amount to convert.
     * @param currency Target currency to convert to.
     * @return Amount in target currency as a double.
     */
    public static double convertFromDefaultCurrency(Map<String, Double> currencyMappings, double value,
            String currency) {
        requireNonNull(currencyMappings);
        requireNonNull(value);
        requireNonNull(currency);
        if (currencyMappings.containsKey(currency)) {
            return (value * currencyMappings.get(currency));
        } else {
            return value;
        }
    }

    /**
     * Converts input currency amount to {@link Value#DEFAULT_CURRENCY} denomination
     * from the currency specified in the input.
     *
     * @param currencyMappings Mapping of currency rates scaling from
     *     {@link Value#DEFAULT_CURRENCY}.
     * @param value Amount to convert.
     * @param currency Target currency to convert from.
     * @return Amount in {@link Value#DEFAULT_CURRENCY} denomination as a double.
     */
    public static double convertToDefaultCurrency(Map<String, Double> currencyMappings, double value, String currency) {
        requireNonNull(currencyMappings);
        requireNonNull(value);
        requireNonNull(currency);
        if (currencyMappings.containsKey(currency)) {
            return (value / currencyMappings.get(currency));
        } else {
            return value;
        }
    }

}
