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
import seedu.address.logic.commands.diary.CreateDiaryEntryCommand;

/**
 * Mix of unit and integration tests of {@link CreateDiaryEntryParser}.
 */
class CreateDiaryEntryParserTest {
    private static final int TEST_FACTORY_COUNT = 10;

    @Test
    void parse_inputNull_throwsNullPointer() {
        CreateDiaryEntryParser createDiaryEntryParser = new CreateDiaryEntryParser();
        assertThrows(NullPointerException.class, () -> createDiaryEntryParser.parse(null));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertParseFailure(new CreateDiaryEntryParser(), "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateDiaryEntryCommand.MESSAGE_USAGE));
    }

    /**
     * Generates test cases of valid indexes using {@code Math.random()}.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_validIndex_returnsCreateDiaryEntryCommand() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    int randNum = (int) (Math.random() * (Integer.MAX_VALUE - 1)) + 1;

                    return DynamicTest.dynamicTest(
                            String.format("CreateDiaryEntryParser validIndex %d", randNum), () ->
                                    assertParseSuccess(new CreateDiaryEntryParser(), String.valueOf(randNum),
                                            new CreateDiaryEntryCommand(Index.fromOneBased(randNum))));
                });
    }
}
