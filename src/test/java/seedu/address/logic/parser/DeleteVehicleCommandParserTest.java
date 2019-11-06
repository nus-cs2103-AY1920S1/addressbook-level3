package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DISTRICT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VTYPE_DESC;
import static seedu.address.logic.commands.DeleteVehicleCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;

import javax.annotation.processing.Generated;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVehicleCommand;
import seedu.address.logic.parser.DeleteVehicleCommandParser;

public class DeleteVehicleCommandParserTest {
    private DeleteVehicleCommandParser parser = new DeleteVehicleCommandParser();
    private static final String MESSAGE_INVALID_INPUT = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    @Test
    public void parse_allValid_success() {
        Index index = INDEX_SECOND_ENTITY;
        int userInput = index.getOneBased();

        DeleteVehicleCommand expectedCommand = new DeleteVehicleCommand(Index.fromOneBased(userInput));

        assertParseSuccess(parser, userInput + "", expectedCommand);
    }

    @Test
    public void parse_InvalidIndex_failure() {
        String userInput = "1s";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_INPUT);
    }

}
