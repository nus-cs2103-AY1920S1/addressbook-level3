package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;

/**
 * A class to handle the details view of a person or a group.
 */
public class PersonDetailsView extends UiPart<Region> {

    private static final String FXML = "PersonDetailsView.fxml";

    @FXML
    private StackPane personSchedule;

    @FXML
    private StackPane personDetailContainer;

    public PersonDetailsView(ScheduleWindowDisplay scheduleWindowDisplay) {
        super(FXML);
        //WeekSchedule schedule = scheduleWindowDisplay.getMonthSchedules().get(0).getWeekScheduleOf(0);
        //ScheduleView sv = new ScheduleView(schedule, LocalDate.now());
        //this.personSchedule.getChildren().add(sv.getRoot());
    }

}
