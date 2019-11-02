package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.transaction.TransactionContainsCategoriesPredicate;
import seedu.address.testutil.UserStateBuilder;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();


    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new BankAccount(), new BankAccount(modelManager.getBankAccount()));
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
        assertThrows(NullPointerException.class, () -> modelManager.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInBankAccount_returnsFalse() {
        assertFalse(modelManager.hasTransaction(ALICE));
    }

    @Test
    public void hasTransaction_transactionInBankAccount_returnsTrue() {
        modelManager.addOperation(ALICE);
        assertTrue(modelManager.hasTransaction(ALICE));
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredTransactionList().remove(0));
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
        final List<String> categories = ALICE
            .getCategories()
            .stream()
            .map(category -> category.getCategoryName())
            .collect(Collectors.toList());
        modelManager.updateFilteredTransactionList(new TransactionContainsCategoriesPredicate(categories));
        assertFalse(modelManager.equals(new ModelManager(userState, userPrefs)));


        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setUserStateFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(userState, differentUserPrefs)));
    }
}
