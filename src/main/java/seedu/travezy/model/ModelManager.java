package seedu.travezy.model;

import java.util.function.Supplier;
import java.util.logging.Logger;

import seedu.travezy.achievements.model.StatisticsModel;
import seedu.travezy.achievements.model.StatisticsModelManager;
import seedu.travezy.commons.core.LogsCenter;
import seedu.travezy.address.model.AddressBookModel;

/**
 * Represents the in-memory addressBookModel of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    //main model used to save GUI settings and get user preferences
    private UserPrefsModel userPrefsModel;
    private AddressBookModel addressBookModel;

    public ModelManager(UserPrefsModel userPrefsModel, AddressBookModel addressBookModel) {
        this.userPrefsModel = userPrefsModel;
        this.addressBookModel = addressBookModel;
    }

    @Override
    public AddressBookModel getAddressBookModel() {
        return addressBookModel;
    }

    @Override
    public UserPrefsModel getUserPrefsModel() {
        return userPrefsModel;
    }

    @Override
    public Supplier<StatisticsModel> statisticsModelSupplier() {
        return new Supplier<StatisticsModel>() {
            @Override
            public StatisticsModel get() {
                return new StatisticsModelManager(addressBookModel.getFilteredPersonList());
            }
        };
    }

}
