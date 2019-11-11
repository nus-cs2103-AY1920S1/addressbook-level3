package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.template.ListTemplateItemCommand.MESSAGE_SUCCESS;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.templatelist.template.ListTemplateItemCommand;
import seedu.ifridge.logic.parser.templatelist.template.ListTemplateItemCommandParser;

public class ListTemplateItemCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTemplateItemCommand.MESSAGE_USAGE);
    private static final String MESSAGE_VALID_FORMAT = String.format(MESSAGE_SUCCESS);

    private ListTemplateItemCommandParser parser = new ListTemplateItemCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidParts_failure() {
        // invalid index
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsValid_success() {
        assertParseSuccess(parser, "1", new ListTemplateItemCommand(Index.fromOneBased(1)));
    }
}
