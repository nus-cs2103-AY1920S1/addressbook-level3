package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ReceiveCommand;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.util.Date;
import seedu.address.testutil.LedgerOperationBuilder;

class ReceiveCommandParserTest {
    private ReceiveCommandParser parser = new ReceiveCommandParser();
    private ReceiveMoney expected;

    @BeforeEach
    void init() {
        LedgerOperationBuilder lob = new LedgerOperationBuilder();
        expected = lob.withPerson("amy")
                .withAmount("23")
                .withDate("20022019")
                .withDescription("return money")
                .asReceiveMoney();
    }

    @Test
    void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " $/23 d/20022019 n/amy a/return money", new ReceiveCommand(expected));
    }

    @Test
    void parse_optionalFieldsMissing_success() {
        expected = new LedgerOperationBuilder()
                .withDescription("receive")
                .withPerson("amy")
                .withAmount("23")
                .withDate(Date.now().toString())
                .asReceiveMoney();
        assertParseSuccess(parser, " $/23 n/amy ", new ReceiveCommand(expected));
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReceiveCommand.MESSAGE_USAGE);
        // no amount
        assertParseFailure(parser, " n/amy ", expectedMessage);
        // no name
        assertParseFailure(parser, " $/23 ", expectedMessage);
    }

    @Test
    void parse_invalidValues_failure() {
        // invalid amount
        assertParseFailure(parser, " $/2003.23049 n/amy ", Amount.DOUBLE_CONSTRAINTS);

        // overflow amount
        assertParseFailure(parser, " $/383838388382003 n/amy ", Messages.MESSAGE_AMOUNT_OVERFLOW);

        // invalid name
        assertParseFailure(parser, " $/2003 n/amy#@ ", Name.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, " $/2003 n/amy a/amy#@ ", Description.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, " $/2003 n/amy d/amy#@ ", Date.MESSAGE_FORMAT_CONSTRAINTS);

        // invalid date format
        assertParseFailure(parser, " $/2003 n/amy d/20202019 ",
                String.format(Date.MESSAGE_DATE_INVALID, "20202019"));
    }
}
