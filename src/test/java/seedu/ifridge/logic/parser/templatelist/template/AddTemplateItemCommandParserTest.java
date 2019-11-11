package seedu.ifridge.logic.parser.templatelist.template;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.AMOUNT_DESC_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.AMOUNT_DESC_PORK;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_NAME_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_UNIT_DESC;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.NAME_DESC_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.NAME_DESC_PORK;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_AMOUNT_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_NAME_CHEESE;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.model.food.Amount.MESSAGE_INVALID_AMOUNT;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.template.AddTemplateItemCommand;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.testutil.TemplateItemBuilder;

public class AddTemplateItemCommandParserTest {
    private AddTemplateItemCommandParser parser = new AddTemplateItemCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        TemplateItem expectedTemplateItem = new TemplateItemBuilder().build();

        // multiple names - last name accepted
        assertParseSuccess(parser, "1" + NAME_DESC_CHEESE + NAME_DESC_PORK + AMOUNT_DESC_PORK,
                new AddTemplateItemCommand(INDEX_FIRST, expectedTemplateItem));

        // multiple amount - last amount accepted
        assertParseSuccess(parser, "1" + NAME_DESC_PORK + AMOUNT_DESC_CHEESE + AMOUNT_DESC_PORK,
                new AddTemplateItemCommand(INDEX_FIRST, expectedTemplateItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateItemCommand.MESSAGE_USAGE);

        // whitespace only preamble
        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_CHEESE + AMOUNT_DESC_CHEESE,
                expectedMessage);

        // name missing
        assertParseFailure(parser, AMOUNT_DESC_CHEESE, expectedMessage);

        // amount missing
        assertParseFailure(parser, NAME_DESC_CHEESE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CHEESE + AMOUNT_DESC_CHEESE, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_CHEESE + " " + VALID_AMOUNT_CHEESE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_CHEESE + " " + VALID_AMOUNT_CHEESE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + AMOUNT_DESC_CHEESE , Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, "1" + NAME_DESC_CHEESE + INVALID_AMOUNT_DESC , MESSAGE_INVALID_AMOUNT);

        // invalid unit
        assertParseFailure(parser, "1" + NAME_DESC_CHEESE + INVALID_UNIT_DESC , MESSAGE_INVALID_AMOUNT);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_AMOUNT_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1" + NAME_DESC_CHEESE + AMOUNT_DESC_CHEESE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateItemCommand.MESSAGE_USAGE));
    }
}
