package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.person.Person;
import seedu.address.model.person.ScheduleStub;

/**
 * A class to handle the details view of a person or a group.
 */
public class DetailsView extends UiPart<Region> {

    private static final String FXML = "DetailsView.fxml";

    @FXML
    private VBox detailsView;

    @FXML
    private StackPane card;

    @FXML
    private StackPane schedule;

    @FXML
    private ScrollPane container;

    public DetailsView(PersonDisplay person) {
        super(FXML);
        this.detailsView = new VBox();
        PersonCard personCard = new PersonCard(person, 1);
        ScheduleStub stub = new ScheduleStub();
        ArrayList<WeekSchedule> schedules = new ArrayList<>(List.of(stub.getSchedule(),
                stub.getSchedule(), stub.getSchedule()));
        ScheduleView scheduleView = new ScheduleView(schedules);
        schedule.getChildren().add(scheduleView.getScheduleView());
        this.card.getChildren().add(personCard.getRoot());

    }

    public VBox exportNode() {
        return detailsView;
    }
}
