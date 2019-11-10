package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Panel displaying details of a contact.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Person person;

    @FXML
    private ScrollPane detailsPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox activityHistory;

    public PersonDetailsPanel(Person viewedPerson, List<Activity> activities) {
        super(FXML);
        this.person = viewedPerson;

        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        address.setText(person.getAddress().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        activities.stream()
                .forEach(activity -> {
                    Double transferAmount = activity.getTransferAmount(person.getPrimaryKey());
                    ActivityHistoryCard newNode = new ActivityHistoryCard(activity, transferAmount);
                    activityHistory.getChildren().add(newNode.getRoot());
                });
    }
}
