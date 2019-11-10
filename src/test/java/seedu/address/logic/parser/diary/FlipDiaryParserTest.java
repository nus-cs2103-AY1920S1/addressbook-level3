package seedu.address.logic.parser.diary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.FlipDiaryCommand;

/**
 * Mix of unit and integration tests for {@link FlipDiaryParser}.
 */
class FlipDiaryParserTest {
    private static final int TEST_FACTORY_COUNT = 10;

    @Test
    void parse_inputNull_throwsNullPointer() {
        FlipDiaryParser flipDiaryParser = new FlipDiaryParser();
        assertThrows(NullPointerException.class, () -> flipDiaryParser.parse(null));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertParseFailure(new FlipDiaryParser(), "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlipDiaryCommand.MESSAGE_USAGE));
    }

    /**
     * Generates test cases of valid indexes using {@code Math.random()}.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_validIndex_returnsFlipDiaryCommand() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    int randNum = (int) (Math.random() * (Integer.MAX_VALUE - 1)) + 1;

                    return DynamicTest.dynamicTest(
                            String.format("FlipDiaryParser validIndex %d", randNum), () ->
                                    assertParseSuccess(new FlipDiaryParser(), String.valueOf(randNum),
                                            new FlipDiaryCommand(Index.fromOneBased(randNum))));
                });
    }
}
