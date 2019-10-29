package seedu.address.model;

import java.util.function.Supplier;

import seedu.address.achievements.model.StatisticsModel;
import seedu.address.address.model.AddressBookModel;

/**
 * The API of the AddressBookModel component.
 */
public interface Model {

    AddressBookModel getAddressBookModel();

    UserPrefsModel getUserPrefsModel();

    Supplier<StatisticsModel> statisticsModelSupplier();
}
