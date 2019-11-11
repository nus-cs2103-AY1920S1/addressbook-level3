package seedu.address.transaction.logic.parser;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TransactionBuilder.DEFAULT_CATEGORY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_AMOUNT;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_AMOUNT;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_CATEGORY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_DATE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_DESC;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_CATEGORY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_DATE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_DESC;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_NAME_ALICE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_NAME_AMY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_NAME_BENSEN;
import static seedu.address.transaction.logic.commands.CommandTestUtil.INVALID_AMOUNT;
import static seedu.address.transaction.logic.commands.CommandTestUtil.INVALID_DATE_1;
import static seedu.address.transaction.logic.commands.CommandTestUtil.INVALID_DATE_2;
import static seedu.address.transaction.logic.commands.CommandTestUtil.INVALID_DATE_3;
import static seedu.address.transaction.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_CATEGORY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_DESC;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.transaction.logic.parser.CommandParserTestUtil.assertCommandParseWithPersonModelFailure;
import static seedu.address.transaction.logic.parser.CommandParserTestUtil.assertCommandParseWithPersonModelSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.logic.commands.AddCommand;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();
    private CheckAndGetPersonByNameModel personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommandParser().parse("dummy" , null));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(TypicalPersons.ALICE).build();
        Transaction expectedTransaction2 = new TransactionBuilder(TypicalPersons.BENSON).build();


        // whitespace only preamble
        assertCommandParseWithPersonModelSuccess(parser, PREAMBLE_WHITESPACE + DESC_NAME_ALICE + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction), personModel);

        //no whitespace preamble
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_BENSEN + DESC_BUILDER_DATE + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT, new AddCommand(expectedTransaction2), personModel);

        // multiple description - last description accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_DESC + DESC_BUILDER_DESC
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction), personModel);

        // multiple category - last category accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_CATEGORY
                        + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_DESC + DESC_BUILDER_DATE + DESC_BUILDER_AMOUNT,
                new AddCommand(expectedTransaction), personModel);

        // multiple amount - last amount accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_AMOUNT + DESC_BUILDER_AMOUNT
                        + DESC_BUILDER_DESC + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction), personModel);

        // multiple date - last date accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_BUILDER_AMOUNT
                        + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_DATE + DESC_BUILDER_DATE , new AddCommand(expectedTransaction),
                personModel);
    }

    @Test
    public void parse_personNotInAddressBook_failure() {
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_AMY + DESC_BUILDER_AMOUNT
                + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE, TransactionMessages.MESSAGE_NO_SUCH_PERSON, personModel);
    }

    @Test
    public void parse_amountTooLarge_failure() {
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_AMY + " a/10000000000"
                + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE, TransactionMessages.MESSAGE_AMOUNT_TOO_LARGE, personModel);
    }

    @Test
    public void parse_amountTooSmall_failure() {
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_AMY + " a/-10000000000"
                + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE, TransactionMessages.MESSAGE_AMOUNT_TOO_SMALL, personModel);
    }

    @Test
    public void parse_amountZero_failure() {
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_AMY + " a/0"
                + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE, TransactionMessages.MESSAGE_NO_ZERO_ALLOWED, personModel);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT;

        // missing name prefix
        assertCommandParseWithPersonModelFailure(parser, VALID_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel);

        // missing date prefix
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + VALID_DATE + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel);

        // missing amount prefix
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_CATEGORY
                        + VALID_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel);

        // missing description prefix
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + DEFAULT_CATEGORY
                        + DESC_BUILDER_AMOUNT + VALID_DESC,
                expectedMessage, personModel);

        // missing category prefix
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE + VALID_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                expectedMessage, personModel);

        // all prefixes missing
        assertCommandParseWithPersonModelFailure(parser, VALID_NAME_ALICE + VALID_DATE + VALID_CATEGORY
                        + VALID_AMOUNT + VALID_DESC,
                expectedMessage, personModel);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid amount
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + DESC_BUILDER_DATE
                + DESC_BUILDER_CATEGORY
                + INVALID_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT, personModel);

        // invalid date
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + INVALID_DATE_1
                        + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_DATE_FORMAT,
                personModel);

        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + INVALID_DATE_2
                        + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_DATE_FORMAT,
                personModel);

        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + INVALID_DATE_3
                        + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_DATE_FORMAT,
                personModel);

        // two invalid values, only first invalid value reported
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_ALICE + INVALID_DATE_1
                        + DESC_BUILDER_CATEGORY
                        + INVALID_AMOUNT + DESC_BUILDER_DESC, TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT,
                personModel);

        // non-empty preamble
        assertCommandParseWithPersonModelFailure(parser, PREAMBLE_NON_EMPTY + DESC_NAME_ALICE
                        + DESC_BUILDER_DATE
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC,
                TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT, personModel);
    }
}
