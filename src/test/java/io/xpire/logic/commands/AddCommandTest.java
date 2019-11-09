package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_KIWI;

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
                null, new ExpiryDate(VALID_EXPIRY_DATE_KIWI), new Quantity(VALID_QUANTITY_KIWI)));
        //expiry date is null
        assertThrows(NullPointerException.class, () -> new AddCommand(
                new Name(VALID_NAME_KIWI), null, new Quantity(VALID_QUANTITY_KIWI)));
        //quantity is null
        assertThrows(NullPointerException.class, () -> new AddCommand(
                new Name(VALID_NAME_KIWI), new ExpiryDate(VALID_EXPIRY_DATE_KIWI), null));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        XpireItem kiwi = new XpireItemBuilder().withName(VALID_NAME_KIWI)
                .withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                .withQuantity(VALID_QUANTITY_KIWI).build();
        AddCommand addCommand = new AddCommand(new Name(VALID_NAME_KIWI), new ExpiryDate(VALID_EXPIRY_DATE_KIWI),
                new Quantity(VALID_QUANTITY_KIWI));
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS_ITEM_ADDED, kiwi);
        expectedModel.addItem(XPIRE, kiwi);
        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        XpireItem banana = new XpireItemBuilder().withName("Banana").build();
        AddCommand addKiwiCommand = new AddCommand(new Name(VALID_NAME_KIWI), new ExpiryDate(VALID_EXPIRY_DATE_KIWI),
                new Quantity(VALID_QUANTITY_KIWI));
        AddCommand addBananaCommand = new AddCommand(new Name(VALID_NAME_BANANA),
                new ExpiryDate(VALID_EXPIRY_DATE_BANANA), new Quantity(VALID_QUANTITY_BANANA));
        // same object -> returns true
        assertTrue(addKiwiCommand.equals(addKiwiCommand));
        // same values -> returns true
        AddCommand addKiwiCommandCopy = new AddCommand(new Name(VALID_NAME_KIWI),
                new ExpiryDate(VALID_EXPIRY_DATE_KIWI),
                new Quantity(VALID_QUANTITY_KIWI));
        assertTrue(addKiwiCommand.equals(addKiwiCommandCopy));
        // different types -> returns false
        assertFalse(addKiwiCommand.equals(1));
        // null -> returns false
        assertFalse(addKiwiCommand == null);
        // different xpireItem -> returns false
        assertFalse(addKiwiCommand.equals(addBananaCommand));
    }

}

