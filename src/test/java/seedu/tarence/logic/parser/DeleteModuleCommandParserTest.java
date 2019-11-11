package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_INDEX_NON_POSITIVE;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.DeleteModuleCommand;
import seedu.tarence.model.module.ModCode;

class DeleteModuleCommandParserTest {

    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    void parse_validIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteModuleCommand(INDEX_FIRST_IN_LIST));
    }

    @Test
    void parse_validModule_returnsDeleteCommand() {
        assertParseSuccess(parser, " m/GES1000", new DeleteModuleCommand(new ModCode("GES1000")));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_INDEX_NON_POSITIVE,
                DeleteModuleCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModuleCommand.MESSAGE_USAGE));
    }
}
