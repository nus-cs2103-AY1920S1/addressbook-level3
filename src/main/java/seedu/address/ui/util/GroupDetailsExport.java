package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.person.ScheduleStub;
import seedu.address.ui.ScheduleView;
import seedu.address.ui.ScheduleViewExport;
import seedu.address.ui.UiPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to handle exportation of group details.
 */
public class GroupDetailsExport extends UiPart<Region> {
    private static final String FXML = "GroupDetailsExport.fxml";

    @FXML
    private StackPane groupDetailCard;

    @FXML
    private StackPane groupMembersList;

    @FXML
    private StackPane groupSchedule;

    @FXML
    private StackPane groupDetailContainer;

    public GroupDetailsExport(DetailWindowDisplay detailWindowDisplay) {
        super(FXML);
        ScheduleStub stub = new ScheduleStub();
        ArrayList<WeekSchedule> schedules = new ArrayList<>(List.of(stub.getSchedule(),
                stub.getSchedule(), stub.getSchedule()));
        ArrayList<String> names = schedules.stream()
                .map(wkSch -> wkSch.getPersonDisplay().getName().toString())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> colors = ColorGenerator.generateColorList(names.size());
        ScheduleViewExport scheduleView = new ScheduleViewExport(schedules, colors);
        GroupDetailCard groupCard = new GroupDetailCard(detailWindowDisplay.getGroupDisplay());
        groupDetailCard.getChildren().add(groupCard.getRoot());
        groupMembersList.getChildren().add(new MemberList(names, colors).getRoot());
        groupSchedule.getChildren().add(scheduleView.getScheduleView());
    }
}
