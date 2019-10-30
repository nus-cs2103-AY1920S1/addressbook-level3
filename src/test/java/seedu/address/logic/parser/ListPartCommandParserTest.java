package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.insession.ListPartCommand;
import seedu.address.logic.parser.participation.ListPartCommandParser;
import seedu.address.model.person.Name;

public class ListPartCommandParserTest {

    private ListPartCommandParser parser = new ListPartCommandParser();

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }


    // @Test
    public void parse_emptyArg_returnListPartCommand() {
        assertParseSuccess(parser, "", new ListPartCommand());
    }

    /*
    @Test
    public void parse_validArgs_returnsListPartCommand() {
        String userInput = "Test of Strength 2019";
        ListPartCommand expectedListPartCommand = new ListPartCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedListPartCommand);
    }

    @Test
    public void parse_invalidCompetitionAsArgs() {
        String userInput = "naijfbgaf";
        assertParseFailure(parser, userInput, MESSAGE_COMPETITION_NOT_FOUND + userInput);
    }
     */
}
