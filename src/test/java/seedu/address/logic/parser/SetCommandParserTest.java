package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FINE_INCREMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOAN_PERIOD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENEW_PERIOD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FINE_INCREMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FINE_INCREMENT_1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FINE_INCREMENT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FINE_INCREMENT_2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_PERIOD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_PERIOD_1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_PERIOD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_PERIOD_2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEW_PERIOD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEW_PERIOD_1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEW_PERIOD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEW_PERIOD_2_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.commands.SetCommand.SetUserSettingsDescriptor;
import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.RenewPeriod;
import seedu.address.testutil.SetUserSettingsDescriptorBuilder;

public class SetCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);

    private SetCommandParser parser = new SetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", SetCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid loan period
        assertParseFailure(parser, INVALID_LOAN_PERIOD_DESC, LoanPeriod.MESSAGE_CONSTRAINTS);
        // invalid renew period
        assertParseFailure(parser, INVALID_RENEW_PERIOD_DESC, RenewPeriod.MESSAGE_CONSTRAINTS);
        // invalid fine increment
        assertParseFailure(parser, INVALID_FINE_INCREMENT_DESC, FineIncrement.MESSAGE_CONSTRAINTS);

        // invalid loan period followed by valid renew period
        assertParseFailure(parser, INVALID_LOAN_PERIOD_DESC + VALID_RENEW_PERIOD_1_DESC,
                LoanPeriod.MESSAGE_CONSTRAINTS);

        // invalid renew period followed by valid loan period
        assertParseFailure(parser, INVALID_RENEW_PERIOD_DESC + VALID_LOAN_PERIOD_1_DESC,
                RenewPeriod.MESSAGE_CONSTRAINTS);

        // valid loan period followed by invalid renew period
        assertParseFailure(parser, VALID_LOAN_PERIOD_1_DESC + INVALID_RENEW_PERIOD_DESC,
                RenewPeriod.MESSAGE_CONSTRAINTS);

        // Invalid fine increment followed by valid renew period
        assertParseFailure(parser, INVALID_FINE_INCREMENT_DESC + VALID_RENEW_PERIOD_1_DESC,
                FineIncrement.MESSAGE_CONSTRAINTS);

        // invalid loan period followed by valid fine increment
        assertParseFailure(parser, INVALID_LOAN_PERIOD_DESC + VALID_FINE_INCREMENT_1_DESC,
                LoanPeriod.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value that is checked by parser is captured
        assertParseFailure(parser, INVALID_FINE_INCREMENT_DESC
                        + INVALID_LOAN_PERIOD_DESC + INVALID_RENEW_PERIOD_DESC, LoanPeriod.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_LOAN_PERIOD_1_DESC + VALID_RENEW_PERIOD_1_DESC + VALID_FINE_INCREMENT_1_DESC;

        SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .withRenewPeriod(VALID_RENEW_PERIOD_1)
                .withFineIncrement(VALID_FINE_INCREMENT_1)
                .build();
        SetCommand expectedCommand = new SetCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = VALID_LOAN_PERIOD_1_DESC + VALID_FINE_INCREMENT_1_DESC;

        SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .withFineIncrement(VALID_FINE_INCREMENT_1)
                .build();

        SetCommand expectedCommand = new SetCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // loan period
        String userInput = VALID_LOAN_PERIOD_1_DESC;
        SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .build();

        SetCommand expectedCommand = new SetCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // renew period
        userInput = VALID_RENEW_PERIOD_1_DESC;
        descriptor = new SetUserSettingsDescriptorBuilder()
                .withRenewPeriod(VALID_RENEW_PERIOD_1)
                .build();

        expectedCommand = new SetCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // fine increment
        userInput = VALID_FINE_INCREMENT_1_DESC;
        descriptor = new SetUserSettingsDescriptorBuilder()
                .withFineIncrement(VALID_FINE_INCREMENT_1)
                .build();

        expectedCommand = new SetCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = VALID_LOAN_PERIOD_1_DESC + VALID_RENEW_PERIOD_1_DESC
                + VALID_FINE_INCREMENT_1_DESC + VALID_LOAN_PERIOD_2_DESC + VALID_RENEW_PERIOD_2_DESC
                + VALID_FINE_INCREMENT_2_DESC;

        SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_2)
                .withRenewPeriod(VALID_RENEW_PERIOD_2)
                .withFineIncrement(VALID_FINE_INCREMENT_2)
                .build();

        SetCommand expectedCommand = new SetCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_LOAN_PERIOD_DESC + VALID_LOAN_PERIOD_1_DESC;
        SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .build();

        SetCommand expectedCommand = new SetCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_LOAN_PERIOD_1_DESC + INVALID_RENEW_PERIOD_DESC + VALID_RENEW_PERIOD_1_DESC;

        descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .withRenewPeriod(VALID_RENEW_PERIOD_1)
                .build();

        expectedCommand = new SetCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
