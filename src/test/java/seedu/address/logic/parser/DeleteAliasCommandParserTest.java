package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.alias.DeleteAliasCommand;

class DeleteAliasCommandParserTest {

    private DeleteAliasCommandParser parser = new DeleteAliasCommandParser();

    @Test
    void parse_blankArgument_parseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAliasCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullArgument_nullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    void parse_nonBlankStringArgument_success() {
        String aliasName1 = "someword";
        assertParseSuccess(
                parser,
                DeleteAliasCommand.COMMAND_WORD + " " + aliasName1,
                new DeleteAliasCommand(aliasName1));
        // ignore leading and trailing white spaces
        assertParseSuccess(
                parser,
                DeleteAliasCommand.COMMAND_WORD + "      " + aliasName1 + "      ",
                new DeleteAliasCommand(aliasName1));

        // white spaces inside
        String aliasName2 = "some  word";
        assertParseSuccess(
                parser,
                DeleteAliasCommand.COMMAND_WORD + " " + aliasName2,
                new DeleteAliasCommand(aliasName2));
    }

}