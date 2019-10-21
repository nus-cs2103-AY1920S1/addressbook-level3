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

import seedu.address.logic.commands.InviteCommand;

public class InviteCommandParserTest {
    private InviteCommandParser parser = new InviteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        List<String> peopleToInvite = new ArrayList<>();
        peopleToInvite.add(VALID_NAME_AMY);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_AMY, new InviteCommand(peopleToInvite));
    }

    @Test
    public void parse_multipleInvitees_success() {
        List<String> peopleToInvite = new ArrayList<>();
        peopleToInvite.add(VALID_NAME_AMY);
        peopleToInvite.add(VALID_NAME_BOB);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_AMY + PARTICIPANT_DESC_BOB,
                new InviteCommand(peopleToInvite));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_BOB + PARTICIPANT_DESC_AMY,
                new InviteCommand(peopleToInvite));

        List<String> invitees = new ArrayList<>();
        invitees.add(VALID_NAME_BOB);
        invitees.add(VALID_NAME_AMY);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_AMY + PARTICIPANT_DESC_BOB,
                new InviteCommand(invitees));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PARTICIPANT_DESC_BOB + PARTICIPANT_DESC_AMY,
                new InviteCommand(invitees));
    }


}
