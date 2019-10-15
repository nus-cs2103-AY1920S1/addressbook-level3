package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;


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
        userPrefs.setBankAccountFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setBankAccountFilePath(Paths.get("new/address/book/file/path"));
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
        assertThrows(NullPointerException.class, () -> modelManager.setBankAccountFilePath(null));
    }

    @Test
    public void setBankAccountFilePath_validPath_setsBankAccountFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setBankAccountFilePath(path);
        assertEquals(path, modelManager.getBankAccountFilePath());
    }

    /*
    @Test
    public void hasTransaction_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInBankAccount_returnsFalse() {
        assertFalse(modelManager.hasTransaction(ALICE));
    }

    @Test
    public void hasTransaction_transactionInBankAccount_returnsTrue() {
        modelManager.addTransaction(ALICE);
        assertTrue(modelManager.hasTransaction(ALICE));
    }
     */

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
            .getFilteredTransactionList().remove(0));
    }

    /*
    @Test
    public void equals() {
        BankAccount bankAccount = new BankAccountBuilder().withTransaction(ALICE).withTransaction(BENSON).build();
        BankAccount differentBankAccount = new BankAccount();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(bankAccount, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(bankAccount, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different bankAccount -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentBankAccount, userPrefs)));


        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredTransactionList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(bankAccount, userPrefs)));


        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBankAccountFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(bankAccount, differentUserPrefs)));
    }
     */
}
