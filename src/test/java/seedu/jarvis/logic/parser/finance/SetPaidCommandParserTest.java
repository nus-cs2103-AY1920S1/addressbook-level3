package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.CommandTestUtil.INVALID_PURCHASE_MONEY;
import static seedu.jarvis.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jarvis.logic.commands.CommandTestUtil.PURCHASE_DESC_LUNCH;
import static seedu.jarvis.logic.commands.CommandTestUtil.PURCHASE_MONEY_LUNCH;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.logic.parser.finance.FinanceParserUtil.MONEY_MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.SetPaidCommand;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.testutil.finance.PurchaseBuilder;

public class SetPaidCommandParserTest {
    private SetPaidCommandParser parser = new SetPaidCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Purchase expectedPurchase = new PurchaseBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PURCHASE_DESC_LUNCH + PURCHASE_MONEY_LUNCH,
                new SetPaidCommand(expectedPurchase));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, PURCHASE_DESC_LUNCH + PURCHASE_DESC_LUNCH
                        + PURCHASE_MONEY_LUNCH, new SetPaidCommand(expectedPurchase));

        // multiple money amounts - last amount accepted
        assertParseSuccess(parser, PURCHASE_DESC_LUNCH + PURCHASE_MONEY_LUNCH + PURCHASE_MONEY_LUNCH,
                new SetPaidCommand(expectedPurchase));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPaidCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, PURCHASE_MONEY_LUNCH, expectedMessage);

        // missing money amount
        assertParseFailure(parser, PURCHASE_DESC_LUNCH, expectedMessage);
    }

    @Test
    public void parse_invalidMoneyAmount_failure() {
        assertParseFailure(parser, PURCHASE_DESC_LUNCH + INVALID_PURCHASE_MONEY,
                MONEY_MESSAGE_CONSTRAINTS);
    }
}
