package seedu.savenus.testutil;

import seedu.savenus.model.wallet.Wallet;

//@@author raikonen
/**
 * A utility class containing a typical {@code Wallet} objects to be used in tests.
 */
public class TypicalWallet {

    private TypicalWallet() {} // prevents instantiation

    public static Wallet getTypicalWallet() {
        Wallet wallet = new Wallet("100", "30");
        return wallet;
    }
}
