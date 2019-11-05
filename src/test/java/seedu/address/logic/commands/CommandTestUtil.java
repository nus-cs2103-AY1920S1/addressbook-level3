package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.editcommand.EditCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.predicates.CustomerContainsKeywordsPredicate;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.predicates.OrderContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.testutil.EditCustomerDescriptorBuilder;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditPhoneDescriptorBuilder;
import seedu.address.testutil.EditScheduleDescriptorBuilder;
import seedu.address.testutil.TypicalCustomers;
import seedu.address.testutil.TypicalPhones;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_CONTACT_NUMBER_AMY = "11111111";
    public static final String VALID_CONTACT_NUMBER_BOB = "22222222";
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

    // =================================== Customer ====================================

    public static final String VALID_NAME_ALICE = "Alice Lim";
    public static final String VALID_NAME_BEN = "Ben Ten";
    public static final String VALID_CONTACT_NUMBER_ALICE = "98123459";
    public static final String VALID_CONTACT_NUMBER_BEN = "83719038";
    public static final String VALID_EMAIL_ALICE = "alice@example.com";
    public static final String VALID_EMAIL_BEN = "ben@example.com";
    public static final String VALID_TAG_REGULAR = "Regular";
    public static final String VALID_TAG_RICH = "Rich";

    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_NAME_ALICE;
    public static final String NAME_DESC_BEN = " " + PREFIX_NAME + VALID_NAME_BEN;
    public static final String CONTACT_NUMBER_DESC_ALICE = " " + PREFIX_CONTACT + VALID_CONTACT_NUMBER_ALICE;
    public static final String CONTACT_NUMBER_DESC_BEN = " " + PREFIX_CONTACT + VALID_CONTACT_NUMBER_BEN;
    public static final String EMAIL_DESC_ALICE = " " + PREFIX_EMAIL + VALID_EMAIL_ALICE;
    public static final String EMAIL_DESC_BEN = " " + PREFIX_EMAIL + VALID_EMAIL_BEN;
    public static final String TAG_DESC_REGULAR = " " + PREFIX_TAG + VALID_TAG_REGULAR;
    public static final String TAG_DESC_RICH = " " + PREFIX_TAG + VALID_TAG_RICH;

    public static final String INVALID_CUSTOMER_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_CONTACT_NUMBER_DESC = " " + PREFIX_CONTACT + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    // =================================== Phone ====================================

    public static final String VALID_PHONE_NAME_IPHONE = "iPhone Pro 11";
    public static final String VALID_PHONE_NAME_SAMSUNG = "Galaxy S10";
    public static final String VALID_IDENTITY_NUMBER_IPHONE = "013373005371667";
    public static final String VALID_IDENTITY_NUMBER_SAMSUNG = "352039075644270";
    public static final String VALID_SERIAL_NUMBER_IPHONE = "1d27s9az";
    public static final String VALID_SERIAL_NUMBER_SAMSUNG = "29asdn1mx";
    public static final String VALID_COLOUR_IPHONE = "Purple";
    public static final String VALID_COLOUR_SAMSUNG = "Black";
    public static final String VALID_BRAND_IPHONE = "Apple";
    public static final String VALID_BRAND_SAMSUNG = "Samsung";
    public static final Capacity VALID_CAPACITY_IPHONE = Capacity.SIZE_128GB;
    public static final Capacity VALID_CAPACITY_SAMSUNG = Capacity.SIZE_256GB;
    public static final String VALID_COST_IPHONE = "$1649";
    public static final String VALID_COST_SAMSUNG = "$1298";
    public static final String VALID_PRICE_IPHONE = "$2000";
    public static final String VALID_PRICE_IPHONEXR = "$3000";
    public static final String VALID_PRICE_SAMSUNG = "$1500";
    public static final String VALID_TAG_NEW = "New";
    public static final String VALID_TAG_USED = "Used";
    public static final String VALID_TAG_BESTSELLER = "Bestseller";

    public static final String PHONE_NAME_DESC_IPHONE = " " + PREFIX_NAME + VALID_PHONE_NAME_IPHONE;
    public static final String PHONE_NAME_DESC_SAMSUNG = " " + PREFIX_NAME + VALID_PHONE_NAME_SAMSUNG;
    public static final String IDENTITY_NUM_DESC_IPHONE = " " + PREFIX_IDENTITY_NUM + VALID_IDENTITY_NUMBER_IPHONE;
    public static final String IDENTITY_NUM_DESC_SAMSUNG = " " + PREFIX_IDENTITY_NUM + VALID_IDENTITY_NUMBER_SAMSUNG;
    public static final String SERIAL_NUM_DESC_IPHONE = " " + PREFIX_SERIAL_NUM + VALID_SERIAL_NUMBER_IPHONE;
    public static final String SERIAL_NUM_DESC_SAMSUNG = " " + PREFIX_SERIAL_NUM + VALID_SERIAL_NUMBER_SAMSUNG;
    public static final String BRAND_DESC_IPHONE = " " + PREFIX_BRAND + VALID_BRAND_IPHONE;
    public static final String BRAND_DESC_SAMSUNG = " " + PREFIX_BRAND + VALID_BRAND_SAMSUNG;
    public static final String COLOUR_DESC_IPHONE = " " + PREFIX_COLOUR + VALID_COLOUR_IPHONE;
    public static final String COLOUR_DESC_SAMSUNG = " " + PREFIX_COLOUR + VALID_COLOUR_SAMSUNG;
    public static final String CAPACITY_DESC_IPHONE = " " + PREFIX_CAPACITY + "128";
    public static final String CAPACITY_DESC_SAMSUNG = " " + PREFIX_CAPACITY + "256";
    public static final String COST_DESC_IPHONE = " " + PREFIX_COST + VALID_COST_IPHONE;
    public static final String COST_DESC_SAMSUNG = " " + PREFIX_COST + VALID_COST_SAMSUNG;
    public static final String PRICE_DESC_IPHONE = " " + PREFIX_PRICE + VALID_PRICE_IPHONE;
    public static final String PRICE_DESC_IPHONEXR = " " + PREFIX_PRICE + VALID_PRICE_IPHONEXR;
    public static final String TAG_DESC_NEW = " " + PREFIX_TAG + VALID_TAG_NEW;
    public static final String TAG_DESC_USED = " " + PREFIX_TAG + VALID_TAG_USED;
    public static final String TAG_DESC_BESTSELLER = " " + PREFIX_TAG + VALID_TAG_BESTSELLER;

    public static final String INVALID_PHONE_NAME_DESC = " " + PREFIX_NAME + "iPhone &";
    public static final String INVALID_IDENTITY_NUM_DESC = " " + PREFIX_IDENTITY_NUM + "123019238901283098190212312";
    public static final String INVALID_SERIAL_NUM_DESC = " " + PREFIX_SERIAL_NUM + "^asad";
    public static final String INVALID_BRAND_DESC = " " + PREFIX_BRAND + "";
    public static final String INVALID_COLOUR_DESC = " " + PREFIX_COLOUR + "";
    public static final String INVALID_CAPACITY_DESC = " " + PREFIX_CAPACITY + "129";
    public static final String INVALID_COST_DESC = " " + PREFIX_COST + "20.789";
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "120.89";

    // =================================== Order ====================================

    public static final String VALID_S_CUSTOMER_INDEX = "1";
    public static final String VALID_S_PHONE_INDEX = "3";
    public static final String VALID_S_CUSTOMER_INDEX_2 = "3";
    public static final String VALID_S_PHONE_INDEX_2 = "4";
    public static final String VALID_ORDER_PRICE = "$3000";
    public static final Index VALID_CUSTOMER_INDEX = Index.fromOneBased(1);
    public static final Index VALID_PHONE_INDEX = Index.fromOneBased(3);

    public static final String CUSTOMER_INDEX_DESC = " " + PREFIX_CUSTOMER + VALID_S_CUSTOMER_INDEX;
    public static final String PHONE_INDEX_DESC = " " + PREFIX_PHONE + VALID_S_PHONE_INDEX;
    public static final String CUSTOMER_INDEX_DESC_2 = " " + PREFIX_CUSTOMER + VALID_S_CUSTOMER_INDEX_2;
    public static final String PHONE_INDEX_DESC_2 = " " + PREFIX_PHONE + VALID_S_PHONE_INDEX_2;
    public static final Price PRICE_DESC_VIPORDER = new Price(VALID_ORDER_PRICE);

    public static final String INVALID_CUSTOMER_INDEX_DESC = " " + PREFIX_CUSTOMER + "-1";
    public static final String INVALID_PHONE_INDEX_DESC = " " + PREFIX_PHONE + "-1";

    // =================================== Schedule ====================================

    public static final int VALID_S_INDEX_MONDAY = 1;
    public static final int VALID_S_INDEX_FRIDAY = 2;
    public static final String VALID_DATE_MONDAY = "2019.12.17";
    public static final String VALID_DATE_FRIDAY = "2020.7.1";
    public static final String VALID_TIME_MONDAY = "13.00";
    public static final String VALID_TIME_FRIDAY = "19.30";
    public static final String VALID_VENUE_MONDAY = "Orchard MRT";
    public static final String VALID_VENUE_FRIDAY = "Buona Vista KOI";
    public static final String VALID_TAG_MONDAY = "Carrier";
    public static final String VALID_TAG_FRIDAY = "Freebie";
    public static final String VALID_TAG_EVERYDAY = "Bag";
    public static final Index VALID_INDEX_MONDAY = Index.fromOneBased(VALID_S_INDEX_MONDAY);
    public static final Index VALID_INDEX_FRIDAY = Index.fromOneBased(VALID_S_INDEX_FRIDAY);
    public static final Calendar VALID_CALENDAR_MONDAY = new Calendar.Builder()
            .setDate(2019, 11, 17).setTimeOfDay(13, 0, 0).build();
    public static final Calendar VALID_CALENDAR_FRIDAY = new Calendar.Builder()
            .setDate(2020, 6, 1).setTimeOfDay(19, 30, 0).build();
    public static final Calendar VALID_SCHEDULE_MONDAY = new Calendar.Builder().setDate(2019, 11, 17).build();
    public static final boolean VALID_ALLOW_EVERYDAY = true;
    public static final boolean VALID_DISALLOW_EVERYDAY = false;

    public static final String INDEX_DESC_MONDAY = " " + VALID_S_INDEX_MONDAY;
    public static final String INDEX_DESC_FRIDAY = " " + VALID_S_INDEX_FRIDAY;
    public static final String DATE_DESC_MONDAY = " " + PREFIX_DATE + VALID_DATE_MONDAY;
    public static final String DATE_DESC_FRIDAY = " " + PREFIX_DATE + VALID_DATE_FRIDAY;
    public static final String TIME_DESC_MONDAY = " " + PREFIX_TIME + VALID_TIME_MONDAY;
    public static final String TIME_DESC_FRIDAY = " " + PREFIX_TIME + VALID_TIME_FRIDAY;
    public static final String VENUE_DESC_MONDAY = " " + PREFIX_VENUE + VALID_VENUE_MONDAY;
    public static final String VENUE_DESC_FRIDAY = " " + PREFIX_VENUE + VALID_VENUE_FRIDAY;
    public static final String TAG_DESC_MONDAY = " " + PREFIX_TAG + VALID_TAG_MONDAY;
    public static final String TAG_DESC_FRIDAY = " " + PREFIX_TAG + VALID_TAG_FRIDAY;
    public static final String TAG_DESC_EVERYDAY = " " + PREFIX_TAG + VALID_TAG_EVERYDAY;
    public static final String ALLOW_DESC_EVERYDAY = " " + PREFIX_ALLOW;

    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "2012.a.1"; // 'a' not allowed in dates
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "20.$"; // '$' not allowed in time
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE; // empty string not allowed for venues

    //===========================================================================================

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditCustomerDescriptor DESC_ALICE;
    public static final EditCustomerDescriptor DESC_BEN;
    public static final EditPhoneDescriptor DESC_IPHONE;
    public static final EditPhoneDescriptor DESC_SAMSUNG;
    public static final EditOrderDescriptor DESC_ORDER_IPHONE;
    public static final EditOrderDescriptor DESC_ORDER_SAMSUNG;
    public static final EditScheduleDescriptor DESC_MONDAY;
    public static final EditScheduleDescriptor DESC_FRIDAY;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_ALICE = new EditCustomerDescriptorBuilder().withCustomerName(VALID_NAME_ALICE)
                .withContactNumber(VALID_CONTACT_NUMBER_ALICE).withEmail(VALID_EMAIL_ALICE)
                .withTags(VALID_TAG_REGULAR).build();

        DESC_BEN = new EditCustomerDescriptorBuilder().withCustomerName(VALID_NAME_BEN)
                .withContactNumber(VALID_CONTACT_NUMBER_BEN).withEmail(VALID_EMAIL_BEN)
                .withTags(VALID_TAG_RICH).build();

        DESC_IPHONE = new EditPhoneDescriptorBuilder().withPhoneName(VALID_PHONE_NAME_IPHONE)
                .withBrand(VALID_BRAND_IPHONE).withCapacity(VALID_CAPACITY_IPHONE).withCost(VALID_COST_IPHONE)
                .withColour(VALID_COLOUR_IPHONE).withSerialNumber(VALID_SERIAL_NUMBER_IPHONE)
                .withIdentityNumber(VALID_IDENTITY_NUMBER_IPHONE).build();

        DESC_SAMSUNG = new EditPhoneDescriptorBuilder().withPhoneName(VALID_PHONE_NAME_SAMSUNG)
                .withBrand(VALID_BRAND_SAMSUNG).withCapacity(VALID_CAPACITY_SAMSUNG).withCost(VALID_COST_SAMSUNG)
                .withColour(VALID_COLOUR_SAMSUNG).withSerialNumber(VALID_SERIAL_NUMBER_SAMSUNG)
                .withIdentityNumber(VALID_IDENTITY_NUMBER_SAMSUNG).build();

        DESC_ORDER_IPHONE = new EditOrderDescriptorBuilder()
                .withCustomer(TypicalCustomers.ALICE).withPhone(TypicalPhones.IPHONEPRO11)
                .withPrice(VALID_PRICE_IPHONE).withTags(VALID_TAG_NEW).build();

        DESC_ORDER_SAMSUNG = new EditOrderDescriptorBuilder()
                .withCustomer(TypicalCustomers.ALICE).withPhone(TypicalPhones.ANDROIDONE)
                .withPrice(VALID_PRICE_SAMSUNG).withTags(VALID_TAG_BESTSELLER).build();

        DESC_MONDAY = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY)
                .withTime(VALID_CALENDAR_MONDAY).withVenue(VALID_VENUE_MONDAY).withTags(VALID_TAG_EVERYDAY).build();

        DESC_FRIDAY = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_FRIDAY)
                .withTime(VALID_CALENDAR_FRIDAY).withVenue(VALID_VENUE_FRIDAY).withTags(VALID_TAG_EVERYDAY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel, new CommandHistory(), new UndoRedoStack());

            System.out.println(result.getFeedbackToUser());
            System.out.println(expectedCommandResult.getFeedbackToUser());
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory, new UndoRedoStack());

            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, UndoRedoStack actualUndoRedoStack,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        UndoRedoStack expectedUndoRedoStack = new UndoRedoStack(actualUndoRedoStack);
        try {
            CommandResult result = command.execute(actualModel, new CommandHistory(), actualUndoRedoStack);

            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedUndoRedoStack, actualUndoRedoStack);
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
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, UndoRedoStack actualUndoRedoStack,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualUndoRedoStack, expectedCommandResult, expectedModel);
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

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, new CommandHistory(),
                new UndoRedoStack()));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
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

    /**
     * Updates {@code model}'s filtered list to show only the customer at the given {@code targetIndex} in the
     * {@code model}'s customer book.
     */
    public static void showCustomerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCustomerList().size());

        Customer customer = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        final String[] splitName = customer.getCustomerName().fullName.split("\\s+");
        model.updateFilteredCustomerList(new CustomerContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCustomerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the phone at the given {@code targetIndex} in the
     * {@code model}'s phone book.
     */
    public static void showPhoneAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPhoneList().size());

        Phone phone = model.getFilteredPhoneList().get(targetIndex.getZeroBased());
        final String[] splitName = phone.getIdentityNumber().value.split("\\s+");
        model.updateFilteredPhoneList(new PhoneContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPhoneList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s order book.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderList().size());

        Order order = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        final String[] splitName = order.getId().toString().split("\\s+");
        model.updateFilteredOrderList(new OrderContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredOrderList().size());
    }


}
