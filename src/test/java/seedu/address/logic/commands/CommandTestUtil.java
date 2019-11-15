package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.address.logic.commands.EditCommand;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.address.model.AddressBook;
import seedu.address.address.model.AddressBookModel;
import seedu.address.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.address.model.person.Person;
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
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_COUNTRY_AMY = "Singapore";
    public static final String VALID_COUNTRY_BOB = "Malaysia";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String COUNTRY_DESC_AMY = " " + PREFIX_COUNTRY + VALID_COUNTRY_AMY;
    public static final String COUNTRY_DESC_BOB = " " + PREFIX_COUNTRY + VALID_COUNTRY_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_COUNTRY_DESC = " " + PREFIX_COUNTRY + "Soviet Union"; // soviet union is not in
    //the list of countries

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withCountry(VALID_COUNTRY_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withCountry(VALID_COUNTRY_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualAddressBookModel} matches {@code expectedAddressBookModel}
     */
    public static void assertCommandSuccess(Command command, AddressBookModel actualAddressBookModel,
                                            CommandResult expectedCommandResult,
                                            AddressBookModel expectedAddressBookModel) {
        try {
            CommandResult result = command.execute(actualAddressBookModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedAddressBookModel, actualAddressBookModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, AddressBookModel, CommandResult, AddressBookModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, AddressBookModel actualAddressBookModel,
                                            String expectedMessage, AddressBookModel expectedAddressBookModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualAddressBookModel, expectedCommandResult, expectedAddressBookModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualAddressBookModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, AddressBookModel actualAddressBookModel,
                                            String expectedMessage) {
        // we are unable to defensively copy the addressBookModel for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualAddressBookModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualAddressBookModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualAddressBookModel));
        assertEquals(expectedAddressBook, actualAddressBookModel.getAddressBook());
        assertEquals(expectedFilteredList, actualAddressBookModel.getFilteredPersonList());
    }

    /**
     * Updates {@code addressBookModel}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code addressBookModel}'s address book.
     */
    public static void showPersonAtIndex(AddressBookModel addressBookModel, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < addressBookModel.getFilteredPersonList().size());

        Person person = addressBookModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        addressBookModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, addressBookModel.getFilteredPersonList().size());
    }

}
