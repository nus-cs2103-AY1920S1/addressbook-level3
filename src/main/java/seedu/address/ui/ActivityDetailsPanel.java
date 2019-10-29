package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Panel displaying details of a contact.
 */
public class ActivityDetailsPanel extends UiPart<Region> {
    private static final String FXML = "ActivityDetailsPanel.fxml";

    public final Activity activity;

    @FXML
    private ScrollPane detailsPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private FlowPane participantTags;
    @FXML
    private Label participantCount;
    @FXML
    private Label spending;

    public ActivityDetailsPanel(Activity viewedActivity, List<Person> participants) {
        super(FXML);

        this.activity = viewedActivity;
        id.setText("ID: " + activity.getPrimaryKey());
        title.setText(activity.getTitle().toString());

        participants.stream()
                .map((participant) -> participant.getName().toString())
                .forEach(name -> participantTags.getChildren().add(new Label(name)));

        int numParticipants = activity.getParticipantIds().size();
        participantCount.setText(numParticipants + (numParticipants != 1 ? " participants" : " participant"));

        double totalSpending = activity.getExpenses().stream()
                .map((expense) -> expense.getAmount().value)
                .reduce(0.00, (acc, amt) -> acc + amt);
        spending.setText(String.format("$%.2f", totalSpending));
    }
}
