package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.person.schedule.Event;
import seedu.address.ui.UiPart;

import java.time.LocalDate;
import java.util.List;

/**
 * A class to show our projet logo when the application starts up.
 */
public class DefaultStartView extends UiPart<Region> {

    private static final String FXML = "DefaultStartView.fxml";
    private final Image logo = new Image(getClass().getResourceAsStream("/images/timebook_logo.png"));

    @FXML
    private Label defaultTitle;

    @FXML
    private GridPane defaultStartViewContainer;

    @FXML
    private StackPane calendarPlaceHolder;

    @FXML
    private StackPane noticeBoardPlaceHolder;

    public DefaultStartView(List<Event> eventsToday) {
        super(FXML);
        defaultTitle.setText("WELCOME TO TIMEBOOK!");
        Calendar calendar = new Calendar(LocalDate.now());
        calendarPlaceHolder.getChildren().add(calendar.getRoot());
        UpcomingScheduleList upcomingScheduleList = new UpcomingScheduleList(LocalDate.now(), eventsToday);
        noticeBoardPlaceHolder.getChildren().add(upcomingScheduleList.getRoot());
    }
}
