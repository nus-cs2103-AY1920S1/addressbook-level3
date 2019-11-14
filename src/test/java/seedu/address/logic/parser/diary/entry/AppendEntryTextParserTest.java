package seedu.address.logic.parser.diary.entry;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import seedu.address.logic.commands.diary.entry.AppendEntryTextCommand;
import seedu.address.model.diary.DiaryTestUtil;

/**
 * Unit test of {@link AppendEntryTextParser}.
 */
class AppendEntryTextParserTest {

    private static final int TEST_FACTORY_COUNT = 15;

    @Test
    void parse_inputNull_throwsNullPointer() {
        AppendEntryTextParser appendEntryTextParser = new AppendEntryTextParser();
        assertThrows(NullPointerException.class, () -> appendEntryTextParser.parse(null));
    }

    /**
     * Generates test cases of valid descriptions using {@link DiaryTestUtil}.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_inputNotNull_returnsAppendEntryTextCommandWithLeadingWhiteSpaceStripped() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    String testString = " " + DiaryTestUtil.generateRandomText();

                    return dynamicTest(String.format("AppendEntryTextParser inputNotNull %s", testString), () ->
                            assertParseSuccess(new AppendEntryTextParser(), testString,
                                    new AppendEntryTextCommand(testString.stripLeading())));
                });
    }
}
