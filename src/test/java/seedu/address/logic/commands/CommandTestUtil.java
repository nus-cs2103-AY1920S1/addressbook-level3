package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_SCHEDULE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_SCHEDULE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MANPOWER_TO_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.employee.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.NameContainsKeywordsPredicate;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //Valid Employee Fields
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

    //Valid Employee Fields with Prefixes
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

    //Invalid Employee Fields with Prefixes
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    //Valid Event Fields
    public static final String VALID_EVENT_NAME_PARTY = "Party";
    public static final String VALID_EVENT_NAME_MUSICAL = "Musical";
    public static final String VALID_VENUE_PARTY = "Orchard";
    public static final String VALID_VENUE_MUSICAL = "NUS";
    public static final String VALID_MANPOWER_COUNT_PARTY = "2";
    public static final String VALID_MANPOWER_COUNT_MUSICAL = "10";
    public static final String VALID_MANPOWER_COUNT_TO_ADD = "10";
    public static final String VALID_DATE_1_PARTY = "20/10/2019";
    public static final String VALID_DATE_1_MUSICAL = "10/10/2021";
    public static final String VALID_DATE_2_PARTY = "22/10/2019";
    public static final String VALID_DATE_2_MUSICAL = "15/10/2021";
    public static final String VALID_TAG_PARTY = "fun";
    public static final String VALID_TAG_MUSICAL = "free";
    public static final String MANPOWER_COUNT_DESC = " " + PREFIX_MANPOWER_TO_ADD + VALID_MANPOWER_COUNT_MUSICAL;
    public static final String INVALID_MANPOWER_DESC = " " + PREFIX_MANPOWER_TO_ADD + "James&";

    //Valid Event Fields with Prefixes
    public static final String EVENT_NAME_DESC_MUSICAL = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_MUSICAL;
    public static final String EVENT_NAME_DESC_PARTY = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_PARTY;
    public static final String VENUE_DESC_MUSICAL = " " + PREFIX_EVENT_VENUE + VALID_VENUE_MUSICAL;
    public static final String VENUE_DESC_PARTY = " " + PREFIX_EVENT_VENUE + VALID_VENUE_PARTY;
    public static final String MANPOWER_NEEDED_DESC_MUSICAL = " " + PREFIX_EVENT_MANPOWER_NEEDED
            + VALID_MANPOWER_COUNT_MUSICAL;
    public static final String MANPOWER_NEEDED_DESC_PARTY = " " + PREFIX_EVENT_MANPOWER_NEEDED
            + VALID_MANPOWER_COUNT_PARTY;
    public static final String START_DATE_DESC_MUSICAL = " " + PREFIX_EVENT_START_DATE + VALID_DATE_1_MUSICAL;
    public static final String END_DATE_DESC_MUSICAL = " " + PREFIX_EVENT_END_DATE + VALID_DATE_2_MUSICAL;
    public static final String START_DATE_DESC_PARTY = " " + PREFIX_EVENT_START_DATE + VALID_DATE_1_PARTY;
    public static final String END_DATE_DESC_PARTY = " " + PREFIX_EVENT_END_DATE + VALID_DATE_2_PARTY;
    public static final String TAG_DESC_PARTY = " " + PREFIX_TAG + VALID_TAG_PARTY;
    public static final String TAG_DESC_MUSIC = " " + PREFIX_TAG + VALID_TAG_MUSICAL;

    //Event Invalid Fields
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "Music@l";
    public static final String INVALID_VENUE_DESC = " " + PREFIX_EVENT_VENUE + "   ";
    public static final String INVALID_MANPOWER_NEEDED_DESC = " "
            + PREFIX_EVENT_MANPOWER_NEEDED + "3000";
    public static final String INVALID_START_DATE_DESC = " "
            + PREFIX_EVENT_START_DATE + "2019/10/29"; // '&' not allowed in names
    public static final String INVALID_END_DATE_DESC = " "
            + PREFIX_EVENT_END_DATE + "20 Aug 2019"; // '&' not allowed in names

    //valid date fields
    public static final String VALID_DATE_1 = " " + PREFIX_DATE + "20/10/2019";
    public static final String VALID_DATE_2 = " " + PREFIX_DATE + "22/10/2019";
    public static final String VALID_YEAR_MONTH_1 = " " + PREFIX_YEAR_MONTH + "10/2019";
    public static final String VALID_YEAR_MONTH_2 = " " + PREFIX_YEAR_MONTH + "01/2019";
    public static final String VALID_DATE_RANGE_1 = " " + PREFIX_EVENT_SCHEDULE_START
            + "01/10/2019 " + PREFIX_EVENT_SCHEDULE_END + "30/12/2019";
    public static final String VALID_DATE_RANGE_2 = " " + PREFIX_EVENT_SCHEDULE_START
            + "10/10/2019 " + PREFIX_EVENT_SCHEDULE_END + "12/10/2019";

    //invalid date fields
    public static final String INVALID_DATE_1 = " " + PREFIX_DATE + "2019/10/29";
    public static final String INVALID_DATE_2 = " " + PREFIX_DATE + "20 Aug 2019";
    public static final String INVALID_DATE_3 = " " + PREFIX_DATE + "date";
    public static final String INVALID_DATE_4 = " " + PREFIX_DATE + "10/12";
    public static final String INVALID_YEAR_MONTH_1 = " " + PREFIX_YEAR_MONTH + "1/2019";
    public static final String INVALID_YEAR_MONTH_2 = " " + PREFIX_YEAR_MONTH + "2019/10";
    public static final String INVALID_YEAR_MONTH_3 = " " + PREFIX_YEAR_MONTH + "2019 August";
    public static final String INVALID_YEAR_MONTH_4 = " " + PREFIX_YEAR_MONTH + "August 2019";
    public static final String INVALID_DATE_RANGE_1 = " " + PREFIX_EVENT_SCHEDULE_START
            + "2019/10/29 " + PREFIX_EVENT_SCHEDULE_END + "30/12/2019";
    public static final String INVALID_DATE_RANGE_2 = " " + PREFIX_EVENT_SCHEDULE_START
            + "01/01/2019 " + PREFIX_EVENT_SCHEDULE_END + "30 Aug 2019";
    public static final String INVALID_DATE_RANGE_3 = " " + PREFIX_EVENT_SCHEDULE_START
            + "10 Aug 2019 " + PREFIX_EVENT_SCHEDULE_END + "30 Aug 2019";
    public static final String INVALID_DATE_RANGE_4 = " " + PREFIX_EVENT_SCHEDULE_START
            + "30/12/2019 " + PREFIX_EVENT_SCHEDULE_END + "01/01/2019";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEmployeeDescriptor DESC_AMY;
    public static final EditCommand.EditEmployeeDescriptor DESC_BOB;

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
            assertEquals(actualModel, expectedModel);
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
     * - the address book, filtered employee list and selected employee in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Employee> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEmployeeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredEmployeeList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the employee at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEmployeeList().size());

        Employee employee = model.getFilteredEmployeeList().get(targetIndex.getZeroBased());
        final String[] splitName = employee.getEmployeeName().fullName.split("\\s+");
        model.updateFilteredEmployeeList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEmployeeList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s event book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().eventName.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }

}
