package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;

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
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    public final Person person;

    @FXML
    private ScrollPane detailsPane;
    @FXML
    private Label id;
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

    public PersonDetailsPanel(Person viewedPerson, Set<Activity> activities) {
        super(FXML);

        this.person = viewedPerson;
        id.setText("ID: " + person.getPrimaryKey());
        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        address.setText(person.getAddress().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
