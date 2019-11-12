package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.getTypicalLists;

import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_CORIANDER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.testutil.XpireItemBuilder;

public class AddCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        //name is null
        assertThrows(NullPointerException.class, () -> new AddCommand(
                null, new ExpiryDate(VALID_EXPIRY_DATE_APPLE), new Quantity(VALID_QUANTITY_APPLE)));
        //expiry date is null
        assertThrows(NullPointerException.class, () -> new AddCommand(
                new Name(VALID_NAME_APPLE), null, new Quantity(VALID_QUANTITY_APPLE)));
        //quantity is null
        assertThrows(NullPointerException.class, () -> new AddCommand(
                new Name(VALID_NAME_APPLE), new ExpiryDate(VALID_EXPIRY_DATE_APPLE), null));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        XpireItem coriander = new XpireItemBuilder().withName(VALID_NAME_CORIANDER)
                .withExpiryDate(VALID_EXPIRY_DATE_CORIANDER)
                .withQuantity(VALID_QUANTITY_CORIANDER).build();
        AddCommand addCommand = new AddCommand(new Name(VALID_NAME_CORIANDER),
                new ExpiryDate(VALID_EXPIRY_DATE_CORIANDER), new Quantity(VALID_QUANTITY_CORIANDER));
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS_ITEM_ADDED, coriander);
        expectedModel.addItem(XPIRE, coriander);
        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateItemAcceptedByModel_addSuccessful() {
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        AddCommand addCommand = new AddCommand(new Name(VALID_NAME_BANANA), new ExpiryDate(VALID_EXPIRY_DATE_BANANA),
                new Quantity(VALID_QUANTITY_BANANA));
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS_ITEM_UPDATED,
                VALID_NAME_BANANA, new Quantity("2"));
        //retrieves banana
        XpireItem itemToUpdate = (XpireItem) expectedModel.getItemList(XPIRE).get(1);
        XpireItem newItem = new XpireItemBuilder().withName(VALID_NAME_BANANA)
                .withExpiryDate(VALID_EXPIRY_DATE_BANANA)
                .withQuantity("2").build();
        expectedModel.setItem(XPIRE, itemToUpdate, newItem);
        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        AddCommand addAppleCommand = new AddCommand(new Name(VALID_NAME_APPLE), new ExpiryDate(VALID_EXPIRY_DATE_APPLE),
                new Quantity(VALID_QUANTITY_APPLE));
        AddCommand addBananaCommand = new AddCommand(new Name(VALID_NAME_BANANA),
                new ExpiryDate(VALID_EXPIRY_DATE_BANANA), new Quantity(VALID_QUANTITY_BANANA));
        // same object -> returns true
        assertTrue(addAppleCommand.equals(addAppleCommand));
        // same values -> returns true
        AddCommand addAppleCommandCopy = new AddCommand(new Name(VALID_NAME_APPLE),
                new ExpiryDate(VALID_EXPIRY_DATE_APPLE),
                new Quantity(VALID_QUANTITY_APPLE));
        assertTrue(addAppleCommand.equals(addAppleCommandCopy));
        // different types -> returns false
        assertFalse(addAppleCommand.equals(1));
        // null -> returns false
        assertFalse(addAppleCommand == null);
        // different xpireItem -> returns false
        assertFalse(addAppleCommand.equals(addBananaCommand));
    }

}

