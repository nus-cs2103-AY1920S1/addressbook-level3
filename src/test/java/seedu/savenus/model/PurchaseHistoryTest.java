package seedu.savenus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalPurchaseHistory.getTypicalPurchaseHistory;

import java.util.Collection;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.savenus.model.food.Food;
import seedu.savenus.model.wallet.Wallet;

public class PurchaseHistoryTest {

    private final PurchaseHistory purchaseHistory = new PurchaseHistory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), purchaseHistory.getPurchaseHistoryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> purchaseHistory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMenu_replacesData() {
        PurchaseHistory newData = getTypicalPurchaseHistory();
        purchaseHistory.resetData(newData);
        assertEquals(newData, purchaseHistory);
    }

    @Test
    public void getPurchaseHistoryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> purchaseHistory.getPurchaseHistoryList().remove(0));
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

    }

}
