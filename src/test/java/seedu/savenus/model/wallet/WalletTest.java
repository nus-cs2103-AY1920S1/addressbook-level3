package seedu.savenus.model.wallet;

import org.junit.jupiter.api.Test;
import seedu.savenus.commons.core.Messages;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.food.Price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

public class WalletTest {

    public static final Wallet emptyWallet = new Wallet("0" , "10");
    public static final Wallet richWallet = new Wallet("300", "10");
    public static final Price validPrice = new Price("2.50");

    @Test
    public void pay_validPriceEnoughFunds_success() {
        Wallet modelWallet = new Wallet(richWallet.getRemainingBudget(), richWallet.getDaysToExpire());
        try {
            modelWallet.pay(validPrice);
        } catch (Exception e) {}
        assertEquals(modelWallet, new Wallet("297.50", "10"));
    }

    @Test
    public void pay_invalidPriceEnoughFunds_throwsIllegalArgumentException() {
        Wallet modelWallet = new Wallet(richWallet.getRemainingBudget(), richWallet.getDaysToExpire());
        assertThrows(IllegalArgumentException.class, () -> modelWallet.pay(new Price("abc")));
    }

    @Test
    public void pay_validPriceNotEnoughFunds_throwsCommandException() {
        Wallet modelWallet = new Wallet(emptyWallet.getRemainingBudget(), emptyWallet.getDaysToExpire());
        assertThrows(CommandException.class, Messages.MESSAGE_INSUFFICIENT_FUNDS, () -> modelWallet.pay(validPrice));
    }

}
