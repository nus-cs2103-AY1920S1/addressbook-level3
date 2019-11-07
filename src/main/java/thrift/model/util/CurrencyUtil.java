package thrift.model.util;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import thrift.commons.core.LogsCenter;
import thrift.model.transaction.Value;

/**
 * Contains utility methods for dealing with currencies.
 */
public class CurrencyUtil {

    private static final Logger logger = LogsCenter.getLogger(CurrencyUtil.class);

    private static Map<String, Double> defaultMappings;
    private static Map<String, Double> currencyMappings;


    public static Map<String, Double> getCurrencyMap() {
        if (currencyMappings == null) {
            currencyMappings = getDefaultMap();
        }

        return currencyMappings;
    }

    public static void setCurrencyMap(Map<String, Double> newCurrencyMappings) {
        assert newCurrencyMappings != null; //assumption: currencyMappings is not null

        if (newCurrencyMappings != null && newCurrencyMappings.size() > 0) {
            currencyMappings = newCurrencyMappings;
            logger.info("Currency mapping has been successfully modified.");
        } else {
            logger.info("Currency mapping has not been modified.");
            assert newCurrencyMappings == null; //assumption: if this else block is triggered, it is null
        }

    }

    /**
     * Converts input currency amount from {@link Value#DEFAULT_CURRENCY} denomination
     * to the currency specified in the input.
     *
     * @param currencyMappings Mapping of currency rates scaling from
     * {@link Value#DEFAULT_CURRENCY}.
     * @param value Amount to convert.
     * @param currency Target currency to convert to.
     * @return Amount in target currency as a double.
     */
    public static double convertFromDefaultCurrency(Map<String, Double> currencyMappings, double value,
                                                    String currency) {
        assert currencyMappings != null; //assumption: currencyMappings passed in is not null
        assert currency != null; //assumption: currency passed in is not null

        requireNonNull(currencyMappings);
        requireNonNull(value);
        requireNonNull(currency);
        if (currencyMappings.containsKey(currency.toUpperCase())) {
            return (value * currencyMappings.get(currency.toUpperCase()));
        } else if (getDefaultMap().containsKey(currency.toUpperCase())) {
            return (value * getDefaultMap().get(currency.toUpperCase()));
        } else {
            return value;
        }
    }

    /**
     * Converts input currency amount to {@link Value#DEFAULT_CURRENCY} denomination
     * from the currency specified in the input.
     *
     * @param currencyMappings Mapping of currency rates scaling from
     * {@link Value#DEFAULT_CURRENCY}.
     * @param value Amount to convert.
     * @param currency Target currency to convert from.
     * @return Amount in {@link Value#DEFAULT_CURRENCY} denomination as a double.
     */
    public static double convertToDefaultCurrency(Map<String, Double> currencyMappings, double value, String currency) {
        assert currencyMappings != null; //assumption: currencyMappings passed in is not null
        assert currency != null; //assumption: currency passed in is not null

        requireNonNull(currencyMappings);
        requireNonNull(value);
        requireNonNull(currency);
        if (currencyMappings.containsKey(currency.toUpperCase())) {
            return (value / currencyMappings.get(currency.toUpperCase()));
        } else if (getDefaultMap().containsKey(currency.toUpperCase())) {
            return (value / getDefaultMap().get(currency.toUpperCase()));
        } else {
            return value;
        }
    }

    /**
     * Converts input currency amount from {@code currencyFrom} to {@code currencyTo} via {@link Value#DEFAULT_CURRENCY}
     *
     * @param currencyMappings Mapping of currency rates scaling from
     * *     {@link Value#DEFAULT_CURRENCY}.
     * @param value Amount to convert
     * @param currencyFrom Target currency to convert from
     * @param currencyTo Target currency to convert to
     * @return Amount in {@code currencyTo} denomination as a double.
     */
    public static double convert(Map<String, Double> currencyMappings, double value,
                                 String currencyFrom, String currencyTo) {

        assert currencyMappings != null; //assumption: currencyMappings passed in is not null
        assert currencyTo != null; //assumption: currencyTo passed in is not null
        assert currencyFrom != null; //assumption: currencyFrom passed in is not null

        requireNonNull(currencyMappings);
        requireNonNull(value);
        requireNonNull(currencyTo);
        requireNonNull(currencyFrom);

        double valueInDefaultCurrency = convertToDefaultCurrency(currencyMappings, value, currencyFrom);

        return convertFromDefaultCurrency(currencyMappings, valueInDefaultCurrency, currencyTo);
    }

    /**
     * Returns a default mapping of currency mappings
     * @return default mapping of currency mappings
     */
    public static Map<String, Double> getDefaultMap() {
        if (defaultMappings != null) {
            return defaultMappings;
        }
        Map<String, Double> currencyMappings = new HashMap<String, Double>(0);

        currencyMappings.put("SGD", 1.0);
        currencyMappings.put("CAD", 0.9664844933);
        currencyMappings.put("HKD", 5.6971093699);
        currencyMappings.put("ISK", 91.1964179891);
        currencyMappings.put("PHP", 37.5123460855);
        currencyMappings.put("DKK", 4.9176269178);
        currencyMappings.put("HUF", 219.6022914335);
        currencyMappings.put("CZK", 17.0494501877);
        currencyMappings.put("GBP", 0.5936327122);
        currencyMappings.put("RON", 3.1291894383);
        currencyMappings.put("SEK", 7.138671232);
        currencyMappings.put("IDR", 10277.9350760519);
        currencyMappings.put("INR", 51.5937973267);
        currencyMappings.put("BRL", 2.9753078291);
        currencyMappings.put("RUB", 46.9415289392);
        currencyMappings.put("HRK", 4.8910252189);
        currencyMappings.put("JPY", 78.0404293145);
        currencyMappings.put("THB", 22.0827023112);
        currencyMappings.put("CHF", 0.7208796997);
        currencyMappings.put("EUR", 0.6584578916);
        currencyMappings.put("MYR", 3.0424046882);
        currencyMappings.put("BGN", 1.2878119444);
        currencyMappings.put("TRY", 4.275762165);
        currencyMappings.put("CNY", 5.1733061171);
        currencyMappings.put("NOK", 6.6198064134);
        currencyMappings.put("NZD", 1.1471653388);
        currencyMappings.put("ZAR", 10.9895305195);
        currencyMappings.put("USD", 0.7262790545);
        currencyMappings.put("MXN", 14.1715282808);
        currencyMappings.put("AUD", 1.0737472839);
        currencyMappings.put("ILS", 2.5478369658);
        currencyMappings.put("KRW", 866.4449858432);
        currencyMappings.put("PLN", 2.8434187134);

        defaultMappings = currencyMappings;
        return defaultMappings;
    }
}
