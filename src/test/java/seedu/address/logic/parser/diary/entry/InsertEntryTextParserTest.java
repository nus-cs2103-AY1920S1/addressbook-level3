package seedu.address.logic.parser.diary.entry;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.entry.InsertEntryTextCommand;
import seedu.address.model.diary.DiaryTestUtil;

class InsertEntryTextParserTest {

    private static final int TEST_FACTORY_COUNT = 15;

    @Test
    void parse_inputNull_throwsNullPointer() {
        InsertEntryTextParser insertEntryTextParser = new InsertEntryTextParser();
        assertThrows(NullPointerException.class, () -> insertEntryTextParser.parse(null));
    }

    @Test
    void parse_absentArguments_throwsParseException() {
        //absent description present index
        assertParseFailure(new InsertEntryTextParser(), String.format(" %1$s%2$d", PREFIX_INDEX, 10),
                String.format(MESSAGE_MISSING_FIELDS, InsertEntryTextCommand.MESSAGE_USAGE));

        //absent index present description
        assertParseFailure(new InsertEntryTextParser(), String.format(" %1$s%2$s", PREFIX_DESCRIPTION, "abc"),
                String.format(MESSAGE_MISSING_FIELDS, InsertEntryTextCommand.MESSAGE_USAGE));

        //both absent
        assertParseFailure(new InsertEntryTextParser(), " ",
                String.format(MESSAGE_MISSING_FIELDS, InsertEntryTextCommand.MESSAGE_USAGE));
    }

    /**
     * Generates test cases of valid descriptions using {@link DiaryTestUtil}, with a fixed valid index.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_fixedValidIndexValidDescriptions_returnsInsertEntryTextCommand() {
        int testIndex = 10;

        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    String testDescription = DiaryTestUtil.generateRandomText().trim();
                    String userInput = String.format(" %1$s%2$s %3$s%4$d",
                            PREFIX_DESCRIPTION, testDescription,
                            PREFIX_INDEX, testIndex);

                    return dynamicTest(String.format("InsertEntryTextParser fixed index random description: %s",
                            testDescription), () ->
                                    assertParseSuccess(
                                            new InsertEntryTextParser(),
                                            userInput,
                                            new InsertEntryTextCommand(testDescription,
                                                    Index.fromOneBased(testIndex))));
                });
    }

    /**
     * Generates test cases of valid indexes using {@code Math.random()}, with a fixed valid description.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_fixedDescriptionValidIndexes_returnsInsertEntryTextCommand() {
        String testDescription = "abc";

        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    int randNum = (int) (Math.random() * (Integer.MAX_VALUE - 1)) + 1;
                    String userInput = String.format(" %1$s%2$s %3$s%4$d",
                            PREFIX_DESCRIPTION, testDescription,
                            PREFIX_INDEX, randNum);

                    return dynamicTest(
                            String.format("InsertEntryTextParser fixed descrpition random index: %d", randNum), () ->
                                    assertParseSuccess(new InsertEntryTextParser(), userInput,
                                            new InsertEntryTextCommand(testDescription, Index.fromOneBased(randNum))));
                });
    }

    @Test
    void parse_validDescriptionInvalidIndex_throwsParseException() {
        String testDescription = "abc";
        String invalidIndex = "abc 10";

        assertParseFailure(new InsertEntryTextParser(),
                String.format(" %1$s%2$s %3$s%4$s", PREFIX_DESCRIPTION, testDescription, PREFIX_INDEX, invalidIndex),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertEntryTextCommand.MESSAGE_USAGE));
    }
}
