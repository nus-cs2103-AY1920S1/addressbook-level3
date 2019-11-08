package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.CommandTestUtil.INVALID_INSTAL_MONEY;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.INSTAL_DESC_NETFLIX;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.INSTAL_DESC_SPOTIFY;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.INSTAL_MONEY_NETFLIX;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.INSTAL_MONEY_SPOTIFY;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.VALID_DESC_NETFLIX;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.VALID_DESC_SPOTIFY;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.VALID_MONEY_NETFLIX;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.VALID_MONEY_SPOTIFY;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_SECOND_INSTALLMENT;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.testutil.finance.EditInstallmentDescriptorBuilder;

public class EditInstallmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditInstallmentCommand.MESSAGE_USAGE);

    private EditInstallmentCommandParser parser = new EditInstallmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, INSTAL_DESC_NETFLIX, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditInstallmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + INSTAL_DESC_NETFLIX, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + INSTAL_DESC_NETFLIX, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_INSTALLMENT;
        String userInput = targetIndex.getOneBased() + INSTAL_DESC_NETFLIX + INSTAL_MONEY_NETFLIX;

        EditInstallmentCommand.EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder()
                        .withDescription(VALID_DESC_NETFLIX)
                        .withSubscriptionFee(VALID_MONEY_NETFLIX)
                        .build();

        EditInstallmentCommand expectedCommand = new EditInstallmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_INSTALLMENT;
        String userInput = targetIndex.getOneBased() + INSTAL_DESC_SPOTIFY + INSTAL_MONEY_SPOTIFY;

        EditInstallmentCommand.EditInstallmentDescriptor descriptor = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESC_SPOTIFY)
                .withSubscriptionFee(VALID_MONEY_SPOTIFY)
                .build();
        EditInstallmentCommand expectedCommand = new EditInstallmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_SECOND_INSTALLMENT;
        String userInput = targetIndex.getOneBased() + INSTAL_DESC_SPOTIFY;
        EditInstallmentCommand.EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder()
                        .withDescription(VALID_DESC_SPOTIFY)
                        .build();
        EditInstallmentCommand expectedCommand = new EditInstallmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // subscription fee
        userInput = targetIndex.getOneBased() + INSTAL_MONEY_SPOTIFY;
        descriptor = new EditInstallmentDescriptorBuilder()
                .withSubscriptionFee(VALID_MONEY_SPOTIFY)
                .build();
        expectedCommand = new EditInstallmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_INSTALLMENT;
        String userInput = targetIndex.getOneBased()
                + INSTAL_DESC_SPOTIFY + INSTAL_MONEY_SPOTIFY
                + INSTAL_DESC_SPOTIFY + INSTAL_MONEY_SPOTIFY;

        EditInstallmentCommand.EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder()
                        .withDescription(VALID_DESC_SPOTIFY)
                        .withSubscriptionFee(VALID_MONEY_SPOTIFY)
                        .build();
        EditInstallmentCommand expectedCommand = new EditInstallmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_INSTALLMENT;
        String userInput = targetIndex.getOneBased() + INVALID_INSTAL_MONEY + INSTAL_MONEY_SPOTIFY;
        EditInstallmentCommand.EditInstallmentDescriptor descriptor =
                new EditInstallmentDescriptorBuilder()
                        .withSubscriptionFee(VALID_MONEY_SPOTIFY)
                        .build();
        EditInstallmentCommand expectedCommand = new EditInstallmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
