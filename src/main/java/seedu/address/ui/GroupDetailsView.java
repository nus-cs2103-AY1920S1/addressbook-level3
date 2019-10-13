package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.person.ScheduleStub;
import seedu.address.ui.util.MemberList;

/**
 * A class to handle the details view of a person or a group.
 */
public class GroupDetailsView extends UiPart<Region> {

    private static final String FXML = "GroupDetailsView.fxml";

    @FXML
    private StackPane groupDetailCard;

    @FXML
    private StackPane groupMembersList;

    @FXML
    private StackPane groupSchedule;

    @FXML
    private StackPane groupDetailContainer;

    public GroupDetailsView(DetailWindowDisplay detailWindowDisplay) {
        super(FXML);
        ScheduleStub stub = new ScheduleStub();
        ArrayList<WeekSchedule> schedules = new ArrayList<>(List.of(stub.getSchedule(),
                stub.getSchedule(), stub.getSchedule()));
        ScheduleView scheduleView = new ScheduleView(schedules);
        GroupCard groupCard = new GroupCard(detailWindowDisplay.getGroupDisplay(), 1);
        groupDetailCard.getChildren().add(groupCard.getRoot());
        groupMembersList.getChildren().add(new MemberList().getRoot());
        groupSchedule.getChildren().add(scheduleView.getRoot());
    }

}
