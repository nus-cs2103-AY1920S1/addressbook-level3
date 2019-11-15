package seedu.address.commons.util;

/**
 * Country Data used for generating Statistics.
 */
public class CountryData implements Comparable<CountryData> {
    private int key;
    private String value;

    public CountryData(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    private boolean isSameKey(CountryData other) {
        return this.key == other.key;
    }

    private int compareKey(CountryData other) {
        return Integer.compare(this.key, other.key);
    }

    private int compareValue(CountryData other) {
        return this.value.compareToIgnoreCase(other.value);
    }

    @Override
    public int compareTo(CountryData other) {
        return this.isSameKey(other) ? this.compareValue(other) : this.compareKey(other);
    }

    @Override
    public String toString() {
        return "Key: " + key + " Value: " + value;
    }
}
