package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.DeleteInventoryCommand;

class DeleteInventoryParserTest {

    private static final int TEST_FACTORY_COUNT = 10;

    @Test
    void parse_inputNull_throwsNullPointer() {
        DeleteInventoryParser deleteInventoryParser = new DeleteInventoryParser();
        assertThrows(NullPointerException.class, () -> deleteInventoryParser.parse(null));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertParseFailure(new DeleteInventoryParser(), "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInventoryCommand.MESSAGE_USAGE));
    }

    /**
     * Generates test cases of valid indexes using {@code Math.random()}.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    /*
    @TestFactory
    Stream<DynamicTest> parse_validIndex_returnsCheckInventoryCommand() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    int randNum = (int) (Math.random() * (Integer.MAX_VALUE - 1)) + 1;

                    return DynamicTest.dynamicTest(
                            String.format("DeleteInventoryParser validIndex %d", randNum), () ->
                                    assertParseSuccess(new DeleteInventoryParser(), String.valueOf(randNum),
                                            new DeleteInventoryCommand(Index.fromOneBased(randNum))));
                });
    }*/


}
