package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.moolah.logic.parser.Prefix;

/**
 * Contains tests for {@code AutofillSupportedCommand}.
 */
class AutofillSupportedCommandTest {

    private static final String COMMAND_0 = "command0";
    private static final String COMMAND_1 = "command1";
    private static final AutofillSupportedCommand SUT_3 =
            new AutofillSupportedCommand(COMMAND_1, Collections.emptyList(), Collections.emptyList());
    private static Prefix prefixStub0 = new Prefix("", "") {
        @Override
        public String getPrefix() {
            return "a/";
        }
    };
    private static Prefix prefixStub1 = new Prefix("", "") {
        @Override
        public String getPrefix() {
            return "ab/";
        }
    };

    private static final AutofillSupportedCommand SUT_1 =
            new AutofillSupportedCommand(
                    COMMAND_0,
                    List.of(prefixStub0, prefixStub1),
                    Collections.emptyList());
    private static final AutofillSupportedCommand SUT_2 =
            new AutofillSupportedCommand(
                    COMMAND_0,
                    List.of(prefixStub0),
                    List.of(prefixStub1));

    @Test
    void isRequired() {
        assertTrue(SUT_1.isRequired(prefixStub0));
        assertTrue(SUT_1.isRequired(prefixStub1));

        assertTrue(SUT_2.isRequired(prefixStub0));
        assertFalse(SUT_2.isRequired(prefixStub1));
    }

    @Test
    void getCommand() {
        assertEquals(COMMAND_0, SUT_1.getCommand());
        assertEquals(COMMAND_0, SUT_2.getCommand());
        assertEquals(COMMAND_1, SUT_3.getCommand());
    }

    @Test
    void getMissingPrefixes() {
        Pair<List<Prefix>, List<Prefix>> oneOfEachPair = new Pair<>(List.of(prefixStub0), List.of(prefixStub1));
        Pair<List<Prefix>, List<Prefix>> oneRequiredPair = new Pair<>(List.of(prefixStub1), Collections.emptyList());
        Pair<List<Prefix>, List<Prefix>> oneOptionalPair = new Pair<>(Collections.emptyList(), List.of(prefixStub1));
        Pair<List<Prefix>, List<Prefix>> emptyPair = new Pair<>(Collections.emptyList(), Collections.emptyList());

        assertEquals(
                emptyPair,
                SUT_1.getMissingPrefixes(
                        String.format("%s %s %s", COMMAND_0, prefixStub0.getPrefix(), prefixStub1.getPrefix())));

        assertEquals(oneOfEachPair, SUT_2.getMissingPrefixes(String.format("%s", COMMAND_0)));

        assertEquals(
                oneRequiredPair,
                SUT_1.getMissingPrefixes(String.format("%s %s%s", COMMAND_0, prefixStub0.getPrefix(), "argument")));

        assertEquals(
                oneOptionalPair,
                SUT_2.getMissingPrefixes(String.format("%s %s ", COMMAND_0, prefixStub0.getPrefix())));

        assertEquals(emptyPair, SUT_3.getMissingPrefixes(String.format("%s", COMMAND_1)));
    }
}
