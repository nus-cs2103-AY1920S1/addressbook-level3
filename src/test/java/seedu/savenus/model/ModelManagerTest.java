package seedu.savenus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.model.Model.PREDICATE_SHOW_ALL_FOOD;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.TONKATSU_RAMEN;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.NameContainsKeywordsPredicate;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.sorter.CustomSorter;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.testutil.MenuBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Menu(), new Menu(modelManager.getMenu()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMenuFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setMenuFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setMenuFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setMenuFilePath(null));
    }

    @Test
    public void setMenuFilePath_validPath_setsMenuFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setMenuFilePath(path);
        assertEquals(path, modelManager.getMenuFilePath());
    }

    @Test
    public void hasFood_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInMenu_returnsFalse() {
        assertFalse(modelManager.hasFood(CARBONARA));
    }

    @Test
    public void hasFood_foodInMenu_returnsTrue() {
        modelManager.addFood(CARBONARA);
        assertTrue(modelManager.hasFood(CARBONARA));
    }

    @Test
    public void getFilteredfoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFoodList().remove(0));
    }

    @Test
    public void getPurchaseHistoryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getPurchaseHistoryList().remove(0));
    }

    @Test
    public void nullRecommendationComparator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateRecommendationComparator(null));
    }

    @Test
    public void nullRecommendationPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateRecommendationPredicate(null));
    }

    @Test
    public void updateRecommendationPredicate_inUse() {
        Predicate<Food> recommendationPredicate = x -> false;
        modelManager.updateRecommendationPredicate(recommendationPredicate);
        modelManager.setRecommendationSystemInUse(true);

        assertEquals(recommendationPredicate, modelManager.getRecommendationSystem().getRecommendationPredicate());
    }

    @Test
    public void updateRecommendationPredicate_notInUse() {
        Predicate<Food> recommendationPredicate = x -> false;
        modelManager.updateRecommendationPredicate(recommendationPredicate);
        modelManager.setRecommendationSystemInUse(false);

        assertNotEquals(recommendationPredicate, modelManager.getRecommendationSystem().getRecommendationPredicate());
    }

    @Test
    public void updateRecommendationComparator_inUse() {
        Comparator<Food> recommendationComparator = (x, y) -> 12345;
        modelManager.updateRecommendationComparator(recommendationComparator);
        modelManager.setRecommendationSystemInUse(true);

        assertEquals(recommendationComparator, modelManager.getRecommendationSystem().getRecommendationComparator());
    }

    @Test
    public void updateRecommendationComparator_notInUse() {
        Comparator<Food> recommendationComparator = (x, y) -> 12345;
        modelManager.updateRecommendationComparator(recommendationComparator);
        modelManager.setRecommendationSystemInUse(false);

        assertNotEquals(recommendationComparator, modelManager.getRecommendationSystem().getRecommendationComparator());
    }

    // WALLET

    @Test
    public void get_wallet_test() {
        Wallet wallet = modelManager.getWallet();
        assertEquals(wallet, new Wallet());
        assertNotEquals(wallet, null);
    }

    @Test
    public void get_remainingBudget_test() {
        Wallet wallet = new Wallet("30", "10");
        assertTrue(new BigDecimal(30).compareTo(wallet.getRemainingBudgetAmount()) == 0);
    }

    @Test
    public void set_remainingBudget_test() {
        Wallet wallet = new Wallet();
        wallet.setRemainingBudget(new RemainingBudget("250.50"));
        assertTrue(new BigDecimal(250.50).compareTo(wallet.getRemainingBudgetAmount()) == 0);
    }

    @Test
    public void set_remainingBudget_throwsCommandException() {
        assertThrows(CommandException.class, () -> modelManager
                .setRemainingBudget(new RemainingBudget("1000000000")));
    }

    @Test
    public void get_daysToExpire_test() {
        Wallet wallet = new Wallet("30", "10");
        assertEquals(10, wallet.getNumberOfDaysToExpire());
    }

    @Test
    public void set_daysToExpire_test() {
        Wallet wallet = new Wallet();
        wallet.setDaysToExpire(new DaysToExpire("50"));
        assertEquals(50, wallet.getNumberOfDaysToExpire());
    }

    @Test
    public void set_daysToExpire_throwsCommandException() {
        Wallet wallet = new Wallet();
        assertThrows(CommandException.class, () -> modelManager
                .setDaysToExpire(new DaysToExpire("1000")));
    }

    @Test
    public void autoSortFlag_tests() {
        assertEquals(modelManager.getAutoSortFlag(), false);
        modelManager.setAutoSortFlag(true);
        assertEquals(modelManager.getAutoSortFlag(), true);
    }

    @Test
    public void equals() {
        Menu menu = new MenuBuilder().withfood(CARBONARA).withfood(TONKATSU_RAMEN).build();
        Menu differentMenu = new Menu();
        UserPrefs userPrefs = new UserPrefs();
        UserRecommendations userRecs = new UserRecommendations();
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        CustomSorter customSorter = new CustomSorter();

        // same values -> returns true
        modelManager = new ModelManager(menu, userPrefs, userRecs, purchaseHistory, customSorter);
        ModelManager modelManagerCopy = new ModelManager(menu, userPrefs, userRecs, purchaseHistory, customSorter);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMenu, userPrefs, userRecs, purchaseHistory,
                customSorter)));

        // different filteredList -> returns false
        String[] keywords = CARBONARA.getName().fullName.split("\\s+");
        modelManager.updateFilteredFoodList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(menu, userPrefs, userRecs, purchaseHistory, customSorter)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFoodList(PREDICATE_SHOW_ALL_FOOD);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMenuFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(menu, differentUserPrefs, userRecs, purchaseHistory,
                customSorter)));
    }
}
