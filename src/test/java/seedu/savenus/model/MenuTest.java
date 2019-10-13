package seedu.savenus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.exceptions.DuplicateFoodException;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.testutil.FoodBuilder;

public class MenuTest {

    private final Menu menu = new Menu();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), menu.getFoodList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> menu.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMenu_replacesData() {
        Menu newData = getTypicalMenu();
        menu.resetData(newData);
        assertEquals(newData, menu);
    }

    @Test
    public void resetData_withDuplicatefoods_throwsDuplicatefoodException() {
        // Two foods with the same identity fields
        Food editedAlice = new FoodBuilder(CARBONARA).withTags(VALID_TAG_CHICKEN)
                .build();
        List<Food> newFoods = Arrays.asList(CARBONARA, editedAlice);
        MenuStub newData = new MenuStub(newFoods);

        assertThrows(DuplicateFoodException.class, () -> menu.resetData(newData));
    }

    @Test
    public void hasFood_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> menu.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInMenu_returnsFalse() {
        assertFalse(menu.hasFood(CARBONARA));
    }

    @Test
    public void hasFood_foodInMenu_returnsTrue() {
        menu.addFood(CARBONARA);
        assertTrue(menu.hasFood(CARBONARA));
    }

    @Test
    public void hasFood_foodWithSameIdentityFieldsInMenu_returnsTrue() {
        menu.addFood(CARBONARA);
        Food editedAlice = new FoodBuilder(CARBONARA).withTags(VALID_TAG_CHICKEN)
                .build();
        assertTrue(menu.hasFood(editedAlice));
    }

    @Test
    public void getFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> menu.getFoodList().remove(0));
    }

    /**
     * A stub ReadOnlyMenu whose foods list can violate interface constraints.
     */
    private static class MenuStub implements ReadOnlyMenu {
        private final ObservableList<Food> foods = FXCollections.observableArrayList();
        private final Wallet wallet = new Wallet();

        MenuStub(Collection<Food> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<Food> getFoodList() {
            return foods;
        }

        @Override
        public Wallet getWallet() {
            return wallet;
        }

        @Override
        public DoubleProperty getRemainingBudgetProperty() {
            return wallet.getRemainingBudgetProperty();
        }

        @Override
        public IntegerProperty getDaysToExpireProperty() {
            return wallet.getDaysToExpireProperty();
        }
    }

}
