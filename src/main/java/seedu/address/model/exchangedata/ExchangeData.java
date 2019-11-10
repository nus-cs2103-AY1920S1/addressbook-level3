package seedu.address.model.exchangedata;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.rates.Rates;


/**
 * Represents the common exchange rates of currencies supported.
 * Guarantees: details are present and not null.
 */
public class ExchangeData {

    // Identity fields
    private final Date lastUpdatedDate;
    private final Currency baseCurrency;

    //Data fields
    private final Rates rates = new Rates();

    public ExchangeData() {
        lastUpdatedDate = new Date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        baseCurrency = new Currency();
    }

    public ExchangeData(ExchangeData data) {
        this.lastUpdatedDate = data.lastUpdatedDate;
        this.baseCurrency = data.baseCurrency;
        this.rates.getRates().putAll(data.getRates().getRates());
    }

    /**
     * Every field must be present and not null.
     */
    public ExchangeData(Date lastUpdatedDate, Currency baseCurrency, Rates rates) {
        requireAllNonNull(lastUpdatedDate, baseCurrency, rates);
        this.lastUpdatedDate = lastUpdatedDate;
        this.baseCurrency = baseCurrency;
        this.rates.getRates().putAll(rates.getRates());
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Rates getRates() {
        return rates;
    }

    public boolean isValidCurrency(String currency) {
        return rates.getSupportedCurrencies().contains(currency);
    }

    /**
     * Returns true if both ExchangeData of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two ExchangeData.
     */
    public boolean isSameExchangeData(ExchangeData otherExchangeData) {
        if (otherExchangeData == this) {
            return true;
        }

        return otherExchangeData != null
            && otherExchangeData.getLastUpdatedDate().equals(getLastUpdatedDate())
            && otherExchangeData.getBaseCurrency().equals(getBaseCurrency());
    }

    /**
     * Returns true if both ExchangeData have the same identity and data fields.
     * This defines a stronger notion of equality between two ExchangeData.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExchangeData)) {
            return false;
        }

        ExchangeData otherExchangeData = (ExchangeData) other;
        return otherExchangeData.getLastUpdatedDate().equals(getLastUpdatedDate())
            && otherExchangeData.getBaseCurrency().equals(getBaseCurrency())
            && otherExchangeData.getRates().equals(getRates());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(lastUpdatedDate, baseCurrency, rates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n")
            .append("Base Currency: ")
            .append(getBaseCurrency())
            .append("\n")
            .append("Last Updated: ")
            .append(getLastUpdatedDate())
            .append("\n")
            .append(rates.toString());
        return builder.toString();
    }
}
