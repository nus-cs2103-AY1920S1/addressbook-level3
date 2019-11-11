package seedu.address.model.exchangedata;

import seedu.address.model.util.SampleDataUtil;

/**
 * ExchangeDataSingleton is the single source of truth for all currency conversions in MyMorise.
 *
 * @code ExchangeDataStorage is not accessible from the Expense Object, thus this singleton acts
 * as the intermediary to allow access to rates for conversion.
 * <p>
 * NOTE:
 * Ideally, ExchangeStorage can be refactored out in this trivial application, however is left in place
 * in case backdated currency conversions is planned.
 * <p>
 * Supposing this evolves into an internet dependant application, the storage of old forex rates will be
 * redundant as they would be accessible from the endpoint MyMorise presently retrieves data from upon startup.
 */
public class ExchangeDataSingleton {

    private static ExchangeData instance = null;

    public static ExchangeData getInstance() {
        if (instance == null) {
            instance = new ExchangeData(SampleDataUtil.getSampleExchangeData());
        }
        return instance;
    }

    /**
     * Updates the Singleton instance with the updated forex data.
     * @param newExchangeData The new ExchangeData to replace the Singleton with.
     * @return returns a reference to the new Singleton Instance.
     */
    public static ExchangeData updateInstance(ExchangeData newExchangeData) {
        instance = new ExchangeData(newExchangeData);
        return instance;
    }
}
