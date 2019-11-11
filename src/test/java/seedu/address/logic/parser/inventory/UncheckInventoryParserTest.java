package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.UncheckInventoryCommand;

class UncheckInventoryParserTest {

    //private static final int TEST_FACTORY_COUNT = 10;

    @Test
    void parse_inputNull_throwsNullPointer() {
        UncheckInventoryParser uncheckInventoryParser = new UncheckInventoryParser();
        assertThrows(NullPointerException.class, () -> uncheckInventoryParser.parse(null));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertParseFailure(new UncheckInventoryParser(), "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncheckInventoryCommand.MESSAGE_USAGE));
    }

    /**
     * Generates test cases of valid indexes using {@code Math.random()}.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */

    /*
    @TestFactory
    Stream<DynamicTest> parse_validIndex_returnsUncheckInventoryCommand() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    int randNum = (int) (Math.random() * (Integer.MAX_VALUE - 1)) + 1;

                    return DynamicTest.dynamicTest(
                            String.format("UncheckInventoryParser validIndex %d", randNum), () ->
                                    assertParseSuccess(new UncheckInventoryParser(), String.valueOf(randNum),
                                            new UncheckInventoryCommand(Index.fromOneBased(randNum))));
                });

    }*/

}
