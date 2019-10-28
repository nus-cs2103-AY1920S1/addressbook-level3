package seedu.address.commons.util;

public class IntegerPairUtil implements Comparable<IntegerPairUtil> {
    int key;
    String value;

    public IntegerPairUtil(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    private boolean isSameKey(IntegerPairUtil other) {
        return this.key == other.key;
    }

    private int compareKey(IntegerPairUtil other) {
        return Integer.compare(this.key, other.key);
    }

    private int compareValue(IntegerPairUtil other) {
        return this.value.compareToIgnoreCase(other.value);
    }

    @Override
    public int compareTo(IntegerPairUtil other) {
        return this.isSameKey(other) ? this.compareValue(other) : this.compareKey(other);
    }

    @Override
    public String toString() {
        return "Key: " + key + " Value: " + value;
    }
}
