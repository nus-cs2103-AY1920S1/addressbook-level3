package seedu.address.model;

/**
 * Unmodifiable view of an user state.
 */
public interface ReadOnlyUserState {

    ReadOnlyBankAccount getBankAccount();

    ReadOnlyLedger getLedger();
}
