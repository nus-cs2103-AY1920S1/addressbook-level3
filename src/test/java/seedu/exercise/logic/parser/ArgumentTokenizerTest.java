package seedu.exercise.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.CommonTestData;

public class ArgumentTokenizerTest {

    @Test
    public void tokenize_emptyArgsString_noValues() {
        String argsString = "  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, CommonTestData.P_SLASH);

        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, CommonTestData.P_SLASH);
    }

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code prefix} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Prefix)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefix).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Prefix prefix) {
        assertFalse(argMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMultimap, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        // Preamble present
        String argsString = "  Some preamble string p/ Argument value ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, CommonTestData.P_SLASH);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, CommonTestData.P_SLASH, "Argument value");

        // No preamble
        argsString = " p/   Argument value ";
        argMultimap = ArgumentTokenizer.tokenize(argsString, CommonTestData.P_SLASH);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, CommonTestData.P_SLASH, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        // Only two arguments are present
        String argsString = "SomePreambleString -t dashT-Value p/pSlash value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString,
                CommonTestData.P_SLASH, CommonTestData.DASH_T, CommonTestData.HAT_Q);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, CommonTestData.P_SLASH, "pSlash value");
        assertArgumentPresent(argMultimap, CommonTestData.DASH_T, "dashT-Value");
        assertArgumentAbsent(argMultimap, CommonTestData.HAT_Q);

        // All three arguments are present
        argsString = "Different Preamble String ^Q111 -t dashT-Value p/pSlash value";
        argMultimap = ArgumentTokenizer.tokenize(argsString,
                CommonTestData.P_SLASH, CommonTestData.DASH_T, CommonTestData.HAT_Q);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, CommonTestData.P_SLASH, "pSlash value");
        assertArgumentPresent(argMultimap, CommonTestData.DASH_T, "dashT-Value");
        assertArgumentPresent(argMultimap, CommonTestData.HAT_Q, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = ArgumentTokenizer.tokenize(argsString,
                CommonTestData.P_SLASH, CommonTestData.DASH_T, CommonTestData.HAT_Q);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, CommonTestData.P_SLASH);

        /** Also covers: testing for prefixes not specified as a prefix **/

        // Prefixes not previously given to the tokenizer should not return any values
        argsString = CommonTestData.UNKNOWN_PREFIX + "some value";
        argMultimap = ArgumentTokenizer.tokenize(argsString,
                CommonTestData.P_SLASH, CommonTestData.DASH_T, CommonTestData.HAT_Q);
        assertArgumentAbsent(argMultimap, CommonTestData.UNKNOWN_PREFIX);
        assertPreamblePresent(argMultimap, argsString); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q -t another dashT value p/ pSlash value -t";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString,
                CommonTestData.P_SLASH, CommonTestData.DASH_T, CommonTestData.HAT_Q);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, CommonTestData.P_SLASH, "pSlash value");
        assertArgumentPresent(argMultimap, CommonTestData.DASH_T, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMultimap, CommonTestData.HAT_Q, "", "");
    }

    @Test
    public void tokenize_multipleArgumentsJoined() {
        String argsString = "SomePreambleStringp/ pSlash joined-tjoined -t not joined^Qjoined";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString,
                CommonTestData.P_SLASH, CommonTestData.DASH_T, CommonTestData.HAT_Q);
        assertPreamblePresent(argMultimap, "SomePreambleStringp/ pSlash joined-tjoined");
        assertArgumentAbsent(argMultimap, CommonTestData.P_SLASH);
        assertArgumentPresent(argMultimap, CommonTestData.DASH_T, "not joined^Qjoined");
        assertArgumentAbsent(argMultimap, CommonTestData.HAT_Q);
    }

    @Test
    public void equalsMethod() {
        Prefix aaa = new Prefix("aaa");

        assertEquals(aaa, aaa);
        assertEquals(aaa, new Prefix("aaa"));

        assertNotEquals(aaa, "aaa");
        assertNotEquals(aaa, new Prefix("aab"));
    }

}
