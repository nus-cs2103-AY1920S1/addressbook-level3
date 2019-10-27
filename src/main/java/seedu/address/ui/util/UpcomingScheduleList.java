package seedu.address.ui.util;

import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.ui.UiPart;

public class UpcomingScheduleList extends UiPart<Region> {
    private static final String FXML = "UpcomingScheduleList.fxml";

    @FXML
    private ScrollPane upcomingScheduleContainer;
    @FXML
    private Label upcomingScheduleHeader;
    @FXML
    private VBox scheduleCards;

    private LocalDate date;
    private List<Event> eventsToday;

    public UpcomingScheduleList(LocalDate date, List<Event> eventsToday) {
        super(FXML);
        this.eventsToday = eventsToday;
        this.date = date;
        String upcomingScheduleTitle = date.getDayOfMonth() + " " + date.getMonth().toString().substring(0, 3);
        this.upcomingScheduleHeader.setText(upcomingScheduleTitle);
        initialiseContent();
    }

    public void initialiseContent() {
        for (int i = 0; i < eventsToday.size(); i++) {
            Event event = eventsToday.get(i);
            for (Timeslot t : event.getTimeslots()) {
                ScheduleDisplayCard scheduleDisplayCard = new ScheduleDisplayCard(event.getEventName(), t);
                scheduleCards.getChildren().add(scheduleDisplayCard.getRoot());
            }
        }
    }
}
