package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BankAccount} that keeps track of its own history.
 */
public class VersionedBankAccount extends BankAccount {

    private final List<ReadOnlyBankAccount> bankAccountStateList;
    private int currentStatePointer;

    public VersionedBankAccount(ReadOnlyBankAccount initialState) {
        super(initialState);

        bankAccountStateList = new ArrayList<>();
        bankAccountStateList.add(new BankAccount(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code BankAccount} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        bankAccountStateList.add(new BankAccount(this));
        currentStatePointer++;
        // indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        bankAccountStateList.subList(currentStatePointer + 1, bankAccountStateList.size()).clear();
    }

    /**
     * Restores the bank account to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(bankAccountStateList.get(currentStatePointer));
    }

    /**
     * Restores the bank account to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(bankAccountStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has bank account states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has bank account states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < bankAccountStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedBankAccount)) {
            return false;
        }

        VersionedBankAccount otherVersionedBankAccount = (VersionedBankAccount) other;

        // state check
        return super.equals(otherVersionedBankAccount)
                && bankAccountStateList.equals(otherVersionedBankAccount.bankAccountStateList)
                && currentStatePointer == otherVersionedBankAccount.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of bankAccountState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of bankAccountState list, unable to redo.");
        }
    }
}
