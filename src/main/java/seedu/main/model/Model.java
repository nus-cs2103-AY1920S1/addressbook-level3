package seedu.main.model;

import seedu.address.model.AddressBookModel;

/**
 * The API of the AddressBookModel component.
 */
public interface Model {

    AddressBookModel getAddressBookModel();

    MainModel getMainModel();
}
