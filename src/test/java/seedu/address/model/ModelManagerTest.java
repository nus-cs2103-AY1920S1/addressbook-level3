package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.travezy.commons.core.GuiSettings;
import seedu.travezy.address.model.AddressBook;
import seedu.travezy.address.model.AddressBookModel;
import seedu.travezy.address.model.AddressBookModelManager;
import seedu.travezy.model.*;

public class ModelManagerTest {
    private UserPrefsModel userPrefsModel = new UserPrefsModelManager();
    private AddressBookModel addressBookModel = new AddressBookModelManager();
    private Model modelManager = new ModelManager(userPrefsModel, addressBookModel);

    @Test
    public void constructor() {
        assertEquals(addressBookModel, modelManager.getAddressBookModel());
        assertEquals(userPrefsModel, modelManager.getUserPrefsModel());
    }

    @Test
    public void getAddressBookModel_throwsNullPointerException() {
        assertEquals(null, new ModelManager(userPrefsModel, null).getAddressBookModel());
    }

    @Test
    public void getMainBookModel_throwsNullPointerException() {
        assertEquals(null, new ModelManager(null, addressBookModel).getUserPrefsModel());
    }

    @Test
    public void getAddressBookModel_equals() {
        assertEquals(modelManager.getAddressBookModel(), new AddressBookModelManager());
    }

    @Test
    public void getMainModel_equals() {
        assertEquals(modelManager.getUserPrefsModel(), new UserPrefsModelManager());
    }

    @Test
    public void getAddressBookModel_notEquals() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(
                new GuiSettings(2.0, 3.0, 5, 6));
        assertNotEquals(modelManager.getAddressBookModel(), new AddressBookModelManager(new AddressBook(), userPrefs));
    }

    @Test
    public void getMainModel_notEquals() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(
                new GuiSettings(2.0, 3.0, 5, 6));
        assertNotEquals(modelManager.getUserPrefsModel(), new UserPrefsModelManager(userPrefs));
    }

}
