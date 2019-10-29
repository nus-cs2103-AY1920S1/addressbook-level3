package seedu.address.ui.util;

import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.ui.UiPart;

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

    public DefaultStartView(List<PersonTimeslot> eventsToday) {
        super(FXML);
        defaultTitle.setText("WELCOME TO TIMEBOOK!");
        Calendar calendar = new Calendar(LocalDate.now());
        calendarPlaceHolder.getChildren().add(calendar.getRoot());
        UpcomingScheduleList upcomingScheduleList = new UpcomingScheduleList(LocalDate.now(), eventsToday);
        noticeBoardPlaceHolder.getChildren().add(upcomingScheduleList.getRoot());
    }
}
