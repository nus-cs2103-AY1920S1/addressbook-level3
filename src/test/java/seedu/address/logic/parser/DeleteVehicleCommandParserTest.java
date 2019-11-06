package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteVehicleCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVehicleCommand;


public class DeleteVehicleCommandParserTest {
    private static final String MESSAGE_INVALID_INPUT = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
    private DeleteVehicleCommandParser parser = new DeleteVehicleCommandParser();

    @Test
    public void parse_allValid_success() {
        Index index = INDEX_SECOND_ENTITY;
        int userInput = index.getOneBased();

        DeleteVehicleCommand expectedCommand = new DeleteVehicleCommand(Index.fromOneBased(userInput));

        assertParseSuccess(parser, userInput + "", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "1s";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_INPUT);
    }

}
