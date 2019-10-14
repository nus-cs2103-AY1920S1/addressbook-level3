package seedu.savenus.model.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.food.Price;

public class WalletTest {

    public static final Wallet EMPTYWALLET = new Wallet("0" , "10");
    public static final Wallet RICHWALLET = new Wallet("300", "10");
    public static final Price VALIDPRICE = new Price("2.50");

    @Test
    public void pay_validPriceEnoughFunds_success() {
        Wallet modelWallet = new Wallet(RICHWALLET.getRemainingBudget(), RICHWALLET.getDaysToExpire());
        try {
            modelWallet.pay(VALIDPRICE);
        } catch (Exception e) {
            return;
        }
        assertEquals(modelWallet, new Wallet("297.50", "10"));
    }

    @Test
    public void pay_invalidPriceEnoughFunds_throwsIllegalArgumentException() {
        Wallet modelWallet = new Wallet(RICHWALLET.getRemainingBudget(), RICHWALLET.getDaysToExpire());
        assertThrows(IllegalArgumentException.class, () -> modelWallet.pay(new Price("abc")));
    }

    @Test
    public void pay_validPriceNotEnoughFunds_throwsCommandException() {
        Wallet modelWallet = new Wallet(EMPTYWALLET.getRemainingBudget(), EMPTYWALLET.getDaysToExpire());
        assertThrows(CommandException.class, Messages.MESSAGE_INSUFFICIENT_FUNDS, () -> modelWallet.pay(VALIDPRICE));
    }

}
