package seedu.address.ui.home;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.display.timeslots.PersonSchedule;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.ui.UiPart;
import seedu.address.ui.util.Calendar;

/**
 * A class to show our projet logo when the application starts up.
 */
public class DefaultStartView extends UiPart<Region> {

    private static final String FXML = "DefaultStartView.fxml";

    @FXML
    private Label defaultTitle;

    @FXML
    private GridPane defaultStartViewContainer;

    @FXML
    private StackPane calendarPlaceHolder;

    @FXML
    private StackPane noticeBoardPlaceHolder;

    public DefaultStartView(PersonSchedule personSchedule) {
        super(FXML);

        ArrayList<PersonTimeslot> personTimeslots = personSchedule.getScheduleDisplay()
                .get(0)
                .get(LocalDate.now().getDayOfWeek());

        PersonDisplay personDisplay = personSchedule.getPersonDisplay();
        defaultTitle.setText(String.format("Welcome %s!", personDisplay.getName()));

        Calendar calendar = new Calendar(LocalDate.now());
        calendarPlaceHolder.getChildren().add(calendar.getRoot());
        UpcomingScheduleList upcomingScheduleList = new UpcomingScheduleList(LocalDate.now(), personTimeslots);
        noticeBoardPlaceHolder.getChildren().add(upcomingScheduleList.getRoot());
    }
}
