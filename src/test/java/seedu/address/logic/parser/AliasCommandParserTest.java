package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.AliasCommand;



public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        // missing alias name and input
        assertParseFailure(parser, "", expectedMessage);

        // missing input
        assertParseFailure(parser, "nothing", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid aliasname
        assertParseFailure(parser, "#asd@## nothing", Alias.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                "name input", new AliasCommand(new Alias("name", "input")));
        assertParseSuccess(parser,
                "name input input", new AliasCommand(new Alias("name", "input input")));
    }
}
