package seedu.address.transaction.logic;

import static seedu.address.person.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_CATEGORY;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_DATE;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_DESC;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_NAME_ALICE;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_NAME_AMY;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_NAME_BENSEN;
import static seedu.address.transaction.commands.CommandTestUtil.INVALID_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.INVALID_DATE_1;
import static seedu.address.transaction.commands.CommandTestUtil.INVALID_DATE_2;
import static seedu.address.transaction.commands.CommandTestUtil.INVALID_DATE_3;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_CATEGORY;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_DESC;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_NAME_BENSEN;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertCommandParseWithPersonModelFailure;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertCommandParseWithPersonModelSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_INVALID_EDIT_COMMAND_FORMAT;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_PERSON;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_WRONG_DATE_FORMAT;

import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.commands.EditCommand;

import org.junit.jupiter.api.Test;

class EditCommandParserTest {
    private EditCommandParser parser = new EditCommandParser();
    Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertCommandParseWithPersonModelFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_EDIT_COMMAND_FORMAT,
                personModel);

        // no field specified
        assertCommandParseWithPersonModelFailure(parser, "1", MESSAGE_INVALID_EDIT_COMMAND_FORMAT,
                personModel);

        // no index and no field specified
        assertCommandParseWithPersonModelFailure(parser, "", MESSAGE_INVALID_EDIT_COMMAND_FORMAT, personModel);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertCommandParseWithPersonModelFailure(parser, "-5" + NAME_DESC_AMY,
                MESSAGE_INVALID_EDIT_COMMAND_FORMAT, personModel);

        // zero index
        assertCommandParseWithPersonModelFailure(parser, "0" + NAME_DESC_AMY,
                MESSAGE_INVALID_EDIT_COMMAND_FORMAT, personModel);

        // invalid arguments being parsed as preamble
        assertCommandParseWithPersonModelFailure(parser, "1 some random string",
                MESSAGE_INVALID_EDIT_COMMAND_FORMAT, personModel);

        // invalid prefix being parsed as preamble
        assertCommandParseWithPersonModelFailure(parser, "1 i/ string",
                MESSAGE_INVALID_EDIT_COMMAND_FORMAT, personModel);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertCommandParseWithPersonModelFailure(parser, "1" + DESC_NAME_AMY,
                MESSAGE_NO_SUCH_PERSON, personModel); // invalid name
        assertCommandParseWithPersonModelFailure(parser, "1" + INVALID_DATE_1, MESSAGE_WRONG_DATE_FORMAT,
                personModel); // invalid date
        assertCommandParseWithPersonModelFailure(parser, "1" + INVALID_DATE_2, MESSAGE_WRONG_DATE_FORMAT,
                personModel); // invalid date
        assertCommandParseWithPersonModelFailure(parser, "1" + INVALID_DATE_3,MESSAGE_WRONG_DATE_FORMAT,
                personModel); // invalid date
        assertCommandParseWithPersonModelFailure(parser, "1" + INVALID_AMOUNT, MESSAGE_WRONG_AMOUNT_FORMAT,
                personModel); // invalid tag

        // invalid amount followed by invalid date
        assertCommandParseWithPersonModelFailure(parser, "1" + INVALID_AMOUNT + INVALID_DATE_1,
                MESSAGE_WRONG_DATE_FORMAT, personModel);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        int targetIndex = 1;
        String userInput = targetIndex + DESC_NAME_BENSEN
                + DESC_DATE + DESC_AMOUNT + DESC_CATEGORY + DESC_DESC;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withName(TypicalPersons.BENSON.getName().toString())
                .withAmount(Double.parseDouble(VALID_AMOUNT))
                .withDate(VALID_DATE)
                .withCategory(VALID_CATEGORY)
                .withDescription(VALID_DESC).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertCommandParseWithPersonModelSuccess(parser, userInput, expectedCommand, personModel);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        int targetIndex = 2;
        String userInput = targetIndex + DESC_NAME_ALICE + DESC_AMOUNT;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withName(TypicalPersons.ALICE.getName().toString())
                .withAmount(Double.parseDouble(VALID_AMOUNT)).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertCommandParseWithPersonModelSuccess(parser, userInput, expectedCommand, personModel);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        int targetIndex = 3;
        String userInput = targetIndex + DESC_DESC;
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESC).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertCommandParseWithPersonModelSuccess(parser, userInput, expectedCommand, personModel);

        // date
        String userInput1 = targetIndex + DESC_DATE;
        EditCommand.EditTransactionDescriptor descriptor1 = new EditTransactionDescriptorBuilder()
                .withDate(VALID_DATE).build();
        EditCommand expectedCommand1 = new EditCommand(targetIndex, descriptor1);
        assertCommandParseWithPersonModelSuccess(parser, userInput1, expectedCommand1, personModel);

        // category
        String userInput3 = targetIndex + DESC_CATEGORY;
        EditCommand.EditTransactionDescriptor descriptor3 = new EditTransactionDescriptorBuilder()
                .withCategory(VALID_CATEGORY).build();
        EditCommand expectedCommand3 = new EditCommand(targetIndex, descriptor3);
        assertCommandParseWithPersonModelSuccess(parser, userInput3, expectedCommand3, personModel);

        // person
        String userInput4 = targetIndex+ DESC_NAME_BENSEN;
        EditCommand.EditTransactionDescriptor descriptor4 = new EditTransactionDescriptorBuilder()
                .withName(VALID_NAME_BENSEN).build();
        EditCommand expectedCommand4 = new EditCommand(targetIndex, descriptor4);
        assertCommandParseWithPersonModelSuccess(parser, userInput4, expectedCommand4, personModel);

        // amount
        String userInput5 = targetIndex + DESC_AMOUNT;
        EditCommand.EditTransactionDescriptor descriptor5 = new EditTransactionDescriptorBuilder()
                .withAmount(Double.parseDouble(VALID_AMOUNT)).build();
        EditCommand expectedCommand5 = new EditCommand(targetIndex, descriptor5);
        assertCommandParseWithPersonModelSuccess(parser, userInput5, expectedCommand5, personModel);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        int targetIndex = 2;
        String userInput = targetIndex + DESC_NAME_ALICE + DESC_NAME_BENSEN;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withName(VALID_NAME_BENSEN)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertCommandParseWithPersonModelSuccess(parser, userInput, expectedCommand, personModel);
    }
}
