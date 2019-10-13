package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.person.ScheduleStub;
import seedu.address.ui.util.ColorGenerator;
import seedu.address.ui.util.GroupDetailCard;
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
        ArrayList<String> names = schedules.stream()
                .map(wkSch -> wkSch.getPersonDisplay().getName().toString())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> colors = ColorGenerator.generateColorList(names.size());
        ScheduleView scheduleView = new ScheduleView(schedules, colors);
        GroupDetailCard groupCard = new GroupDetailCard(detailWindowDisplay.getGroupDisplay());
        groupDetailCard.getChildren().add(groupCard.getRoot());
        groupMembersList.getChildren().add(new MemberList(names, colors).getRoot());
        groupSchedule.getChildren().add(scheduleView.getRoot());
    }

}
