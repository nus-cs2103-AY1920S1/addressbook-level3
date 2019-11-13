package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.editcommand.EditContactCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.AccommodationManager;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.NameContainsKeywordsPredicate;
import seedu.planner.testutil.contact.EditContactDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ACTIVITY_NAME_A = "Visit Temple";
    public static final String VALID_ACTIVITY_NAME_B = "Sushi class";
    public static final String VALID_ACTIVITY_ADDRESS_A = "Australia";
    public static final String VALID_ACTIVITY_ADDRESS_B = "Bali";
    public static final String VALID_TAG_SIGHTSEEING = "sightseeing";
    public static final String VALID_TAG_SHOPPING = "shopping";
    public static final String VALID_TAG_HIKING = "hiking";
    public static final String VALID_DURATION_A = "360";
    public static final String VALID_DURATION_B = "420";
    public static final String VALID_PRIORITY_SIX = "6";
    public static final String VALID_PRIORITY_SEVEN = "7";
    public static final String VALID_COST_HUNDRED = "100";
    public static final String VALID_COST_TWO_HUNDRED = "200";

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

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final Long VALID_ACTIVITY_COUNT = (long) 6;

    public static final EditContactCommand.EditContactDescriptor DESC_AMY;
    public static final EditContactCommand.EditContactDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
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
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            UiFocus[] uiFocus, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, uiFocus);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contacts list and selected contacts in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ContactManager expectedContactManager = new ContactManager(actualModel.getContacts());
        AccommodationManager expectedAccommodationManager = new AccommodationManager(actualModel.getAccommodations());
        ActivityManager expectedActivityManager = new ActivityManager(actualModel.getActivities());
        Itinerary expectedItinerary = new Itinerary(actualModel.getItinerary());

        List<Contact> expectedContactList = new ArrayList<>(actualModel.getFilteredContactList());
        List<Accommodation> expectedAccommodationList = new ArrayList<>(actualModel.getFilteredAccommodationList());
        List<Activity> expectedActivityList = new ArrayList<>(actualModel.getFilteredActivityList());
        List<Day> expectedItineraryList = new ArrayList<>(actualModel.getFilteredItinerary());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));

        assertEquals(expectedContactManager, actualModel.getContacts());
        assertEquals(expectedAccommodationManager, actualModel.getAccommodations());
        assertEquals(expectedActivityManager, actualModel.getActivities());
        assertEquals(expectedItinerary, actualModel.getItinerary());

        assertEquals(expectedContactList, actualModel.getFilteredContactList());
        assertEquals(expectedAccommodationList, actualModel.getFilteredAccommodationList());
        assertEquals(expectedActivityList, actualModel.getFilteredActivityList());
        assertEquals(expectedItineraryList, actualModel.getFilteredItinerary());
    }

    /**
     * Updates {@code model}'s filtered list to show only the contacts at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().toString().split("\\s+");
        model.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }

}
