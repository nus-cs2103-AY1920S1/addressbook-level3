package seedu.savenus.logic.commands;

import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
