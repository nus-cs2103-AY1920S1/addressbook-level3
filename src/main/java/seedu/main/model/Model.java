package seedu.main.model;

import java.util.function.Supplier;

import seedu.achievements.model.StatisticsModel;
import seedu.address.model.AddressBookModel;

/**
 * The API of the AddressBookModel component.
 */
public interface Model {

    AddressBookModel getAddressBookModel();

    UserPrefsModel getUserPrefsModel();

    Supplier<StatisticsModel> statisticsModelSupplier();
}
