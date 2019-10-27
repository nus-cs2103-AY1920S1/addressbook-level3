package seedu.address.logic.parser.listcommandparser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listcommand.ListMentorCommand;
import seedu.address.logic.commands.listcommand.ListParticipantCommand;
import seedu.address.logic.commands.listcommand.ListTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ListCommandParserTest {

    private static final String LIST_MENTOR_KEYWORD = "mentors";
    private static final String LIST_PARTICIPANT_KEYWORD = "participants";
    private static final String LIST_TEAM_KEYWORD = "teams";
    private static final String INCORRECT_INPUT = "wrong";
    private static final String EMPTY_INPUT = "wrong";

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_correctUserInput_appropriateCommandReturned() throws ParseException {
        assertTrue(parser.parse(LIST_MENTOR_KEYWORD) instanceof ListMentorCommand);

        assertTrue(parser.parse(LIST_TEAM_KEYWORD) instanceof ListTeamCommand);

        assertTrue(parser.parse(LIST_PARTICIPANT_KEYWORD) instanceof ListParticipantCommand);
    }

    @Test
    public void parse_incorrectUserInput_parseExceptionThrown() throws ParseException {
        assertThrows(ParseException.class, () -> parser.parse(INCORRECT_INPUT));
        assertThrows(ParseException.class, () -> parser.parse(EMPTY_INPUT));
    }
}
