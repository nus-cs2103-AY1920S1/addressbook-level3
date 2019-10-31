package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
            new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
    private List<Index> indexes;
    private List<Index> secondindexes;

    @BeforeEach
    public void setUp() {
        indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_FOOD);

        secondindexes = new ArrayList<>();
        secondindexes.add(INDEX_SECOND_FOOD);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Food foodToDelete = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(indexes);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        ModelManager expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        expectedModel.deleteFood(foodToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodList().size() + 1);
        indexes.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexes);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_infiniteIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(Integer.MAX_VALUE);
        indexes.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexes);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Food foodToDelete = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(indexes);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);
        Model expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        expectedModel.deleteFood(foodToDelete);
        showNoFood(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Index outOfBoundIndex = INDEX_SECOND_FOOD;
        indexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMenu().getFoodList().size());

        DeleteCommand deleteCommand = new DeleteCommand(indexes);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(indexes);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondindexes);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(indexes);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different food -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoFood(Model model) {
        model.updateFilteredFoodList(p -> false);

        assertTrue(model.getFilteredFoodList().isEmpty());
    }
}
