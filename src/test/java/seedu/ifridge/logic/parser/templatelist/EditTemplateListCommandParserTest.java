package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_INDEX_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_NAME_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_NAME_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_NAME_DESC_APPLE_PIE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_TEMPLATE_NAME_APPLE_PIE;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.templatelist.EditTemplateListCommand;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.testutil.EditTemplateListDescriptorBuilder;

public class EditTemplateListCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTemplateListCommand.MESSAGE_USAGE);

    private EditTemplateListCommandParser parser = new EditTemplateListCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no template index specified
        assertParseFailure(parser, TEMPLATE_NAME_DESC_APPLE_PIE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTemplateListCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TEMPLATE_NAME_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TEMPLATE_NAME_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 t/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name

        // multiple invalid values, but only the first invalid value is captured.
        // But bcuz of conflicts, we do only one INVALID ONE IN THIS CASE.
        assertParseFailure(parser, INVALID_INDEX_DESC + INVALID_NAME_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetTemplateIndex = INDEX_FIRST;
        String userInput = targetTemplateIndex.getOneBased() + TEMPLATE_NAME_DESC_APPLE_PIE;

        EditTemplateListCommand.EditTemplateListDescriptor descriptor = new EditTemplateListDescriptorBuilder()
                .withName(VALID_TEMPLATE_NAME_APPLE_PIE).build();
        EditTemplateListCommand expectedCommand = new EditTemplateListCommand(targetTemplateIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
