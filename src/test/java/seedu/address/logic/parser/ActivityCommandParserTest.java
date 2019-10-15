package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ActivityCommand.MESSAGE_USAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ActivityCommand;
import seedu.address.model.activity.Title;


public class ActivityCommandParserTest {
    private ActivityCommandParser parser = new ActivityCommandParser();

    @Test
    public void parse_compulsoryFieldsPresent_success() {
        // Valid title
        assertParseSuccess(parser, " " + PREFIX_TITLE + VALID_ACTIVITY_TITLE,
                new ActivityCommand(new Title(VALID_ACTIVITY_TITLE), new ArrayList<String>()));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Title Missing
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_singleParticipantPresent_success() {
        // At least one participant exists
        String commandWithPerson = " " + PREFIX_PARTICIPANT + VALID_NAME_BOB + " "
                + PREFIX_TITLE + VALID_ACTIVITY_TITLE;
        ArrayList<String> participantList = new ArrayList<String>();
        participantList.add(VALID_NAME_BOB);
        assertParseSuccess(parser, commandWithPerson,
                new ActivityCommand(new Title(VALID_ACTIVITY_TITLE), participantList));
    }

    @Test
    public void parse_multipleParticipantPresent_success() {
        // At least one participant exists
        String commandWithPerson = " " + PREFIX_PARTICIPANT + VALID_NAME_BOB + " "
                + PREFIX_TITLE + VALID_ACTIVITY_TITLE + " " + PREFIX_PARTICIPANT + VALID_NAME_AMY;
        ArrayList<String> participantList = new ArrayList<String>();
        participantList.add(VALID_NAME_BOB);
        participantList.add(VALID_NAME_AMY);
        assertParseSuccess(parser, commandWithPerson,
                new ActivityCommand(new Title(VALID_ACTIVITY_TITLE), participantList));
    }
}
