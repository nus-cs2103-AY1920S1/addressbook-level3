package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BRAND_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NAME_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPhoneAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PHONE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.testutil.EditPhoneDescriptorBuilder;
import seedu.address.testutil.PhoneBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditPhoneCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Phone editedPhone = new PhoneBuilder().build();
        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder(editedPhone).build();
        EditPhoneCommand editCommand = new EditPhoneCommand(INDEX_FIRST_PHONE, descriptor);

        String expectedMessage = String.format(EditPhoneCommand.MESSAGE_EDIT_PHONE_SUCCESS, editedPhone);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setPhone(model.getFilteredPhoneList().get(0), editedPhone);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPhone = Index.fromOneBased(model.getFilteredPhoneList().size());
        Phone lastPhone = model.getFilteredPhoneList().get(indexLastPhone.getZeroBased());

        PhoneBuilder phoneInList = new PhoneBuilder(lastPhone);
        Phone editedPhone = phoneInList.withName(VALID_PHONE_NAME_IPHONE)
                .withBrand(VALID_BRAND_IPHONE).withCapacity(VALID_CAPACITY_IPHONE).withCost(VALID_COST_IPHONE)
                .withColour(VALID_COLOUR_IPHONE).withSerialNumber(VALID_SERIAL_NUMBER_IPHONE).build();

        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder().withPhoneName(VALID_PHONE_NAME_IPHONE)
                .withBrand(VALID_BRAND_IPHONE).withCapacity(VALID_CAPACITY_IPHONE).withCost(VALID_COST_IPHONE)
                .withColour(VALID_COLOUR_IPHONE).withSerialNumber(VALID_SERIAL_NUMBER_IPHONE).build();
        EditPhoneCommand editCommand = new EditPhoneCommand(indexLastPhone, descriptor);

        String expectedMessage = String.format(EditPhoneCommand.MESSAGE_EDIT_PHONE_SUCCESS, editedPhone);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setPhone(lastPhone, editedPhone);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPhoneCommand editCommand = new EditPhoneCommand(INDEX_FIRST_PHONE, new EditPhoneDescriptor());
        Phone editedPhone = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());

        String expectedMessage = String.format(EditPhoneCommand.MESSAGE_EDIT_PHONE_SUCCESS, editedPhone);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);

        Phone phoneInFilteredList = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());
        Phone editedPhone = new PhoneBuilder(phoneInFilteredList).withSerialNumber(VALID_SERIAL_NUMBER_IPHONE).build();
        EditPhoneCommand editCommand = new EditPhoneCommand(INDEX_FIRST_PHONE,
                new EditPhoneDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_IPHONE).build());

        String expectedMessage = String.format(EditPhoneCommand.MESSAGE_EDIT_PHONE_SUCCESS, editedPhone);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setPhone(model.getFilteredPhoneList().get(0), editedPhone);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePhoneUnfilteredList_failure() {
        Phone firstPhone = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());
        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder(firstPhone).build();
        EditPhoneCommand editCommand = new EditPhoneCommand(INDEX_SECOND_PHONE, descriptor);

        assertCommandFailure(editCommand, model, EditPhoneCommand.MESSAGE_DUPLICATE_PHONE);
    }

    @Test
    public void execute_duplicatePhoneFilteredList_failure() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);

        // edit phone in filtered list into a duplicate in address book
        Phone phoneInList = model.getPhoneBook().getList().get(INDEX_SECOND_PHONE.getZeroBased());
        EditPhoneCommand editCommand = new EditPhoneCommand(INDEX_FIRST_PHONE,
                new EditPhoneDescriptorBuilder(phoneInList).build());

        assertCommandFailure(editCommand, model, EditPhoneCommand.MESSAGE_DUPLICATE_PHONE);
    }

    @Test
    public void execute_invalidPhoneIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPhoneList().size() + 1);
        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder()
                .withPhoneName(VALID_PHONE_NAME_IPHONE).build();
        EditPhoneCommand editCommand = new EditPhoneCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of phone book
     */
    @Test
    public void execute_invalidPhoneIndexFilteredList_failure() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);
        Index outOfBoundIndex = INDEX_SECOND_PHONE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPhoneBook().getList().size());

        EditPhoneCommand editCommand = new EditPhoneCommand(outOfBoundIndex,
                new EditPhoneDescriptorBuilder().withPhoneName(VALID_PHONE_NAME_IPHONE).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPhoneCommand standardCommand = new EditPhoneCommand(INDEX_FIRST_PHONE, DESC_IPHONE);

        // same values -> returns true
        EditPhoneDescriptor copyDescriptor = new EditPhoneDescriptor(DESC_IPHONE);
        EditPhoneCommand commandWithSameValues = new EditPhoneCommand(INDEX_FIRST_PHONE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPhoneCommand(INDEX_SECOND_PHONE, DESC_IPHONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPhoneCommand(INDEX_FIRST_PHONE, DESC_SAMSUNG)));
    }

}
