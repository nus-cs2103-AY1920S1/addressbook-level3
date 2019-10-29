package seedu.address.ui.util;

import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.display.schedulewindow.DayTimeslot;
import seedu.address.ui.UiPart;

/**
 * Ui Component to show the upcoming schedules for the user.
 */
public class UpcomingScheduleList extends UiPart<Region> {
    private static final String FXML = "UpcomingScheduleList.fxml";

    @FXML
    private ScrollPane upcomingScheduleContainer;
    @FXML
    private Label upcomingScheduleHeader;
    @FXML
    private VBox scheduleCards;

    private LocalDate date;
    private List<DayTimeslot> eventsToday;

    public UpcomingScheduleList(LocalDate date, List<DayTimeslot> eventsToday) {
        super(FXML);
        this.eventsToday = eventsToday;
        this.date = date;
        String upcomingScheduleTitle = date.getDayOfMonth() + " " + date.getMonth().toString().substring(0, 3);
        this.upcomingScheduleHeader.setText(upcomingScheduleTitle);
        initialiseContent();
    }

    /**
     * Method to initialise contents in the container of the list.
     */
    public void initialiseContent() {
        for (DayTimeslot t : eventsToday) {
            ScheduleDisplayCard scheduleDisplayCard = new ScheduleDisplayCard(t);
            scheduleCards.getChildren().add(scheduleDisplayCard.getRoot());
        }
    }
}
