package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.CommandTestUtil.INSTAL_DESC_NETFLIX;
import static seedu.jarvis.logic.commands.CommandTestUtil.INSTAL_MONEY_NETFLIX;
import static seedu.jarvis.logic.commands.CommandTestUtil.INVALID_INSTAL_MONEY;
import static seedu.jarvis.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.logic.parser.ParserUtil.MONEY_MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.SetInstallmentCommand;
import seedu.jarvis.model.financetracker.Installment;
import seedu.jarvis.testutil.InstallmentBuilder;

public class SetInstallmentCommandParserTest {

    private SetInstallmentCommandParser parser = new SetInstallmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Installment expectedInstallment = new InstallmentBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INSTAL_DESC_NETFLIX + INSTAL_MONEY_NETFLIX,
                new SetInstallmentCommand(expectedInstallment));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, INSTAL_DESC_NETFLIX + INSTAL_DESC_NETFLIX + INSTAL_MONEY_NETFLIX,
                new SetInstallmentCommand(expectedInstallment));

        // multiple money amounts - last amount accepted
        assertParseSuccess(parser, INSTAL_DESC_NETFLIX + INSTAL_MONEY_NETFLIX + INSTAL_MONEY_NETFLIX,
                new SetInstallmentCommand(expectedInstallment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetInstallmentCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, INSTAL_MONEY_NETFLIX, expectedMessage);

        // missing money amount
        assertParseFailure(parser, INSTAL_DESC_NETFLIX, expectedMessage);
    }

    @Test
    public void parse_invalidMoneyAmount_failure() {
        assertParseFailure(parser, INSTAL_DESC_NETFLIX + INVALID_INSTAL_MONEY, MONEY_MESSAGE_CONSTRAINTS);
    }

}
