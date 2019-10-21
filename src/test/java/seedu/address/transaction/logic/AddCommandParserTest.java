package seedu.address.transaction.logic;

import static seedu.address.testutil.TransactionBuilder.DEFAULT_CATEGORY;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_CATEGORY;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_DATE;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_DESC;
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
import static seedu.address.transaction.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.transaction.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_CATEGORY;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_DESC;
import static seedu.address.transaction.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertAddCommandParseFailure;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertAddCommandParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.commands.AddCommand;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(TypicalPersons.ALICE).build();
        Transaction expectedTransaction2 = new TransactionBuilder(TypicalPersons.BENSON).build();


        // whitespace only preamble
        assertAddCommandParseSuccess(parser, PREAMBLE_WHITESPACE + DESC_NAME_ALICE + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction),
                personModel.getFilteredPersonList().size(), personModel);

        //no whitespace preamble
        assertAddCommandParseSuccess(parser, DESC_NAME_BENSEN + DESC_BUILDER_DATE + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT, new AddCommand(expectedTransaction2),
                personModel.getFilteredPersonList().size(), personModel);

        // multiple phones - last description accepted
        assertAddCommandParseSuccess(parser, DESC_NAME_ALICE + DESC_DESC + DESC_BUILDER_DESC
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction),
                personModel.getFilteredPersonList().size(), personModel);

        // multiple emails - last category accepted
        assertAddCommandParseSuccess(parser, DESC_NAME_ALICE + DESC_CATEGORY + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_DESC + DESC_BUILDER_DATE + DESC_BUILDER_AMOUNT,
                new AddCommand(expectedTransaction),
                personModel.getFilteredPersonList().size(), personModel);

        // multiple addresses - last amount accepted
        assertAddCommandParseSuccess(parser, DESC_NAME_ALICE + DESC_AMOUNT + DESC_BUILDER_AMOUNT
                        + DESC_BUILDER_DESC + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction),
                personModel.getFilteredPersonList().size(), personModel);

        // multiple addresses - last date accepted
        assertAddCommandParseSuccess(parser, DESC_NAME_ALICE + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_DATE + DESC_BUILDER_DATE , new AddCommand(expectedTransaction),
                personModel.getFilteredPersonList().size(), personModel);
    }

    @Test
    public void parse_personNotInAddressBook_failure() {
        //person not found in addressbook
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertAddCommandParseFailure(parser, DESC_NAME_AMY + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE, TransactionMessages.MESSAGE_NO_SUCH_PERSON,
                personModel.getFilteredPersonList().size(), personModel);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT;

        // missing name prefix
        assertAddCommandParseFailure(parser, VALID_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel.getFilteredPersonList().size(), personModel);

        // missing date prefix
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + VALID_DATE + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel.getFilteredPersonList().size(), personModel);

        // missing amount prefix
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_CATEGORY
                        + VALID_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel.getFilteredPersonList().size(), personModel);

        // missing description prefix
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + DEFAULT_CATEGORY
                        + DESC_BUILDER_AMOUNT + VALID_DESC,
                expectedMessage, personModel.getFilteredPersonList().size(), personModel);

        // missing category prefix
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + VALID_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel.getFilteredPersonList().size(), personModel);

        // all prefixes missing
        assertAddCommandParseFailure(parser, VALID_NAME_ALICE + VALID_DATE + VALID_CATEGORY
                        + VALID_AMOUNT + VALID_DESC,
                expectedMessage, personModel.getFilteredPersonList().size(), personModel);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid amount
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_CATEGORY
                + INVALID_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT,
                personModel.getFilteredPersonList().size(), personModel);

        // invalid date
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + INVALID_DATE_1 + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_DATE_FORMAT,
                personModel.getFilteredPersonList().size(), personModel);

        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + INVALID_DATE_2 + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_DATE_FORMAT,
                personModel.getFilteredPersonList().size(), personModel);

        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + INVALID_DATE_3 + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_DATE_FORMAT,
                personModel.getFilteredPersonList().size(), personModel);

        // two invalid values, only first invalid value reported
        assertAddCommandParseFailure(parser, DESC_NAME_ALICE + INVALID_DATE_1 + DESC_BUILDER_CATEGORY
                        + INVALID_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT,
                personModel.getFilteredPersonList().size(), personModel);

        // non-empty preamble
        assertAddCommandParseFailure(parser, PREAMBLE_NON_EMPTY + DESC_NAME_ALICE + DESC_BUILDER_DATE
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT,
                personModel.getFilteredPersonList().size(), personModel);
    }
}
