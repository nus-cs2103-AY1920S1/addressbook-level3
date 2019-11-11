package budgetbuddy.logic.parser.accountCommandParsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import budgetbuddy.logic.commands.accountcommands.AccountAddCommand;
import budgetbuddy.logic.parser.commandparsers.accountcommandparsers.AccountAddCommandParser;
import budgetbuddy.model.account.Account;
import budgetbuddy.testutil.accountutil.AccountBuilder;
import budgetbuddy.testutil.accountutil.TypicalAccounts;
import org.junit.jupiter.api.Test;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;


public class AccountAddCommandParserTest {

    private AccountAddCommandParser parser = new AccountAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Account expectedAccount = TypicalAccounts.FOOD;
        String input = "account add n/food d/For food.";
        assertParseSuccess(parser, input, new AccountAddCommand(expectedAccount));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Account expectedAccount = new AccountBuilder(TypicalAccounts.FOOD).withDescription("").build();
        String input = "account add n/food";
        assertParseSuccess(parser, input, new AccountAddCommand(expectedAccount));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // no name
        assertParseFailure(parser, "account add d/For food",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountAddCommand.MESSAGE_USAGE);

        // extra name
        assertParseFailure(parser, "account add n/food n/food", expectedMessage);

        // extra description
        assertParseFailure(parser, "account add n/food d/For food. d/For food.", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "account add n/&&&", Name.MESSAGE_CONSTRAINTS);

        // invalid description that exceeds the maximum length
        assertParseFailure(parser, "account add n/food d/The chatting robot proposed is a multi-faceted approach, " +
                "consisting of data collection, dialogue system and psychology feedback as the main features. " +
                "Users will be monitored through cameras and audio recorders installed. " +
                "A variety of data is automatically collected and analyzed to assess the user’s emotional state " +
                "and look for signs of depressions. " +
                "Data collected includes speech and voice, facial expression and emotion and sentiment features. " +
                "Compared to chatting bots currently in the market, " +
                "the comprehensiveness of data extracted using chatting robot ensures " +
                "the consistency of the users’ conditions, at the same time avoids any subtle signs of depressions or abnormal behavior.  " +
                "The data collected is then used as inputs for algorithms calculating various measurement standards " +
                "related to mental health based on cognitive behavioral therapy (CBT), which is " +
                "a widely recognized type of psychotherapeutic treatment that aims to comprehend the thoughts " +
                "and emotions that affect behaviors. Based on the diagnosis, the chatting robot give responses " +
                "that provide both emotional support and life coaching techniques to ease the pain or anxiety the users feel. " +
                "Furthermore, the chatting robot could be customized in terms of the appearance, " +
                "voice and speed of the speech to align with the preference of the users for ease of " +
                "acceptance and connection building. )", Description.MESSAGE_CONSTRAINTS);
    }
}
