package seedu.savenus.model.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.food.Price;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;

//@@author raikonen
public class WalletTest {

    public static final Wallet EMPTYWALLET = new Wallet("0" , "10");
    public static final Wallet RICHWALLET = new Wallet("300", "10");
    public static final Price VALIDPRICE = new Price("2.50");

    @Test
    public void deduct_validPriceEnoughFunds_success() {
        Wallet modelWallet = new Wallet(RICHWALLET.getRemainingBudget(), RICHWALLET.getDaysToExpire());
        try {
            modelWallet.deduct(VALIDPRICE);
        } catch (Exception e) {
            return;
        }
        assertEquals(modelWallet, new Wallet("297.50", "10"));
    }

    @Test
    public void deduct_invalidPriceEnoughFunds_throwsIllegalArgumentException() {
        Wallet modelWallet = new Wallet(RICHWALLET.getRemainingBudget(), RICHWALLET.getDaysToExpire());
        assertThrows(IllegalArgumentException.class, () -> modelWallet.deduct(new Price("abc")));
    }

    @Test
    public void deduct_validPriceNotEnoughFunds_throwsInsufficientFundsException() {
        Wallet modelWallet = new Wallet(EMPTYWALLET.getRemainingBudget(), EMPTYWALLET.getDaysToExpire());
        assertThrows(InsufficientFundsException.class,
                new InsufficientFundsException().getMessage(), () -> modelWallet.deduct(VALIDPRICE));
    }
}
