package seedu.address.model.rates;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Represents the Map of Currencies to their rates based on FOREX.
 * Guarantees: details are present and not null.
 */
public class Rates {

    /**
     * Represents the rates of currencies.
     * Guarantees: immutable.
     */

    //Data fields
    private final Map<String, Double> rates = new HashMap<>();

    /**
     * Every field must be present and not null.
     */
    public Rates() {
        requireAllNonNull(rates);
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void addRate(String key, Double value) {
        rates.put(key, value);
    }

    public Double getRate(String key) {
        return rates.get(key);
    }

    public List<String> getSupportedCurrencies() {
        return rates.keySet()
            .stream()
            .map(String::new)
            .collect(Collectors.toList());
    }

    /**
     * Returns true if both exchangeRates have the same identity and data fields.
     * This defines a stronger notion of equality between two exchangeRates.
     */
    @Override
    public boolean equals(Object other) {
        Rates toCompare = (Rates) other;

        return (toCompare.getRates().keySet().equals(this.getRates().keySet())
            && new ArrayList<>(toCompare.getRates().values()).equals(
            new ArrayList<>(this.getRates().values()))); //Arraylist Compare values
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(rates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n")
            .append(
                rates.keySet().stream()
                    .map(key -> key + ": " + rates.get(key))
                    .collect(Collectors.joining("\n"))
            );
        return builder.toString();
    }
}
