package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.Model;
import seedu.savenus.model.menu.Menu;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.wallet.Wallet;

/**
 * Clears all food items from our $aveNUS menu.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Your menu, purchase history, savings account, wallet, "
            + "likes and dislikes have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMenu(new Menu());
        model.setPurchaseHistory(new PurchaseHistory());
        model.setSavingsAccount(new SavingsAccount());
        model.setWallet(new Wallet());
        model.clearLikes();
        model.clearDislikes();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
