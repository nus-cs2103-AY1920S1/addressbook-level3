package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_WORKER_FLAG = " -w"; // whitespace in front is necessary for parser
    public static final String VALID_NAME_ZACH = "Zach Tan";
    public static final String VALID_NAME_XENIA = "Xenia Lee";
    public static final String VALID_SEX_ZACH = "MALE";
    public static final String VALID_SEX_XENIA = "FEMALE";
    public static final String VALID_PHONE_NUMBER_ZACH = "91234567";
    public static final String VALID_PHONE_NUMBER_XENIA = "98765432";
    public static final String VALID_DATE_OF_BIRTH_ZACH = "1/2/1997";
    public static final String VALID_DATE_OF_BIRTH_XENIA = "3/4/1997";
    public static final String VALID_DATE_JOINED_ZACH = "1/2/2019";
    public static final String VALID_DATE_JOINED_XENIA = "3/4/2019";
    public static final String VALID_DESIGNATION_ZACH = "coroner";
    public static final String VALID_DESIGNATION_XENIA = "cleaner";
    public static final String VALID_EMPLOYMENT_STATUS_ZACH = "autopsy investigation";
    public static final String VALID_EMPLOYMENT_STATUS_XENIA = "cleaning";

    public static final String VALID_BODY_FLAG = " -b"; // whitespace in front is necessary for parser
    public static final String VALID_NAME_JOHN = "John Doe";
    public static final String VALID_NAME_JANE = "Jane Doe";
    public static final String VALID_SEX_JOHN = "MALE";
    public static final String VALID_SEX_JANE = "FEMALE";
    public static final String VALID_DATE_OF_ADMISSION_JOHN = "5/6/2019";
    public static final String VALID_DATE_OF_ADMISSION_JANE = "7/8/2019";
    public static final String VALID_DATE_OF_DEATH_JOHN = "9/10/2019";
    public static final String VALID_DATE_OF_DEATH_JANE = "11/12/2019";

    public static final String NAME_DESC_ZACH = " " + PREFIX_NAME + " " + VALID_NAME_ZACH;
    public static final String NAME_DESC_XENIA = " " + PREFIX_NAME + " " + VALID_NAME_XENIA;
    public static final String SEX_DESC_ZACH = " " + PREFIX_SEX + " " + VALID_SEX_ZACH;
    public static final String SEX_DESC_XENIA = " " + PREFIX_SEX + " " + VALID_SEX_XENIA;
    public static final String PHONE_NUMBER_DESC_ZACH = " " + PREFIX_PHONE_NUMBER + " " + VALID_PHONE_NUMBER_ZACH;
    public static final String PHONE_NUMBER_DESC_XENIA = " " + PREFIX_PHONE_NUMBER + " " + VALID_PHONE_NUMBER_XENIA;
    public static final String DATE_OF_BIRTH_DESC_ZACH = " " + PREFIX_DATE_OF_BIRTH + " " + VALID_DATE_OF_BIRTH_ZACH;
    public static final String DATE_OF_BIRTH_DESC_XENIA = " " + PREFIX_DATE_OF_BIRTH + " " + VALID_DATE_OF_BIRTH_XENIA;
    public static final String DATE_JOINED_DESC_ZACH = " " + PREFIX_DATE_JOINED + " " + VALID_DATE_JOINED_ZACH;
    public static final String DATE_JOINED_DESC_XENIA = " " + PREFIX_DATE_JOINED + " " + VALID_DATE_JOINED_XENIA;
    public static final String DESIGNATION_DESC_ZACH = " " + PREFIX_DESIGNATION + " " + VALID_DESIGNATION_ZACH;
    public static final String DESIGNATION_DESC_XENIA = " " + PREFIX_DESIGNATION + " " + VALID_DESIGNATION_XENIA;
    public static final String EMPLOYMENT_STATUS_DESC_ZACH = " " + PREFIX_EMPLOYMENT_STATUS + " "
            + VALID_EMPLOYMENT_STATUS_ZACH;
    public static final String EMPLOYMENT_STATUS_DESC_XENIA = " " + PREFIX_EMPLOYMENT_STATUS + " "
            + VALID_EMPLOYMENT_STATUS_XENIA;
    public static final String DATE_OF_ADMISSION_DESC_ZACH = " " + PREFIX_DATE_OF_ADMISSION + " "
            + VALID_DATE_OF_ADMISSION_JOHN;
    public static final String DATE_OF_ADMISSION_DESC_XENIA = " " + PREFIX_DATE_OF_ADMISSION + " "
            + VALID_DATE_OF_ADMISSION_JANE;
    public static final String DATE_OF_DEATH_DESC_ZACH = " " + PREFIX_DATE_OF_DEATH + " "
        + VALID_DATE_OF_DEATH_JOHN;
    public static final String DATE_OF_DEATH_DESC_XENIA = " " + PREFIX_DATE_OF_DEATH + " "
        + VALID_DATE_OF_DEATH_JANE;

    public static final String NAME_DESC_JOHN = " " + PREFIX_NAME + " " + VALID_NAME_JOHN;
    public static final String NAME_DESC_JANE = " " + PREFIX_NAME + " " + VALID_NAME_JANE;
    public static final String SEX_DESC_JOHN = " " + PREFIX_SEX + " " + VALID_SEX_JOHN;
    public static final String SEX_DESC_JANE = " " + PREFIX_SEX + " " + VALID_SEX_JANE;
    public static final String DATE_OF_ADMISSION_DESC_JOHN = " " + PREFIX_DATE_OF_ADMISSION + " "
            + VALID_DATE_OF_ADMISSION_JOHN;
    public static final String DATE_OF_ADMISSION_DESC_JANE = " " + PREFIX_DATE_OF_ADMISSION + " "
        + VALID_DATE_OF_ADMISSION_JANE;
    public static final String DATE_OF_DEATH_DESC_JOHN = " " + PREFIX_DATE_OF_DEATH + " " + VALID_DATE_OF_DEATH_JOHN;
    public static final String DATE_OF_DEATH_DESC_JANE = " " + PREFIX_DATE_OF_DEATH + " " + VALID_DATE_OF_DEATH_JANE;


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE_NUMBER + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE_NUMBER + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE_NUMBER + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
        } catch (CommandException ce) {
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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered entity list and selected entity in {@code actualModel} remain unchanged
     */
    public static void assertDeleteCommandFailure(Command command, Model actualModel, String expectedMessage,
                                                  String entityType) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Entity> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEntityList(entityType));

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredEntityList(entityType));
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
