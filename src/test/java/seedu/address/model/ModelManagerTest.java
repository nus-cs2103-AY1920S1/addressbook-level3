package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.BENSON;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.category.Category;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.LendMoney;
import seedu.address.model.transaction.TransactionPredicate;
import seedu.address.model.transaction.UniqueBudgetList;
import seedu.address.model.util.Date;
import seedu.address.testutil.BankOperationBuilder;
import seedu.address.testutil.UserStateBuilder;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();


    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new VersionedUserState(new UserState()), modelManager.getUserState());
        assertEquals(new BankAccount(), new BankAccount(modelManager.getBankAccount()));
    }

    @Test
    public void setUserState() {
        UserState userState = new UserState();
        VersionedUserState versionedUserState = new VersionedUserState(userState);

        BankAccountOperation op = new BankOperationBuilder()
            .withDescription("milk")
            .withAmount("69")
            .withDate("19112019")
            .withCategories("Uncategorised")
            .build();

        versionedUserState.add(op);

        modelManager.setUserState(versionedUserState);

        VersionedUserState expectedVersionedUserState = new VersionedUserState(new UserState());
        expectedVersionedUserState.add(op);

        VersionedUserState actualVersionedUserState = (VersionedUserState) modelManager.getUserState();
        assertEquals(expectedVersionedUserState, actualVersionedUserState);
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setUserStateFilePath(Paths.get("bank/account/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setUserStateFilePath(Paths.get("new/bank/account/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setBankAccountFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserStateFilePath(null));
    }

    @Test
    public void setBankAccountFilePath_validPath_setsBankAccountFilePath() {
        Path path = Paths.get("bank/account/file/path");
        modelManager.setUserStateFilePath(path);
        assertEquals(path, modelManager.getUserStateFilePath());
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.has((BankAccountOperation) null));
    }

    @Test
    public void hasTransaction_transactionNotInBankAccount_returnsFalse() {
        assertFalse(modelManager.has(ALICE));
    }

    @Test
    public void hasTransaction_transactionInBankAccount_returnsTrue() {
        modelManager.add(ALICE);
        assertTrue(modelManager.has(ALICE));
    }

    @Test
    public void hasBudget_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.has((Budget) null));
    }

    @Test
    public void hasBudget_budgetNotInBankAccount_returnsFalse() {
        Budget falseBudget = new Budget(new Amount(700), new Date("19112019"));
        assertFalse(modelManager.has(falseBudget));
    }

    @Test
    public void hasBudget_budgetInBankAccount_returnsTrue() {
        Budget budget = new Budget(new Amount(700), new Date("19112019"));
        modelManager.add(budget);
        assertTrue(modelManager.has(budget));
    }

    @Test
    public void hasLedgerOperation_nullLedgerOperation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.has((LedgerOperation) null));
    }

    @Test
    public void hasLedgerOperation_ledgerOperationNotInUserState_returnsFalse() {
        LedgerOperation falseLedger = new LendMoney(
            new Person(new Name("John")), new Amount(700),
            new Date("19112019"), new Description("loanshark"));
        assertFalse(modelManager.has(falseLedger));
    }

    @Test
    public void hasLedgerOperation_ledgerOperationInUserState_returnsTrue() {
        LedgerOperation ledger = new LendMoney(
            new Person(new Name("John")), new Amount(700),
            new Date("19112019"), new Description("loanshark"));
        modelManager.add(ledger);
        assertTrue(modelManager.has(ledger));
    }

    @Test
    public void hasProjection_nullProjection_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.has((Projection) null));
    }

    @Test
    public void hasProjection_projectionNotInUserState_returnsFalse() {
        Model falseModel = new ModelManager();
        falseModel.setTransactions(getTypicalTransactions());
        Projection projection = new Projection(falseModel.getFilteredTransactionList(), new Date("19112019"),
            new UniqueBudgetList().asUnmodifiableObservableList());
        assertFalse(modelManager.has(projection));
    }

    @Test
    public void hasProjection_projectionInUserState_returnsTrue() {
        Model stubModel = new ModelManager();
        stubModel.setTransactions(getTypicalTransactions());
        Projection projection = new Projection(stubModel.getFilteredTransactionList(), new Date("19112019"),
            new UniqueBudgetList().asUnmodifiableObservableList());
        modelManager.add(projection);
        assertTrue(modelManager.has(projection));
    }

    @Test
    public void deleteTransaction_transactionInBankAccount_returnsTrue() {
        modelManager.add(ALICE);
        assertTrue(modelManager.has(ALICE));

        modelManager.delete(ALICE);
        assertFalse(modelManager.has(ALICE));
    }

    @Test
    public void deleteBudget_budgetInBankAccount_returnsTrue() {
        Budget budget = new Budget(new Amount(700), new Date("19112019"));
        modelManager.add(budget);
        assertTrue(modelManager.has(budget));

        modelManager.delete(budget);
        assertFalse(modelManager.has(budget));
    }

    @Test
    public void deleteProjection_projectionInUserState_returnsTrue() {
        Model stubModel = new ModelManager();
        stubModel.setTransactions(getTypicalTransactions());
        Projection projection = new Projection(stubModel.getFilteredTransactionList(), new Date("19112019"),
            new UniqueBudgetList().asUnmodifiableObservableList());
        modelManager.add(projection);
        assertTrue(modelManager.has(projection));

        modelManager.delete(projection);
        assertFalse(modelManager.has(projection));
    }


    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredTransactionList().remove(0));
    }

    @Test
    public void getFilteredBudgetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredBudgetList().remove(0));
    }

    @Test
    public void getFilteredLedgerOperationsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredLedgerOperationsList().remove(0));
    }

    @Test
    public void getFilteredProjectionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredProjectionsList().remove(0));
    }

    @Test
    public void canUndo_noUndo_returnsFalse() {
        assertFalse(modelManager.canUndoUserState());
    }

    @Test
    public void canUndo_gotUndo_returnsFalse() {
        modelManager.commitUserState();
        assertTrue(modelManager.canUndoUserState());
    }

    @Test
    public void canRedo_noRedo_returnsFalse() {
        assertFalse(modelManager.canRedoUserState());
    }

    @Test
    public void canRedo_gotRedo_returnsFalse() {
        modelManager.commitUserState();
        modelManager.undoUserState();
        assertTrue(modelManager.canRedoUserState());
    }

    @Test
    public void equals() {
        UserState userState = new UserStateBuilder().withTransaction(ALICE).withTransaction(BENSON).build();
        UserState differentUserState = new UserState();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(userState, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(userState, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different bankAccount -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentUserState, userPrefs)));


        // different filteredList -> returns false
        Optional<Set<Category>> categories = Optional.of(ALICE
            .getCategories());
        modelManager.updateFilteredTransactionList(new TransactionPredicate(categories,
            Optional.empty(), Optional.empty(), Optional.empty()));
        assertFalse(modelManager.equals(new ModelManager(userState, userPrefs)));


        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setUserStateFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(userState, differentUserPrefs)));
    }
}
