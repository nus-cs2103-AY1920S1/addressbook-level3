package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.menu.Menu;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.savings.SavingsHistory;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.model.wallet.exceptions.BudgetAmountOutOfBoundsException;
import seedu.savenus.model.wallet.exceptions.BudgetDurationOutOfBoundsException;

/**
 * Clears all food items from our $aveNUS menu.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Your menu, purchase history, savings account, wallet, "
            + "likes, dislikes and aliases have been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setMenu(new Menu());
        model.setPurchaseHistory(new PurchaseHistory());
        model.setSavingsHistory(new SavingsHistory());
        model.setAliasList(new AliasList());
        try {
            model.setWallet(new Wallet());
        } catch (BudgetDurationOutOfBoundsException | BudgetAmountOutOfBoundsException e) {
            throw new CommandException(e.getMessage());
        }
        model.clearLikes();
        model.clearDislikes();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
