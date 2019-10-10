package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_ACTIVITY_MISSING_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ActivityCommand;


public class ActivityCommandParserTest {
    private ActivityCommandParser parser = new ActivityCommandParser();

    @Test
    public void parse_compulsoryFieldsPresent_success() {
        // Valid title
        assertParseSuccess(parser, " " + PREFIX_TITLE + VALID_ACTIVITY_TITLE,
                new ActivityCommand(VALID_ACTIVITY_TITLE, new ArrayList<String>()));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Title Missing
        assertParseFailure(parser, "", MESSAGE_ACTIVITY_MISSING_TITLE);
    }

    @Test
    public void parse_optionalFieldsPresent_success() {
        // TODO: Update this test when person adding is complete
        // Person exists
        String commandWithPerson = " " + PREFIX_PARTICIPANT + VALID_NAME_BOB + " "
                + PREFIX_TITLE + VALID_ACTIVITY_TITLE;
        assertParseSuccess(parser, commandWithPerson,
                new ActivityCommand(VALID_ACTIVITY_TITLE, new ArrayList<String>()));
    }
}
