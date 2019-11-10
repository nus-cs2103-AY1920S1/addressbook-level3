package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

/**
 * {@code UserState} that keeps track of its own history.
 */
public class VersionedUserState extends UserState {

    private final List<ReadOnlyUserState> userStateList;
    private int currentStatePointer;

    private final List<Predicate<? super BankAccountOperation>> transPredList;
    private final List<Predicate<? super Budget>> budgetPredList;
    private final List<Predicate<? super LedgerOperation>> ledgerPredList;
    private final List<Predicate<? super Projection>> projectionPredList;

    public VersionedUserState(ReadOnlyUserState initialState) {
        super(initialState);

        userStateList = new ArrayList<>();
        userStateList.add(new UserState(initialState));
        currentStatePointer = 0;

        transPredList = new ArrayList<>();
        budgetPredList = new ArrayList<>();
        ledgerPredList = new ArrayList<>();
        projectionPredList = new ArrayList<>();

        transPredList.add(unused -> true);
        budgetPredList.add(unused -> true);
        ledgerPredList.add(unused -> true);
        projectionPredList.add(unused -> true);
    }

    /**
     * Saves a copy of the current {@code UserState} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit(Predicate<? super BankAccountOperation> transPred,
                       Predicate<? super Budget> budgetPred,
                       Predicate<? super LedgerOperation> ledgerPred,
                       Predicate<? super Projection> projectionPred) {
        removeStatesAfterCurrentPointer();
        userStateList.add(new UserState(this));

        transPredList.add(transPred);
        budgetPredList.add(budgetPred);
        ledgerPredList.add(ledgerPred);
        projectionPredList.add(projectionPred);

        currentStatePointer++;
    }

    /**
     * Removes all undone states from the state list. Used only during a commit.
     */
    private void removeStatesAfterCurrentPointer() {
        userStateList.subList(currentStatePointer + 1, userStateList.size()).clear();

        transPredList.subList(currentStatePointer + 1, transPredList.size()).clear();
        budgetPredList.subList(currentStatePointer + 1, budgetPredList.size()).clear();
        ledgerPredList.subList(currentStatePointer + 1, ledgerPredList.size()).clear();
        projectionPredList.subList(currentStatePointer + 1, projectionPredList.size()).clear();
    }

    public Predicate<? super BankAccountOperation> getCurrentTransPred() {
        return transPredList.get(currentStatePointer);
    }

    public Predicate<? super Budget> getCurrentBudgetPred() {
        return budgetPredList.get(currentStatePointer);
    }

    public Predicate<? super LedgerOperation> getCurrentLedgerPred() {
        return ledgerPredList.get(currentStatePointer);
    }

    public Predicate<? super Projection> getCurrentProjectionPred() {
        return projectionPredList.get(currentStatePointer);
    }

    /**
     * Restores the user state to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(userStateList.get(currentStatePointer));
    }

    /**
     * Restores the user state to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(userStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has user state states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has user state states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < userStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedUserState)) {
            return false;
        }

        VersionedUserState otherVersionedUserState = (VersionedUserState) other;

        // state check
        return super.equals(otherVersionedUserState)
            && currentStatePointer == otherVersionedUserState.currentStatePointer
            && userStateList.equals(otherVersionedUserState.userStateList)
            && transPredList.equals(otherVersionedUserState.transPredList)
            && budgetPredList.equals(otherVersionedUserState.budgetPredList)
            && ledgerPredList.equals(otherVersionedUserState.ledgerPredList)
            && projectionPredList.equals(otherVersionedUserState.projectionPredList);
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of userState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of userState list, unable to redo.");
        }
    }
}
