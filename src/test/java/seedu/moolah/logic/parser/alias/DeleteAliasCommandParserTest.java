package seedu.moolah.logic.parser.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.alias.DeleteAliasCommand;
import seedu.moolah.logic.parser.exceptions.ParseException;

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
        try {
            assertEquals(
                    parser.parse(aliasName1),
                    new DeleteAliasCommand(aliasName1));
        } catch (ParseException e) {
            throw new AssertionError("Should not be thrown.");
        }
        // ignore leading and trailing white spaces
        try {
            assertEquals(
                    parser.parse("      " + aliasName1 + "  "),
                    new DeleteAliasCommand(aliasName1));
        } catch (ParseException e) {
            throw new AssertionError("Should not be thrown.");
        }

        // white spaces inside
        String aliasName2 = "some  word";
        try {
            assertEquals(
                    parser.parse(aliasName2),
                    new DeleteAliasCommand(aliasName2));
        } catch (ParseException e) {
            throw new AssertionError("Should not be thrown.");
        }
    }
}
