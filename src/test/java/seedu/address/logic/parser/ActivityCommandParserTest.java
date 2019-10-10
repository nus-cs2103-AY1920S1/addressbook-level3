package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ActivityCommand;

import static seedu.address.commons.core.Messages.MESSAGE_ACTIVITY_MISSING_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;


public class ActivityCommandParserTest {
    private ActivityCommandParser parser = new ActivityCommandParser();
    
    @Test
    public void parse_compulsoryFieldsPresent_success() {
        // Valid title
        assertParseSuccess(parser, " " + PREFIX_TITLE + VALID_ACTIVITY_TITLE, new ActivityCommand(VALID_ACTIVITY_TITLE));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Title Missing
        assertParseFailure(parser, "", MESSAGE_ACTIVITY_MISSING_TITLE);
    }
}
