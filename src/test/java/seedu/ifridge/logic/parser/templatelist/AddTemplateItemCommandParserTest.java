package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.template.AddTemplateItemCommand;

public class AddTemplateItemCommandParserTest {
    private AddTemplateListCommandParser parser = new AddTemplateListCommandParser();

    /**
    @Test
    public void parse_allFieldsPresent_success() {
        TemplateItem expectedTemplateItem = new TemplateItemBuilder(BOB).withAmount(VALID_AMOUNT_BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + AMOUNT_DESC_BOB, new AddTemplateItemCommand(expectedTemplateItem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                + AMOUNT_DESC_BOB, new AddTemplateItemCommand(expectedTemplateItem));
    }**/

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateItemCommand.MESSAGE_USAGE);
        /**
        // name missing
        assertParseFailure(parser, NAME_DESC_BOB, expectedMessage);

        // amount missing
        assertParseFailure(parser, AMOUNT_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + AMOUNT_DESC_BOB,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_BOB + " " + VALID_AMOUNT_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + " " + VALID_AMOUNT_BOB,
                expectedMessage);**/
    }

    @Test
    public void parse_invalidValue_failure() {
        /**
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC
                + AMOUNT_DESC_BOB , Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_BOB
                + INVALID_AMOUNT_DESC , Amount.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_AMOUNT_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                + AMOUNT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateItemCommand.MESSAGE_USAGE));**/
    }
}
