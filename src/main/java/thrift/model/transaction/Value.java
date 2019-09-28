package thrift.model.transaction;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.AppUtil.checkArgument;

import java.util.Map;

import thrift.model.util.CurrencyUtil;

/**
 * Represents a Transaction's monetary value in the Transactions list.
 * Guarantees: immutable; is valid as declared in {@link #isValidValue(String)}
 */
public class Value {

    public static final String VALUE_CONSTRAINTS =
            "Cost should only contain numbers and an optional decimal point, which if specified, accepts up to 2"
            + " decimal digits.";
    public static final String CURRENCY_CONSTRAINTS =
            "Currency should only be 'SGD', 'MYR', 'USD' OR 'EUR'!";
    public static final String VALIDATION_REGEX = "^\\d+\\.?\\d{0,2}$";
    public static final String DEFAULT_CURRENCY = "SGD";
    public final Double amount;
    public final String currency;

    /**
     * Constructs a {@code Value} with the default currency {@link #DEFAULT_CURRENCY}.
     *
     * @param value Monetary cost describing the value.
     */
    public Value(String value) {
        requireNonNull(value);
        checkArgument(isValidValue(value), VALUE_CONSTRAINTS);
        this.amount = Double.parseDouble(value);
        this.currency = DEFAULT_CURRENCY;
    }

    /**
     * Constructs a {@code Value} with a specified currency.
     *
     * @param amount Monetary cost describing the value.
     * @param currency Currency the amount is in.
     */
    public Value(String amount, String currency) {
        requireNonNull(amount);
        requireNonNull(currency);
        checkArgument(isValidValue(amount), VALUE_CONSTRAINTS);
        checkArgument(isValidCurrency(currency), CURRENCY_CONSTRAINTS);
        this.amount = Double.parseDouble(amount);
        this.currency = currency;
    }

    /**
     * Returns true if a given String is a valid currency type.
     *
     * @param currency Currency type to check if it is valid.
     * @return true if Currency type is supported.
     */
    public static boolean isValidCurrency(String currency) {
        Map<String, Double> currencyMappings = CurrencyUtil.getCurrencyMap();
        return currencyMappings.containsKey(currency);
    }

    /**
     * Returns true if a given String is a valid monetary value.
     *
     * @return true if amount is a valid double.
     */
    public static boolean isValidValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the value in {@link #DEFAULT_CURRENCY} currency amount, if the currency is supported in
     * {@link CurrencyUtil}.
     *
     * @return Value in {@link #DEFAULT_CURRENCY} denomination.
     */
    public double getMonetaryValue() {
        Map<String, Double> currencyMappings = CurrencyUtil.getCurrencyMap();
        return CurrencyUtil.convertFromDefaultCurrency(currencyMappings, amount, currency);
    }

    @Override
    public String toString() {
        return String.valueOf(getMonetaryValue());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Value // instanceof handles nulls
                && amount == (((Value) other).amount)
                && currency.equals(((Value) other).currency)); // state check
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

}
