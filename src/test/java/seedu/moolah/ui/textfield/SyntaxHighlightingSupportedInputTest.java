package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.ui.textfield.SyntaxHighlightingSupportedInput.INPUT_PATTERN_TEMPLATE;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.parser.Prefix;

/**
 * Contains tests for {@code SyntaxHighlightingSupportedInput}.
 */
class SyntaxHighlightingSupportedInputTest {

    private static final String COMMAND0 = "command0";
    private static final String COMMAND1 = "command1";
    private static final String COMMAND2 = "command2";

    private static final Prefix prefixStub0 = new Prefix("a/", "aaa");
    private static final Prefix prefixStub1 = new Prefix("/b", "bbb");
    private static final Prefix prefixStub2 = new Prefix("-c", "ccc");

    private static final SyntaxHighlightingSupportedInput SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0 =
            new SyntaxHighlightingSupportedInput(COMMAND0, List.of(prefixStub0, prefixStub1), List.of(prefixStub2));
    private static final SyntaxHighlightingSupportedInput SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1 =
            new SyntaxHighlightingSupportedInput(COMMAND1, List.of(prefixStub2, prefixStub0), List.of(prefixStub1));
    private static final SyntaxHighlightingSupportedInput SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_2 =
            new SyntaxHighlightingSupportedInput(COMMAND2, List.of(prefixStub1, prefixStub2), List.of(prefixStub0));

    @Test
    void getCommand() {
        assertEquals(COMMAND0, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getCommand());
        assertEquals(COMMAND1, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getCommand());
        assertEquals(COMMAND2, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_2.getCommand());

        assertNotEquals(COMMAND1, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getCommand());
        assertNotEquals(COMMAND2, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getCommand());
        assertNotEquals(COMMAND0, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_2.getCommand());
    }

    @Test
    void getDescription() {
        assertEquals(
                prefixStub0.getDescriptionOfArgument(),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getDescription(prefixStub0.getPrefix()));
        assertEquals(
                prefixStub1.getDescriptionOfArgument(),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getDescription(prefixStub1.getPrefix()));

        assertEquals("", SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getDescription("notAprefix"));

        assertNotEquals(
                prefixStub0.getDescriptionOfArgument(),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getDescription(prefixStub1.getPrefix()));
        assertNotEquals(
                prefixStub2.getDescriptionOfArgument(),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getDescription(prefixStub1.getPrefix()));
    }

    @Test
    void getPattern() {
        Function<Integer, String> zero = num -> "(?<prefix" + num + "> " + prefixStub0.getPrefix() + ")|";
        Function<Integer, String> one = num -> "(?<prefix" + num + "> " + prefixStub1.getPrefix() + ")|";
        Function<Integer, String> two = num -> "(?<prefix" + num + "> " + prefixStub2.getPrefix() + ")|";

        String expected0 = String.format(
                INPUT_PATTERN_TEMPLATE,
                COMMAND0,
                String.join("", zero.apply(0), one.apply(1), two.apply(2)));

        String expected1 = String.format(
                INPUT_PATTERN_TEMPLATE,
                COMMAND1,
                String.join("", two.apply(0), zero.apply(1), one.apply(2)));

        assertEquals(expected0, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPattern().pattern());
        assertEquals(expected1, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getPattern().pattern());

        assertNotEquals(expected1, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPattern().pattern());
        assertNotEquals(expected0, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_1.getPattern().pattern());
    }

    @Test
    void getPrefix() {
        assertEquals(prefixStub0, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefix(prefixStub0.getPrefix()));
        assertEquals(prefixStub1, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefix(prefixStub1.getPrefix()));

        assertNotEquals(prefixStub0, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefix(prefixStub1.getPrefix()));
        assertNotEquals(prefixStub1, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefix(prefixStub2.getPrefix()));
    }

    @Test
    void getPrefixCount() {
        assertEquals(3, SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefixCount());
    }

    @Test
    void getPrefixes() {
        assertEquals(
                List.of(prefixStub0, prefixStub1, prefixStub2),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefixes());
        assertEquals(
                List.of(prefixStub0, prefixStub1, prefixStub2),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefixes());
        assertEquals(
                List.of(prefixStub0, prefixStub1, prefixStub2),
                SUT_WITH_2_REQUIRED_1_OPTIONAL_PREFIX_0.getPrefixes());
    }
}
