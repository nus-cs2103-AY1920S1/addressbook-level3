package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DisinviteCommand;

public class DisinviteCommandParserTest {
    private DisinviteCommandParser parser = new DisinviteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        List<String> peopleToDisinvite = new ArrayList<>();
        peopleToDisinvite.add(VALID_NAME_AMY);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_AMY,
                new DisinviteCommand(peopleToDisinvite));
    }

    @Test
    public void parse_multiplePeople_success() {
        List<String> peopleToDisinvite = new ArrayList<>();
        peopleToDisinvite.add(VALID_NAME_AMY);
        peopleToDisinvite.add(VALID_NAME_BOB);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_AMY + PARTICIPANT_DESC_BOB,
                new DisinviteCommand(peopleToDisinvite));

        // Ensures order of entries does not matter.
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_BOB + PARTICIPANT_DESC_AMY,
                new DisinviteCommand(peopleToDisinvite));

        // Changes order of list.
        List<String> participantsToRemove = new ArrayList<>();
        participantsToRemove.add(VALID_NAME_BOB);
        participantsToRemove.add(VALID_NAME_AMY);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_AMY + PARTICIPANT_DESC_BOB,
                new DisinviteCommand(participantsToRemove));

        //Ensures order of entries does not matter.
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_BOB + PARTICIPANT_DESC_AMY,
                new DisinviteCommand(participantsToRemove));

    }
}
