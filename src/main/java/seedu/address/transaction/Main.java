package seedu.address.transaction;

import java.util.Optional;
import seedu.address.person.commons.core.Config;
import seedu.address.person.model.AddressBook;
import seedu.address.person.model.ReadOnlyAddressBook;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.util.SampleDataUtil;
import seedu.address.person.storage.AddressBookStorage;
import seedu.address.person.storage.JsonAddressBookStorage;
import seedu.address.person.storage.JsonUserPrefsStorage;
import seedu.address.person.storage.UserPrefsStorage;
import seedu.address.transaction.logic.LogicManager;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        StorageManager storage = new StorageManager("data/transactionHistory.txt");
        ModelManager mm = new ModelManager(storage);

        //no config for ui yet
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(new Config().getUserPrefsFilePath());
        UserPrefs userPrefs = new UserPrefs();
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        seedu.address.person.storage.StorageManager personStorage =
                new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);

        ReadOnlyAddressBook initialData;
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = personStorage.readAddressBook();
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);

        } catch (Exception e) {

            initialData = new AddressBook();
        }
        seedu.address.person.model.ModelManager personMM =
                new seedu.address.person.model.ModelManager(initialData, userPrefs);
        LogicManager lm = new LogicManager(mm, storage, personMM, personStorage);

        MainWindow mw = new MainWindow(lm);

        mw.show();
    }
}
