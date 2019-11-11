package seedu.tarence.logic.parser;

/**
 * Represents a single prefix and its corresponding argument value.
 */
class ArgumentSingleValue {
    private Prefix prefix;
    private String value;

    ArgumentSingleValue(Prefix prefix, String value) {
        this.prefix = prefix;
        this.value = value;
    }

    Prefix getPrefix() {
        return prefix;
    }

    String getValue() {
        return value;
    }
}
