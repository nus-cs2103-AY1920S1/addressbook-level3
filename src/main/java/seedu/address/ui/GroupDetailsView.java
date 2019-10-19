package seedu.address.ui;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;

/**
 * A class to handle the details view of a person or a group.
 */
public class GroupDetailsView extends UiPart<Region> {

    private static final String FXML = "GroupDetailsView.fxml";

    @FXML
    private StackPane groupSchedule;

    public GroupDetailsView(DetailWindowDisplay detailWindowDisplay, ArrayList<String> colors) {
        super(FXML);
        ScheduleView scheduleView = new ScheduleView(detailWindowDisplay.getWeekSchedules(), colors);
        groupSchedule.getChildren().add(scheduleView.getRoot());
    }

}
