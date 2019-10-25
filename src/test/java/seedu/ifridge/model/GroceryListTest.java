package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.GroceryItem;

public class GroceryListTest {

    private final GroceryList groceryList = new GroceryList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), groceryList.getGroceryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groceryList.resetData(null));
    }

    /*@Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        GroceryList newData = getTypicalGroceryList();
        groceryList.resetData(newData);
        assertEquals(newData, groceryList);
    }*/

    /*
    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        GroceryItem editedAlice = new GroceryItemBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<GroceryItem> newFoods = Arrays.asList(ALICE, editedAlice);
        GroceryListStub newData = new GroceryListStub(newFoods);

        assertThrows(DuplicateFoodException.class, () -> groceryList.resetData(newData));
    }

     */

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groceryList.hasGroceryItem(null));
    }

    /*@Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(groceryList.hasGroceryItem(ALICE));
    }*/

    /*@Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        groceryList.addGroceryItem(ALICE);
        assertTrue(groceryList.hasGroceryItem(ALICE));
    }*/

    /*@Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        groceryList.addGroceryItem(ALICE);
        GroceryItem editedAlice = new GroceryItemBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(groceryList.hasGroceryItem(editedAlice));
    }*/

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> groceryList.getGroceryList().remove(0));
    }

    /**
     * A stub ReadOnlyGroceryList whose persons list can violate interface constraints.
     */
    private static class GroceryListStub implements ReadOnlyGroceryList {
        private final ObservableList<GroceryItem> foods = FXCollections.observableArrayList();

        GroceryListStub(Collection<GroceryItem> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<GroceryItem> getGroceryList() {
            return foods;
        }
    }

}
