package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.person.Interviewer;

/**
 * An UI component that displays information of a {@code Interviewer}.
 */
public class InterviewerCard extends UiPart<Region> {

    private static final String FXML = "InterviewerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Interviewer interviewer;

    @FXML
    private HBox interviewerCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private FlowPane tags;

    public InterviewerCard(Interviewer interviewer, int displayedIndex) {
        super(FXML);
        this.interviewer = interviewer;
        id.setText(displayedIndex + ". ");
        name.setText(interviewer.getName().fullName);
        phone.setText(interviewer.getPhone().value);
        interviewer.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewerCard)) {
            return false;
        }

        // state check
        InterviewerCard card = (InterviewerCard) other;
        return id.getText().equals(card.id.getText())
                && interviewer.equals(card.interviewer);
    }
}
