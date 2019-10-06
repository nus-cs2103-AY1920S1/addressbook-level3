package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.person.ScheduleStub;
import seedu.address.model.person.schedule.Schedule;

/**
 * A class to handle the details view of a person or a group.
 */
public class DetailsView extends UiPart<Region> {

    private static final String FXML = "DetailsView.fxml";

    @FXML
    private VBox detailsView;

    public DetailsView(Person person) {
        super(FXML);
        this.detailsView = new VBox();
        PersonCard personCard = new PersonCard(person, 1);
        ScheduleStub stub = new ScheduleStub();
        ArrayList<Schedule> schedules = new ArrayList<>(List.of(stub.getSchedule(),
                stub.getSchedule(), stub.getSchedule()));
        ScheduleView scheduleView = new ScheduleView(schedules);
        detailsView.getChildren().addAll(personCard.getRoot(), scheduleView.getScheduleView());
    }

    public ScrollPane getDetailsView() {
        ScrollPane sp = new ScrollPane();
        sp.setContent(this.detailsView);
        return sp;
    }

    public VBox exportNode() {
        return detailsView;
    }
}
