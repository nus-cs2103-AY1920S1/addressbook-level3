package seedu.travezy.model;

import java.util.function.Supplier;

import seedu.travezy.achievements.model.StatisticsModel;
import seedu.travezy.address.model.AddressBookModel;

/**
 * The API of the AddressBookModel component.
 */
public interface Model {

    AddressBookModel getAddressBookModel();

    UserPrefsModel getUserPrefsModel();

    Supplier<StatisticsModel> statisticsModelSupplier();
}
