package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.parser.CliSyntax.MODULE_PATTERN;
import static seedu.address.logic.parser.CliSyntax.SEMESTER_PATTERN;
import static seedu.address.logic.parser.CliSyntax.TAG_PATTERN;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class ArgumentTokenizerTest {

    @Test
    public void tokenize_emptyArgsString_noValues() {
        String argsString = "  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, MODULE_PATTERN);

        assertArgumentAbsent(argMultimap, MODULE_PATTERN);
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code pattern} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Pattern)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, Pattern pattern, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(pattern).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(pattern).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(pattern).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Pattern pattern) {
        assertFalse(argMultimap.getValue(pattern).isPresent());
    }

    @Test
    public void tokenize_noPatternes_allTakenAsPreamble() {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
    }

    @Test
    public void tokenize_oneArgument() {
        String argsString = "CS3233";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, MODULE_PATTERN);
        assertArgumentPresent(argMultimap, MODULE_PATTERN, "CS3233");
    }

    @Test
    public void tokenize_multipleArguments() {
        // Only two arguments are present
        String argsString = "CS3244 Y1ST2";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString,
                MODULE_PATTERN, SEMESTER_PATTERN, TAG_PATTERN);
        assertArgumentPresent(argMultimap, MODULE_PATTERN, "CS3244");
        assertArgumentPresent(argMultimap, SEMESTER_PATTERN, "Y1ST2");
        assertArgumentAbsent(argMultimap, TAG_PATTERN);

        // All three arguments are present
        argsString = "CS3244 Y1ST2 coolstuff";
        argMultimap = ArgumentTokenizer.tokenize(argsString, MODULE_PATTERN, SEMESTER_PATTERN, TAG_PATTERN);
        assertArgumentPresent(argMultimap, MODULE_PATTERN, "CS3244");
        assertArgumentPresent(argMultimap, SEMESTER_PATTERN, "Y1ST2");
        assertArgumentPresent(argMultimap, TAG_PATTERN, "coolstuff");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = ArgumentTokenizer.tokenize(argsString, MODULE_PATTERN, SEMESTER_PATTERN, TAG_PATTERN);
        assertArgumentAbsent(argMultimap, MODULE_PATTERN);
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        // Two arguments repeated, some have empty values
        String argsString = "Y1S1 Y1S2 value CS3244";
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(argsString, MODULE_PATTERN, SEMESTER_PATTERN, TAG_PATTERN);
        assertArgumentPresent(argMultimap, MODULE_PATTERN, "CS3244");
        assertArgumentPresent(argMultimap, SEMESTER_PATTERN, "Y1S1", "Y1S2");
        assertArgumentPresent(argMultimap, TAG_PATTERN, "value");
    }
}
