package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.commands.CommandTestUtil.AMOUNT_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.AMOUNT_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.NAME_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.NAME_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.DESCRIPTION_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.billboard.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.billboard.testutil.TypicalPersons.DINNER;
import static seedu.billboard.testutil.TypicalPersons.TAXES;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.AddCommand;
import seedu.billboard.model.person.*;
import seedu.billboard.model.person.Expense;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.testutil.ExpenseBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new ExpenseBuilder(TAXES).withTags(VALID_TAG_TAXES).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES
                + AMOUNT_DESC_TAXES + TAG_DESC_TAXES, new AddCommand(expectedExpense));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_DINNER + NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES 
                + AMOUNT_DESC_TAXES + TAG_DESC_TAXES, new AddCommand(expectedExpense));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_DINNER + DESCRIPTION_DESC_TAXES
                + AMOUNT_DESC_TAXES + TAG_DESC_TAXES, new AddCommand(expectedExpense));

        // multiple amounts - last amounts accepted
        assertParseSuccess(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_DINNER
                + AMOUNT_DESC_TAXES + TAG_DESC_TAXES, new AddCommand(expectedExpense));

        // multiple tags - all accepted
        Expense expectedExpenseMultipleTags = new ExpenseBuilder(TAXES).withTags(VALID_TAG_TAXES, VALID_TAG_DINNER)
                .build();
        assertParseSuccess(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_TAXES
                + TAG_DESC_TAXES + TAG_DESC_DINNER, new AddCommand(expectedExpenseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Expense expectedExpense = new ExpenseBuilder(DINNER).withTags().build();
        assertParseSuccess(parser, NAME_DESC_DINNER + DESCRIPTION_DESC_DINNER + AMOUNT_DESC_DINNER,
                new AddCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_TAXES + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_TAXES,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_TAXES + VALID_DESCRIPTION_TAXES + AMOUNT_DESC_TAXES,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + VALID_AMOUNT_TAXES,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_TAXES + VALID_DESCRIPTION_TAXES + VALID_AMOUNT_TAXES,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_TAXES
                + TAG_DESC_TAXES + TAG_DESC_DINNER, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_TAXES + INVALID_PHONE_DESC + AMOUNT_DESC_TAXES
                + TAG_DESC_TAXES + TAG_DESC_DINNER, Phone.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + INVALID_AMOUNT_DESC
                + TAG_DESC_TAXES + TAG_DESC_DINNER, Amount.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + INVALID_ADDRESS_DESC
                + TAG_DESC_TAXES + TAG_DESC_DINNER, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + AMOUNT_DESC_TAXES
                + INVALID_TAG_DESC + VALID_TAG_TAXES, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DESCRIPTION_DESC_TAXES + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_TAXES + DESCRIPTION_DESC_TAXES + EMAIL_DESC_BOB
                + AMOUNT_DESC_TAXES + TAG_DESC_TAXES + TAG_DESC_DINNER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
