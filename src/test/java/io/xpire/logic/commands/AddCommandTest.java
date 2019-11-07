package io.xpire.logic.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.xpire.commons.core.GuiSettings;
import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.CommandResult;
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.ReplenishList;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.ListToView;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.state.State;
import io.xpire.model.state.StateManager;
import javafx.collections.ObservableList;


import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_KIWI;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.GuiSettings;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.ReplenishList;
import io.xpire.model.state.StackManager;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.ListToView;
import io.xpire.model.item.Name;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.tag.Tag;
import io.xpire.testutil.XpireItemBuilder;
import javafx.collections.ObservableList;



public class AddCommandTest {

    private StateManager stateManager= new StateManager();
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
                .withQuantity("2").build();
        AddCommand addCommand = new AddCommand(new Name(VALID_NAME_KIWI), new ExpiryDate(VALID_EXPIRY_DATE_KIWI),
                new Quantity(VALID_QUANTITY_KIWI));
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS_ITEM_ADDED, kiwi);
        expectedModel.addItem(XPIRE, kiwi);
        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {

    }


}

