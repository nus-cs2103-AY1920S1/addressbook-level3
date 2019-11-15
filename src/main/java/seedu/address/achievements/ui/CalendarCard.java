package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.calendar.model.util.CalendarStatistics;
import seedu.address.calendar.model.util.exceptions.NoVacationException;
import seedu.address.commons.core.LogsCenter;

/**
 * An UI component that displays the various achievements in a card.
 */
public class CalendarCard {

    private static final Logger logger = LogsCenter.getLogger(CalendarCard.class);
    /**
     * Card for {@link CalendarStatistics}.
     * @param calendarStatistics
     * @return observable list of nodes that makes up the {@code CalendarCard}
     */
    public static ObservableList<Node> make(CalendarStatistics calendarStatistics) {
        ObservableList<Node> observableList = FXCollections.observableArrayList(
                new AchievementsTitleLabel("Calendar").getRoot(),
                new AchievementsDataLabel("Total Number of Trip Days: ", ""
                        + calendarStatistics.getNumDaysTrip()).getRoot(),
                new AchievementsDataLabel("Total Number of Days Of Vacation: ", ""
                        + calendarStatistics.getNumDaysVacation()).getRoot(),
                new AchievementsDataLabel("Total Number of Trips: ", ""
                        + calendarStatistics.getNumTrip()).getRoot());
        try {
            double getPercentageTrip = calendarStatistics.getPercentageTrip();
            observableList.add(
                    new AchievementsDataLabel("Percentage of Vacation Days spent on Trips",
                            calendarStatistics.getNumDaysTrip() + " / "
                                    + calendarStatistics.getNumDaysVacation()).getRoot());
            observableList.add(new AchievementsProgressBar(calendarStatistics.getPercentageTrip()).getRoot());
        } catch (NoVacationException nve) {
            logger.info("No vactation days. Progress bar for calendar not loaded.");
        }
        return observableList;
    }
}
