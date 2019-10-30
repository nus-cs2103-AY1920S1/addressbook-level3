package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORDER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORDER_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PHONE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditOrderCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Index indexLastPhone = Index.fromOneBased(model.getFilteredPhoneList().size());
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());

        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList
                .withCustomer(model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased()))
                .withPhone(model.getFilteredPhoneList().get(indexLastPhone.getZeroBased()))
                .withPrice(VALID_PRICE_IPHONE)
                .withTags(VALID_TAG_NEW)
                .build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withCustomer(model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased()))
                .withPhone(model.getFilteredPhoneList().get(indexLastPhone.getZeroBased()))
                .withPrice(VALID_PRICE_IPHONE)
                .withTags(VALID_TAG_NEW)
                .build();
        EditOrderCommand editCommand = new EditOrderCommand(indexLastOrder,
                Optional.of(indexLastCustomer), Optional.of(indexLastPhone), descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Index indexLastPhone = Index.fromOneBased(model.getFilteredPhoneList().size());
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());

        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList
                .withCustomer(model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased()))
                .withPhone(model.getFilteredPhoneList().get(indexLastPhone.getZeroBased()))
                .withPrice(VALID_PRICE_IPHONE)
                .build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withCustomer(model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased()))
                .withPhone(model.getFilteredPhoneList().get(indexLastPhone.getZeroBased()))
                .withPrice(VALID_PRICE_IPHONE)
                .build();
        EditOrderCommand editCommand = new EditOrderCommand(indexLastOrder,
                Optional.of(indexLastCustomer), Optional.of(indexLastPhone), descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditOrderCommand editCommand = new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.empty(), Optional.empty(), new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).withPrice(VALID_PRICE_IPHONE).build();
        EditOrderCommand editCommand = new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.empty(), Optional.empty(),
                new EditOrderDescriptorBuilder().withPrice(VALID_PRICE_IPHONE).build());

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditOrderCommand editCommand = new EditOrderCommand(INDEX_SECOND_ORDER, Optional.empty(),
                Optional.empty(), descriptor);

        assertCommandFailure(editCommand, model, EditOrderCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_duplicateOrderFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        // edit order in filtered list into a duplicate in address book
        Order orderInList = model.getOrderBook().getList().get(INDEX_SECOND_ORDER.getZeroBased());
        EditOrderCommand editCommand = new EditOrderCommand(INDEX_FIRST_ORDER, Optional.empty(), Optional.empty(),
                new EditOrderDescriptorBuilder(orderInList).build());

        assertCommandFailure(editCommand, model, EditOrderCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_IPHONE).build();
        EditOrderCommand editCommand = new EditOrderCommand(outOfBoundIndex, Optional.empty(),
                Optional.empty(), descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of order book
     */
    @Test
    public void execute_invalidPhoneIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getList().size());

        EditOrderCommand editCommand = new EditOrderCommand(outOfBoundIndex, Optional.empty(),
                Optional.empty(), new EditOrderDescriptorBuilder().withPrice(VALID_PRICE_IPHONE).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditOrderCommand standardCommand = new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.of(INDEX_FIRST_CUSTOMER),
                Optional.of(INDEX_FIRST_PHONE), DESC_ORDER_IPHONE);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_ORDER_IPHONE);
        EditOrderCommand commandWithSameValues = new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.of(INDEX_FIRST_CUSTOMER), Optional.of(INDEX_FIRST_PHONE), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different order index -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_SECOND_ORDER,
                Optional.of(INDEX_FIRST_CUSTOMER), Optional.of(INDEX_FIRST_PHONE), DESC_ORDER_IPHONE)));


        // different customer index -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.of(INDEX_SECOND_CUSTOMER), Optional.of(INDEX_FIRST_PHONE), DESC_ORDER_IPHONE)));

        // different phone index -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.of(INDEX_FIRST_CUSTOMER), Optional.of(INDEX_SECOND_PHONE), DESC_ORDER_IPHONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER,
                Optional.of(INDEX_FIRST_CUSTOMER),
                Optional.of(INDEX_FIRST_PHONE), DESC_ORDER_SAMSUNG)));
    }

}
