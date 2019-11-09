package seedu.address.achievements.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.address.model.util.AddressBookStatistics;
import seedu.address.calendar.model.util.CalendarStatistics;
import seedu.address.diaryfeature.model.util.DiaryBookStatistics;
import seedu.address.financialtracker.model.util.FinancialTrackerStatistics;
import seedu.address.itinerary.model.util.ItineraryStatistics;


/**
 * Represents the in-memory statisticsModel.
 */
public class StatisticsModelManager implements StatisticsModel {

    private final AddressBookStatistics addressBookStatistics;
    private final CalendarStatistics calendarStatistics;
    private final DiaryBookStatistics diaryBookStatistics;
    private final FinancialTrackerStatistics financialTrackerStatistics;
    private final ItineraryStatistics itineraryStatistics;

    public StatisticsModelManager(AddressBookStatistics addressBookStatistics,
                                  CalendarStatistics calendarStatistics,
                                  DiaryBookStatistics diaryStatistics,
                                  FinancialTrackerStatistics financialTrackerStatistics,
                                  ItineraryStatistics itineraryStatistics) {
        this.addressBookStatistics = addressBookStatistics;
        this.diaryBookStatistics = diaryStatistics;
        this.calendarStatistics = calendarStatistics;
        this.financialTrackerStatistics = financialTrackerStatistics;
        this.itineraryStatistics = itineraryStatistics;
    }

    @Override
    public int getTotalPersons() {
        return addressBookStatistics.getTotalPersons();
    }

    @Override
    public XYChart.Series<Number, String> getAddressChartData() {
        return addressBookStatistics.getAddressChartData();
    }

    @Override
    public int getTotalDiaryEntries() {
        return diaryBookStatistics.getTotalDiaryEntries();
    }

    @Override
    public XYChart.Series<String, Number> getDiaryChartData() {
        return diaryBookStatistics.getDiaryChart();
    }

    @Override
    public ObservableList<PieChart.Data> getFinancialPieChartData() {
        return financialTrackerStatistics.getFinancialPieChartData();
    }

    @Override
    public XYChart.Series<String, Number> getFinancialBarChartData() {
        return financialTrackerStatistics.getFinancialBarChartData();
    }

    @Override
    public int getTotalItineraryEntries() {
        return itineraryStatistics.getTotalItineraryEntries();
    }

    @Override
    public XYChart.Series<String, Number> getItineraryLineChartData() {
        return itineraryStatistics.getItineraryLineChartData();
    }

    @Override
    public long getNumberOfDaysTrip() {
        return calendarStatistics.getNumDaysTrip();
    }

    @Override
    public long getNumberOfDaysVacation() {
        return calendarStatistics.getNumDaysVacation();
    }

    @Override
    public long getNumberOfTrip() {
        return calendarStatistics.getNumTrip();
    }

    @Override
    public double getPercentageTrip() {
        return calendarStatistics.getPercentageTrip();
    }
}
