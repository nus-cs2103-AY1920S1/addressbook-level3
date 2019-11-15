package seedu.address.achievements.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.achievements.ui.AddressBookCard;
import seedu.address.achievements.ui.CalendarCard;
import seedu.address.achievements.ui.DiaryBookCard;
import seedu.address.achievements.ui.FinancialCard;
import seedu.address.achievements.ui.ItineraryCard;
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
    public ObservableList<Node> getStatisticsView() {
        return FXCollections.concat(AddressBookCard.make(addressBookStatistics),
                CalendarCard.make(calendarStatistics),
                DiaryBookCard.make(diaryBookStatistics),
                FinancialCard.make(financialTrackerStatistics),
                ItineraryCard.make(itineraryStatistics));
    }
}
