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
import seedu.address.transaction.ui.Start;

//NO LONGER USED (USED FOR CLI, NO UI)
public class Main {
    public static void main(String[] args) {

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
        StorageManager storage = new StorageManager("data/transactionHistory.txt", personMM);
        ModelManager mm = new ModelManager(storage);
        LogicManager lm = new LogicManager(mm, storage, personMM, personStorage);

        Start mw = new Start(lm);

        mw.show();
    }
}
