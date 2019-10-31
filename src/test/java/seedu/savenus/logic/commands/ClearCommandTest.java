package seedu.savenus.logic.commands;

import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.menu.Menu;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

public class ClearCommandTest {

    @Test
    public void execute_emptyMenu_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMenu_success() {
        Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        Model expectedModel = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        expectedModel.setMenu(new Menu());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
