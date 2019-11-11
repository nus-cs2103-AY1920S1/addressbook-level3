package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FinSec;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditIncomeDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "91111111";
    public static final String VALID_PHONE_BOB = "92222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_DESCRIPTION_LOGISTICS = "Logistics";
    public static final String VALID_DESCRIPTION_SHIRTSALES = "Shirt sales";
    public static final String VALID_DESCRIPTION_FUNDRAISING = "Annual fundraising";
    public static final String VALID_DESCRIPTION_TEST1 = "Test1";
    public static final String VALID_DESCRIPTION_TEST2 = "Test2";
    public static final String VALID_DESCRIPTION_TEST3 = "Test3";
    public static final String VALID_DESCRIPTION_TEST4 = "Test4";
    public static final String VALID_DESCRIPTION_TEST5 = "Test5";
    public static final String VALID_AMOUNT_LOGISTICS = "152.82";
    public static final String VALID_AMOUNT_SHIRTSALES = "100.10";
    public static final String VALID_AMOUNT_FUNDRAISING = "10000";
    public static final String VALID_AMOUNT_SCHOOLCLAIMS = "45.10";
    public static final String VALID_AMOUNT_LOGISTICCLAIMS = "9999.99";
    public static final String VALID_AMOUNT_CORPORATECLAIMS = "321.10";
    public static final String VALID_AMOUNT_PROJECTCLAIMS = "500";
    public static final String VALID_DATE_LOGISTICS = "15-12-2019";
    public static final String VALID_DATE_SHIRTSALES = "11-11-2019";
    public static final String VALID_DATE_FUNDRAISING = "01-01-2019";
    public static final String VALID_DATE_BEFOREMONTH = "15-12-2000";
    public static final String VALID_DATE_DURINGMONTH = "11-11-2019";
    public static final String VALID_DATE_DURINGMONTH2 = "23-11-2019";
    public static final String VALID_DATE_FIRSTDAYOFMONTH = "01-11-2019";
    public static final String VALID_DATE_AFTERMONTH = "11-11-2099";
    public static final String VALID_AUTOCORRECTSUGGESTION_CLAIMJOSHUA = "add_claim n/joshua";
    public static final String VALID_AUTOCORRECTSUGGESTION_CLAIMBOB = "add_claim n/Bob";
    public static final String VALID_COMMANDWORD_ADD = "add";
    public static final String VALID_COMMANDWORD_DELETE = "delete";
    public static final String VALID_COMMANDTASK_ADDCONTACT = "add_contact";
    public static final String VALID_COMMANDTASK_DELETEINCOME = "delete_income";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String DESCRIPTION_DESC_LOGISTICS = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_LOGISTICS;
    public static final String DESCRIPTION_DESC_SHIRTSALES = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_SHIRTSALES;
    public static final String DESCRIPTION_DESC_FUNDRAISING = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_FUNDRAISING;
    public static final String AMOUNT_DESC_LOGISTICS = " " + PREFIX_CASH + VALID_AMOUNT_LOGISTICS;
    public static final String AMOUNT_DESC_SHIRTSALES = " " + PREFIX_CASH + VALID_AMOUNT_SHIRTSALES;
    public static final String AMOUNT_DESC_FUNDRAISING = " " + PREFIX_CASH + VALID_DESCRIPTION_FUNDRAISING;
    public static final String DATE_DESC_LOGISTICS = " " + PREFIX_DATE + VALID_DATE_LOGISTICS;
    public static final String DATE_DESC_SHIRTSALES = " " + PREFIX_DATE + VALID_DATE_SHIRTSALES;
    public static final String DATE_DESC_FUNDRAISING = " " + PREFIX_DATE + VALID_DATE_FUNDRAISING;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "Sal3szz!!"; //'3' not allowed
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_CASH + "100.333"; // > 2 decimal places not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "111-13-2019"; // invalid dates not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditContactCommand.EditPersonDescriptor DESC_AMY;
    public static final EditContactCommand.EditPersonDescriptor DESC_BOB;
    public static final EditIncomeCommand.EditIncomeDescriptor DESC_SHIRTSALES;
    public static final EditIncomeCommand.EditIncomeDescriptor DESC_FUNDRAISING;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_SHIRTSALES = new EditIncomeDescriptorBuilder().withDescription(VALID_DESCRIPTION_SHIRTSALES)
                .withAmount(VALID_AMOUNT_SHIRTSALES).withDate(VALID_DATE_SHIRTSALES).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).build();

        DESC_FUNDRAISING = new EditIncomeDescriptorBuilder().withDescription(VALID_DESCRIPTION_FUNDRAISING)
                .withAmount(VALID_AMOUNT_FUNDRAISING).withDate(VALID_DATE_FUNDRAISING).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();

    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | IOException | URISyntaxException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinSec expectedFinSec = new FinSec(actualModel.getFinSec());
        List<seedu.address.model.contact.Contact> expectedFilteredList = new ArrayList<>(
                actualModel.getFilteredContactList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFinSec, actualModel.getFinSec());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    //@@author{joshuaseetss}
    public static void assertCommandFailureNoExceptionThrown(Command command, Model actualModel,
                                                             String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinSec expectedFinSec = new FinSec(actualModel.getFinSec());
        List<seedu.address.model.contact.Contact> expectedFilteredList = new ArrayList<>(
                actualModel.getFilteredContactList());


        assertEquals(expectedFinSec, actualModel.getFinSec());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertViewContactFailure(Command command, Model actualModel, String expectedMessage) {
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }

}
