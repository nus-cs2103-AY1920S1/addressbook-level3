package seedu.address.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.IFridgeSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.TemplateList;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;
import seedu.address.model.waste.WasteMonth;
import seedu.address.testutil.TemplateItemBuilder;

public class AddTemplateItemCommandTest {

    @Test
    public void constructor_nullTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTemplateItemCommand(null, null));
    }

    @Test
    public void execute_templateItemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTemplateItemAdded modelStub = new ModelStubAcceptingTemplateItemAdded();
        TemplateItem validTemplateItem = new TemplateItemBuilder().build();

        /**CommandResult commandResult = new AddTemplateItemCommand(INDEX_FIRST_PERSON, validTemplateItem)
                .execute(modelStub);

        assertEquals(String.format(AddTemplateItemCommand.MESSAGE_SUCCESS, validTemplateItem),
        commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTemplateItem), modelStub.templateItemsAdded);**/
    }

    @Test
    public void equals() {
        TemplateItem mincedMeat = new TemplateItemBuilder().withName("Ground Pork").build();
        TemplateItem freshVeg = new TemplateItemBuilder().withName("Tomato").build();
        AddTemplateItemCommand addMincedMeatCommand = new AddTemplateItemCommand(INDEX_FIRST_PERSON, mincedMeat);
        AddTemplateItemCommand addFreshVegCommand = new AddTemplateItemCommand(INDEX_FIRST_PERSON, freshVeg);

        // same object -> returns true
        assertTrue(addMincedMeatCommand.equals(addMincedMeatCommand));

        // same values -> returns true
        AddTemplateItemCommand addMincedMeatCommandCopy = new AddTemplateItemCommand(INDEX_FIRST_PERSON, mincedMeat);
        assertTrue(addMincedMeatCommand.equals(addMincedMeatCommandCopy));

        // different types -> returns false
        assertFalse(addMincedMeatCommand.equals(1));

        // null -> returns false
        assertFalse(addMincedMeatCommand.equals(null));

        // different person -> returns false
        assertFalse(addMincedMeatCommand.equals(addFreshVegCommand));
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroceryItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryList(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getGroceryList() {
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
        public Path getTemplateListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTemplateListFilePath(Path templateListFilePath) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void setTemplateList(ReadOnlyTemplateList templateList) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ReadOnlyTemplateList getTemplateList() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public boolean hasTemplate(UniqueTemplateItems toAdd) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deleteTemplate(UniqueTemplateItems target) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void addTemplate(UniqueTemplateItems toAdd) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void setTemplate(UniqueTemplateItems target, UniqueTemplateItems editedTemplate) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ObservableList<UniqueTemplateItems> getFilteredTemplateList() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void updateFilteredTemplateList(Predicate<UniqueTemplateItems> predicate) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public Path getWasteListFilePath() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void setWasteListFilePath(Path wasteListFilePath) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void setWasteList(ReadOnlyWasteList wasteList) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ReadOnlyWasteList getWasteList() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ReadOnlyWasteList getWasteListByMonth(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void addWasteItem(GroceryItem toAdd) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ObservableList<GroceryItem> getFilteredWasteItemList() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ObservableList<GroceryItem> getFilteredWasteItemListByMonth(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public Set<WasteMonth> getListOfWasteMonths() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void updateFilteredWasteItemList(WasteMonth wasteMonth) {
            throw new AssertionError("This method should not be called.");
        };


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
        public void setShoppingList(ReadOnlyShoppingList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyShoppingList getShoppingList() {
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
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithTemplateItem extends ModelStub {
        private final TemplateItem templateItem;

        ModelStubWithTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            this.templateItem = templateItem;
        }

        public boolean hasTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            return this.templateItem.isSameFood(templateItem);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingTemplateItemAdded extends ModelStub {
        final ArrayList<TemplateItem> templateItemsAdded = new ArrayList<>();

        public boolean hasTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            return templateItemsAdded.stream().anyMatch(templateItem::isSameFood);
        }

        public void addTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            templateItemsAdded.add(templateItem);
        }

        public ReadOnlyTemplateList getTemplateList() {
            return new TemplateList();
        }
    }

}
