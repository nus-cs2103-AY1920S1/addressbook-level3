package seedu.address.model;

import seedu.address.address.model.AddressBookModel;

/**
 * The API of the AddressBookModel component.
 */
public interface Model {

    AddressBookModel getAddressBookModel();

    UserPrefsModel getUserPrefsModel();
}
