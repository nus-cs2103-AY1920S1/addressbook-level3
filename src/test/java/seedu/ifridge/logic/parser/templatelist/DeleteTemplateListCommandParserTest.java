package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.DeleteTemplateListCommand;

public class DeleteTemplateListCommandParserTest {

    private DeleteTemplateListCommandParser parser = new DeleteTemplateListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // single name
        assertParseSuccess(parser, "1", new DeleteTemplateListCommand(INDEX_FIRST));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateListCommand.MESSAGE_USAGE);

        // index missing
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }
    @Test
    public void parse_invalidFieldsPresent_failure() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTemplateListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateListCommand.MESSAGE_USAGE));
    }
}
