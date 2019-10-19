package seedu.main.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.AddressBookModel;
import seedu.address.model.AddressBookModelManager;

public class ModelManagerTest {
    private MainModel mainModel = new MainModelManager();
    private AddressBookModel addressBookModel = new AddressBookModelManager();
    private Model modelManager = new ModelManager(mainModel, addressBookModel);

    @Test
    public void constructor() {
        assertEquals(addressBookModel, modelManager.getAddressBookModel());
        assertEquals(mainModel, modelManager.getMainModel());
    }

    @Test
    public void getAddressBookModel_throwsNullPointerException() {
        assertEquals(null, new ModelManager(mainModel, null).getAddressBookModel());
    }

    @Test
    public void getMainBookModel_throwsNullPointerException() {
        assertEquals(null, new ModelManager(null, addressBookModel).getMainModel());
    }

    @Test
    public void getAddressBookModel_equals() {
        assertEquals(modelManager.getAddressBookModel(), new AddressBookModelManager());
    }

    @Test
    public void getMainModel_equals() {
        assertEquals(modelManager.getMainModel(), new MainModelManager());
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
        assertNotEquals(modelManager.getMainModel(), new MainModelManager(userPrefs));
    }

}
