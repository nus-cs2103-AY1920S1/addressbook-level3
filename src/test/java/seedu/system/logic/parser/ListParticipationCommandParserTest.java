package seedu.system.logic.parser;

import static seedu.system.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.system.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.outofsession.ListParticipationCommand;
import seedu.system.logic.parser.outofsession.ListParticipationCommandParser;
import seedu.system.model.person.Name;

public class ListParticipationCommandParserTest {

    private ListParticipationCommandParser parser = new ListParticipationCommandParser();

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }


    // @Test
    public void parse_emptyArg_returnListPartCommand() {
        assertParseSuccess(parser, "", new ListParticipationCommand());
    }

    /*
    @Test
    public void parse_validArgs_returnsListPartCommand() {
        String userInput = "Test of Strength 2019";
        ListParticipationCommand expectedListPartCommand = new ListParticipationCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedListPartCommand);
    }

    @Test
    public void parse_invalidCompetitionAsArgs() {
        String userInput = "naijfbgaf";
        assertParseFailure(parser, userInput, MESSAGE_COMPETITION_NOT_FOUND + userInput);
    }
     */
}
