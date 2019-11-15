package seedu.address.calendar.logic.parser;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Represents an option (a user option).
 */
public class Option {
    private OptionalInt optionNumber;
    private Optional<Boolean> optionBinary;

    private Option(int option) {
        optionNumber = OptionalInt.of(option - 1); // zero-based
        optionBinary = Optional.empty();
    }

    private Option(boolean option) {
        optionNumber = OptionalInt.empty();
        optionBinary = Optional.of(option);
    }

    public static Option getFalse() {
        return new Option(false);
    }

    public static Option getTrue() {
        return new Option(true);
    }

    public static Option getInstance(int option) {
        return new Option(option);
    }

    /**
     * Checks if the user input represents a binary choice.
     *
     * @return {@code true} if the user input represents a binary choice
     */
    public boolean isBinary() {
        return optionBinary.isPresent();
    }

    public boolean isNumber() {
        return optionNumber.isPresent();
    }

    public int getNumber() {
        assert optionNumber.isPresent() : "Please call isNumber() before calling this method";
        assert optionBinary.isEmpty() : "Option must either be a yes/no option or a numerical option. Not both.";
        return optionNumber.getAsInt();
    }

    public boolean getBinaryOption() {
        assert optionBinary.isPresent() : "Please call isBinary() before calling this method";
        assert optionNumber.isEmpty() : "Option must either be a yes/no option or a numerical option. Not both.";
        return optionBinary.get();
    }
}
