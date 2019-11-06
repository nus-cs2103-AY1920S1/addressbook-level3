/* TODO:Rewrite Test cases here so that the stub fits the current integration.
package io.xpire.logic.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.xpire.commons.core.GuiSettings;
import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.CommandResult;
import io.xpire.model.Model;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.ListToView;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import javafx.collections.ObservableList;


package io.xpire.logic.commands;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_KIWI;
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

    private StackManager stackManager = new StackManager();

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingItemAdded modelStub = new ModelStubAcceptingItemAdded();
        XpireItem kiwi = new XpireItemBuilder().withName(VALID_NAME_KIWI)
                                            .withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                                            .withQuantity("1").build();
        CommandResult commandResult = new AddCommand(kiwi).execute(modelStub, stackManager);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, kiwi), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(kiwi), modelStub.itemsAdded);
    }

    @Test
    public void execute_duplicateItem_throwsCommandException() {
        XpireItem kiwi = new XpireItemBuilder().withName(VALID_NAME_KIWI)
                .withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                .withQuantity("1").build();
        AddCommand addCommand = new AddCommand(kiwi);
        ModelStub modelStub = new ModelStubWithItem(kiwi);
        StackManager stackManager = new StackManager();
        //duplicate items cannot be added to the list
        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ITEM, () -> addCommand.execute(
                modelStub, stackManager));
    }

    @Test
    public void equals() {
        XpireItem apple = new XpireItemBuilder().withName("Apple").build();
        XpireItem banana = new XpireItemBuilder().withName("Banana").build();
        AddCommand addAppleCommand = new AddCommand(apple);
        AddCommand addBananaCommand = new AddCommand(banana);

        // same object -> returns true
        assertTrue(addAppleCommand.equals(addAppleCommand));

        // same values -> returns true
        AddCommand addAppleCommandCopy = new AddCommand(apple);
        assertTrue(addAppleCommand.equals(addAppleCommandCopy));

        // different types -> returns false
        assertFalse(addAppleCommand.equals(1));

        // null -> returns false
        assertFalse(addAppleCommand.equals(null));

        // different xpireItem -> returns false
        assertFalse(addAppleCommand.equals(addBananaCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */

/*
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListFilePath(Path listFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addItem(XpireItem xpireItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setXpire(ReadOnlyListView<XpireItem> newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyListView<? extends Item>[] getLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyListView<XpireItem> getXpire() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasItem(XpireItem xpireItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(XpireItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItem(XpireItem target, XpireItem editedXpireItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Tag> getAllItemTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Name> getAllItemNames() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortItemList(XpireMethodOfSorting method) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<XpireItem> getFilteredXpireItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<? extends Item> getCurrentFilteredItemList() {
            return null;
        }

        @Override
        public List<XpireItem> getAllItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredItemList(Predicate<? extends Item> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredXpireItemList(Predicate<XpireItem> predicate) {

        }

        @Override
        public void setCurrentFilteredItemList(ListToView list) {

        }

        @Override
        public void setReplenishList(ReadOnlyListView<Item> replenishList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyListView<Item> getReplenishList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReplenishItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReplenishItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReplenishItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReplenishItem(Item target, Item editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Tag> getAllReplenishItemTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Name> getAllReplenishItemNames() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Item> getFilteredReplenishItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReplenishItemList(Predicate<Item> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Item> getReplenishItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateItemTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void shiftItemToReplenishList(XpireItem xpireItem) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single xpireItem.
     */
/*
    private class ModelStubWithItem extends ModelStub {
        private final XpireItem xpireItem;

        ModelStubWithItem(XpireItem xpireItem) {
            requireNonNull(xpireItem);
            this.xpireItem = xpireItem;
        }

        @Override
        public boolean hasItem(XpireItem xpireItem) {
            requireNonNull(xpireItem);
            return this.xpireItem.isSameItem(xpireItem);
        }
    }

    /**
     * A Model stub that always accept the xpireItem being added.
     */
/*
    private class ModelStubAcceptingItemAdded extends ModelStub {
        final ArrayList<XpireItem> itemsAdded = new ArrayList<>();

        @Override
        public boolean hasItem(XpireItem xpireItem) {
            requireNonNull(xpireItem);
            return itemsAdded.stream().anyMatch(xpireItem::isSameItem);
        }

        @Override
        public void addItem(XpireItem xpireItem) {
            requireNonNull(xpireItem);
            itemsAdded.add(xpireItem);
        }

        @Override
        public ReadOnlyListView<? extends Item>[] getLists() {
            return new ReadOnlyListView[]{new Xpire(), new ReplenishList()};
        }
    }

}
*/
