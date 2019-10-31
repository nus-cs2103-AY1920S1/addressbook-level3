package seedu.address.model;

public interface ReadOnlyUserState {

    ReadOnlyBankAccount getBankAccount();

    ReadOnlyLedger getLedger();
}
