package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.calendar.model.util.CalendarStatistics;
import seedu.address.commons.core.LogsCenter;

/**
 * An UI component that displays the various achievements in a card.
 */
public class CalendarCard {

    private final Logger logger = LogsCenter.getLogger(getClass());

    public static ObservableList<Node> make(CalendarStatistics calendarStatistics) {
        return FXCollections.observableArrayList(
                new AchievementsTitleLabel("Calendar").getRoot(),
                new AchievementsDataLabel("Total Number of Trip Days: ", ""
                        + calendarStatistics.getNumDaysTrip()).getRoot(),
                new AchievementsDataLabel("Total Number of Days Of Vacation: ", ""
                        + calendarStatistics.getNumDaysVacation()).getRoot(),
                new AchievementsDataLabel("Total Number of Trips: ", ""
                        + calendarStatistics.getNumTrip()).getRoot(),
                new AchievementsDataLabel("Percentage of Vacation Days spent on Trips", "").getRoot(),
                new AchievementsProgressBar(calendarStatistics.getPercentageTrip()).getRoot());
    }
}
