package seedu.ifridge.logic.parser.templatelist.template;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INDEX_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_INDEX_DESC;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.template.DeleteTemplateItemCommand;

public class DeleteTemplateItemCommandParserTest {

    private DeleteTemplateItemCommandParser parser = new DeleteTemplateItemCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTemplateCommand() {
        assertParseSuccess(parser, "1" + INDEX_DESC, new DeleteTemplateItemCommand(INDEX_FIRST, INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTemplateItemCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + INVALID_INDEX_DESC, String.format(MESSAGE_INVALID_INDEX,
                DeleteTemplateItemCommand.MESSAGE_SUCCESS));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTemplateItemCommand.MESSAGE_USAGE));
    }
}
