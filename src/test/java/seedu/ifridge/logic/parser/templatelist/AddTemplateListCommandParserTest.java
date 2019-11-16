package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_NAME_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_NAME_DESC_APPLE_PIE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_TEMPLATE_NAME_APPLE_PIE;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.AddTemplateListCommand;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.testutil.UniqueTemplateItemsBuilder;

public class AddTemplateListCommandParserTest {
    private AddTemplateListCommandParser parser = new AddTemplateListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        UniqueTemplateItems template = new UniqueTemplateItemsBuilder(new Name(VALID_TEMPLATE_NAME_APPLE_PIE)).build();

        // single name
        assertParseSuccess(parser, TEMPLATE_NAME_DESC_APPLE_PIE, new AddTemplateListCommand(template));

        // multiple names - last name accepted
        assertParseSuccess(parser, TEMPLATE_NAME_DESC + " " + TEMPLATE_NAME_DESC_APPLE_PIE,
                new AddTemplateListCommand(template));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateListCommand.MESSAGE_USAGE);

        // name missing
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, VALID_TEMPLATE_NAME_APPLE_PIE , expectedMessage);
    }
}
