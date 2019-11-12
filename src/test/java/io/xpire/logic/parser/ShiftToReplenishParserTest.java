package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.ShiftToReplenishCommand;

public class ShiftToReplenishParserTest {

    private ShiftToReplenishCommandParser parser = new ShiftToReplenishCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid arguments
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX);

        //invalid trailing arguments
        assertParseFailure(parser, "1||||||1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        CommandParserTestUtil.assertEqualsParseSuccess(parser, "3",
                new ShiftToReplenishCommand(INDEX_THIRD_ITEM));
    }

}
