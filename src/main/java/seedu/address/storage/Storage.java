package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.UserPrefs;
import seedu.address.storage.shoppinglist.ShoppingListStorage;
import seedu.address.storage.wastelist.WasteListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, TemplateListStorage, WasteListStorage,
        ShoppingListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getTemplateListFilePath();

    @Override
    Optional<ReadOnlyTemplateList> readTemplateList() throws DataConversionException, IOException;

    @Override
    void saveTemplateList(ReadOnlyTemplateList templateList) throws IOException;

    @Override
    Path getWasteListFilePath();

    @Override
    Optional<ReadOnlyWasteList> readWasteList() throws DataConversionException, IOException;

    @Override
    void saveWasteList(ReadOnlyWasteList wasteList) throws IOException;

    @Override
    Path getShoppingListFilePath();

    @Override
    Optional<ReadOnlyShoppingList> readShoppingList() throws DataConversionException, IOException;

    @Override
    void saveShoppingList(ReadOnlyShoppingList shoppingList) throws IOException;

}
