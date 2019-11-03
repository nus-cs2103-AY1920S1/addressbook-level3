package seedu.address.achievements.model;

import javafx.scene.chart.XYChart;
import seedu.address.address.model.AddressBookStatisticsModel;

/**
 * Represents the in-memory statisticsModel.
 */
public class StatisticsModelManager implements StatisticsModel {

    private final AddressBookStatisticsModel addressBookStatisticsModel;

    public StatisticsModelManager(AddressBookStatisticsModel addressBookStatisticsModel) {
        this.addressBookStatisticsModel = addressBookStatisticsModel;
    }

    @Override
    public int getTotalPersons() {
        return addressBookStatisticsModel.getTotalPersons();
    }

    @Override
    public XYChart.Series<Integer, String> getAddressChartData() {
        return addressBookStatisticsModel.getAddressChartData();
    }
}
