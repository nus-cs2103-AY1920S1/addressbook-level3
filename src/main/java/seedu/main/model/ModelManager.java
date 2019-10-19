package seedu.main.model;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBookModel;

/**
 * Represents the in-memory addressBookModel of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private MainModel mainModel;
    private AddressBookModel addressBookModel;

    public ModelManager(MainModel mainModel, AddressBookModel addressBookModel) {
        this.mainModel = mainModel;
        this.addressBookModel = addressBookModel;

    }

    @Override
    public AddressBookModel getAddressBookModel() {
        return addressBookModel;
    }

    @Override
    public MainModel getMainModel() {
        return mainModel;
    }
}
