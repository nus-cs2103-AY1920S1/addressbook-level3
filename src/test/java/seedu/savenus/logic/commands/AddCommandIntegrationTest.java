package seedu.savenus.logic.commands;

import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import seedu.savenus.testutil.FoodBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
    }

    @Test
    public void execute_newfood_success() {
        Food validFood = new FoodBuilder().build();

        Model expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        expectedModel.addFood(validFood);

        assertCommandSuccess(new AddCommand(validFood), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validFood), expectedModel);
    }

    @Test
    public void execute_duplicatefood_throwsCommandException() {
        Food foodInList = model.getMenu().getFoodList().get(0);
        assertCommandFailure(new AddCommand(foodInList), model, AddCommand.MESSAGE_DUPLICATE_FOOD);
    }

}
