package seedu.weme.logic.parser.templatecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.NAME_DESC_DOGE;
import static seedu.weme.logic.commands.CommandTestUtil.NAME_DESC_DRAKE;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_DOGE;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_DRAKE;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.parser.commandparser.templatecommandparser.TemplateEditCommandParser;
import seedu.weme.model.template.Name;

public class TemplateEditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TemplateEditCommand.MESSAGE_USAGE);

    private TemplateEditCommandParser parser = new TemplateEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_DRAKE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", TemplateEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_DRAKE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_DRAKE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + NAME_DESC_DRAKE, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nameSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + NAME_DESC_DRAKE;

        Name expectedName = new Name(VALID_NAME_DRAKE);
        TemplateEditCommand expectedCommand = new TemplateEditCommand(targetIndex, expectedName);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleNamesSpecified_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_DRAKE + NAME_DESC_DOGE;

        Name expectedName = new Name(VALID_NAME_DOGE);
        TemplateEditCommand expectedCommand = new TemplateEditCommand(targetIndex, expectedName);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
