package budgetbuddy.logic.parser.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.CommandParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.accountcommands.AccountEditCommand;
import budgetbuddy.logic.commands.accountcommands.AccountEditCommand.AccountEditDescriptor;
import budgetbuddy.logic.parser.commandparsers.accountcommandparsers.AccountEditCommandParser;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.accountutil.AccountEditDescriptorBuilder;

public class AccountEditCommandParserTest {

    private AccountEditCommandParser parser = new AccountEditCommandParser();

    @Test
    public void parse_missingCompulsoryFields_failure() {
        // no field specified
        assertParseFailure(parser, "1", AccountEditCommand.MESSAGE_UNEDITED);

        // no index and no field specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-1 n/food", MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0 n/food", MESSAGE_INVALID_INDEX);

        // extra arguments in preamble
        assertParseFailure(parser, "1 test n/food",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));

        // invalid prefix in preamble
        assertParseFailure(parser, "1 z/string ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1 n/&&&", Name.MESSAGE_CONSTRAINTS);

        // invalid description that exceeds the maximum length
        assertParseFailure(parser, "1 d/The chatting robot proposed is a multi-faceted approach, "
                + "consisting of data collection, dialogue system and psychology feedback as the main features. "
                + "Users will be monitored through cameras and audio recorders installed. "
                + "A variety of data is automatically collected and analyzed to assess the user’s emotional state "
                + "and look for signs of depressions. "
                + "Data collected includes speech and voice, facial expression and emotion and sentiment features. "
                + "Compared to chatting bots currently in the market, "
                + "the comprehensiveness of data extracted using chatting robot ensures "
                + "the consistency of the users’ conditions, at the same time avoids any subtle signs of "
                + "depressions or abnormal behavior.  "
                + "The data collected is then used as inputs for algorithms calculating various measurement standards "
                + "related to mental health based on cognitive behavioral therapy (CBT), which is "
                + "a widely recognized type of psychotherapeutic treatment that aims to comprehend the thoughts "
                + "and emotions that affect behaviors. Based on the diagnosis, the chatting robot give responses "
                + "that provide both emotional support and life coaching techniques to ease the pain "
                + "or anxiety the users feel. "
                + "Furthermore, the chatting robot could be customized in terms of the appearance, "
                + "voice and speed of the speech to align with the preference of the users for ease of "
                + "acceptance and connection building. )", Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = "1 n/SEP d/For Student Exchange Programme";

        AccountEditDescriptor descriptor = new AccountEditDescriptorBuilder()
                                                     .withName("SEP")
                                                     .withDescription("For Student Exchange Programme").build();
        AccountEditCommand expectedCommand = new AccountEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFiledSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = "1 n/food";
        AccountEditDescriptor descriptor = new AccountEditDescriptorBuilder()
                .withName("food").build();
        AccountEditCommand expectedCommand = new AccountEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = "1 n/food n/book";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));
    }
}

