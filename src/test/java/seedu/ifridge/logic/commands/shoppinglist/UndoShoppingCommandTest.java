package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.ReadOnlyUserPrefs;
import seedu.ifridge.model.ReadOnlyWasteList;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteReport;
import seedu.ifridge.testutil.GroceryItemBuilder;
import seedu.ifridge.testutil.GroceryListBuilder;
import seedu.ifridge.testutil.ShoppingItemBuilder;
import seedu.ifridge.testutil.ShoppingListBuilder;

public class UndoShoppingCommandTest {
    @Test
    public void execute_shoppingListCanUndo_undoSuccessful() throws Exception {
        ShoppingItem validShoppingItem = new ShoppingItemBuilder().build();
        ShoppingList validShoppingList = new ShoppingListBuilder().withShoppingItem(validShoppingItem).build();
        GroceryItem validGroceryItem = new GroceryItemBuilder().build();
        GroceryList validBoughtList = new GroceryListBuilder().withGroceryItem(validGroceryItem).build();
        ModelStubWithVersionedShoppingListAndVersionedBoughtList expectedModelStub =
                new ModelStubWithVersionedShoppingListAndVersionedBoughtList(validShoppingList, validBoughtList);
        ModelStubAcceptingShoppingListUndo modelStub =
                new ModelStubAcceptingShoppingListUndo(validShoppingList, validBoughtList);

        CommandResult commandResult = new UndoShoppingCommand().execute(modelStub);

        assertEquals(String.format(UndoShoppingCommand.MESSAGE_SUCCESS),
                commandResult.getFeedbackToUser());
        assertEquals(expectedModelStub.versionedShoppingList, modelStub.versionedShoppingList);
        assertEquals(expectedModelStub.versionedBoughtList, modelStub.versionedBoughtList);
        assertEquals(expectedModelStub.getShoppingList(), modelStub.getShoppingList());
        assertEquals(expectedModelStub.getBoughtList(), modelStub.getBoughtList());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
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
        public IFridgeSettings getIFridgeSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIFridgeSettings(IFridgeSettings iFridgeSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getGroceryListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryListFilePath(Path groceryListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UnitDictionary getUnitDictionary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroceryItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryList(ReadOnlyGroceryList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ShoppingItem getShoppingItem(ShoppingItem shoppingItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroceryList getGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroceryItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroceryItem(GroceryItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryItem(GroceryItem target, GroceryItem editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GroceryItem> getFilteredGroceryItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroceryItemList(Predicate<GroceryItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroceryList undoGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroceryList redoGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTemplateListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTemplateListFilePath(Path templateListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTemplateList(ReadOnlyTemplateList templateList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTemplateList getTemplateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTemplate(UniqueTemplateItems toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTemplate(UniqueTemplateItems target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTemplate(UniqueTemplateItems toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTemplate(UniqueTemplateItems target, UniqueTemplateItems editedTemplate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setShownTemplate(UniqueTemplateItems templateToBeShown) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<UniqueTemplateItems> getFilteredTemplateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTemplateList(Predicate<UniqueTemplateItems> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TemplateItem> getFilteredTemplateToBeShown() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TemplateItem> updateFilteredTemplateToBeShown() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean containsTemplateItemWithName(Food foodItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Name getNameTemplateToBeShown() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTemplateList(UniqueTemplateItems prevTemplate, UniqueTemplateItems newTemplate, int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTemplateList undoTemplateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTemplateList redoTemplateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTemplateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTemplateList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public UniqueTemplateItems getPrevTemplate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueTemplateItems getNewTemplate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Integer getIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getWasteListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWasteListFilePath(Path wasteListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWasteList(ReadOnlyWasteList wasteList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWasteList getWasteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWasteList getWasteListByMonth(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWasteItem(GroceryItem toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GroceryItem> getFilteredWasteItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GroceryItem> getFilteredWasteItemListByMonth(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SortedSet<WasteMonth> getDescendingWasteMonths() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWasteMonth(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WasteMonth getEarliestWasteMonth() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WasteMonth getLatestWasteMonth() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TreeMap<WasteMonth, WasteList> getWasteArchive() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWasteItemList(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWasteReport(WasteReport wasteReport) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WasteReport getWasteReport() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitWasteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWasteList undoWasteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWasteList redoWasteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getShoppingListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setShoppingListFilePath(Path shoppingListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addShoppingItem(ShoppingItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortShoppingItems() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void urgentShoppingItem(ShoppingItem toMarkAsUrgent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setShoppingList(ReadOnlyShoppingList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyShoppingList getShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean containsShoppingItemWithName(Food foodItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasShoppingItem(ShoppingItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteShoppingItem(ShoppingItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setShoppingItem(ShoppingItem target, ShoppingItem editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ShoppingItem> getFilteredShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredShoppingList(Predicate<ShoppingItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBoughtListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBoughtListFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBoughtItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBoughtList(ReadOnlyGroceryList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroceryList getBoughtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBoughtItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBoughtItem(GroceryItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBoughtItem(GroceryItem target, GroceryItem editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GroceryItem> getFilteredBoughtItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBoughtItemList(Predicate<GroceryItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void commitBoughtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroceryList undoBoughtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroceryList redoBoughtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoBoughtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoBoughtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyShoppingList undoShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyShoppingList redoShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoShoppingList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a versionedShoppingList containing a single shoppingList and a versionedBoughtList
     * containing a single groceryList.
     */
    private class ModelStubWithVersionedShoppingListAndVersionedBoughtList extends UndoShoppingCommandTest.ModelStub {
        final List<ReadOnlyShoppingList> versionedShoppingList = new ArrayList<>();
        final List<ReadOnlyGroceryList> versionedBoughtList = new ArrayList<>();
        private ReadOnlyShoppingList shoppingList;
        private ReadOnlyGroceryList boughtList;

        ModelStubWithVersionedShoppingListAndVersionedBoughtList(ReadOnlyShoppingList shoppingList,
                                                                 ReadOnlyGroceryList boughtList) {
            requireNonNull(shoppingList);
            this.shoppingList = shoppingList;
            versionedShoppingList.add(shoppingList);

            requireNonNull(boughtList);
            this.boughtList = boughtList;
            versionedBoughtList.add(boughtList);
        }

        @Override
        public ReadOnlyShoppingList getShoppingList() {
            return shoppingList;
        }

        @Override
        public ReadOnlyGroceryList getBoughtList() {
            return boughtList;
        }
    }

    /**
     * A Model stub that always accept the shoppingList being undone.
     */
    private class ModelStubAcceptingShoppingListUndo extends UndoShoppingCommandTest.ModelStub {
        final List<ReadOnlyShoppingList> versionedShoppingList = new ArrayList<>();
        final List<ReadOnlyGroceryList> versionedBoughtList = new ArrayList<>();
        private ReadOnlyShoppingList shoppingList;
        private ReadOnlyGroceryList boughtList;

        ModelStubAcceptingShoppingListUndo(ReadOnlyShoppingList shoppingList, ReadOnlyGroceryList boughtList) {
            requireNonNull(shoppingList);
            this.shoppingList = new ShoppingList();
            versionedShoppingList.add(shoppingList);
            versionedShoppingList.add(shoppingList);
            requireNonNull(boughtList);
            this.boughtList = new GroceryList();
            versionedBoughtList.add(boughtList);
            versionedBoughtList.add(boughtList);
        }

        @Override
        public ReadOnlyShoppingList undoShoppingList() {
            return versionedShoppingList.remove(1);
        }

        @Override
        public boolean canUndoShoppingList() {
            return true;
        }

        @Override
        public void setShoppingList(ReadOnlyShoppingList newData) {
            shoppingList = newData;
        }

        @Override
        public void setBoughtList(ReadOnlyGroceryList newData) {
            boughtList = newData;
        }

        @Override
        public ReadOnlyGroceryList undoBoughtList() {
            return versionedBoughtList.remove(1);
        }

        @Override
        public void sortShoppingItems() {
        }

        @Override
        public ReadOnlyShoppingList getShoppingList() {
            return shoppingList;
        }

        @Override
        public ReadOnlyGroceryList getBoughtList() {
            return boughtList;
        }
    }
}
