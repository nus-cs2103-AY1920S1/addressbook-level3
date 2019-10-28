package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class ProjectCard extends UiPart<Region> {

    private static final String FXML = "ProjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label description;

    @FXML
    private Label meetingTitle;

    private int count = 0;

    public ProjectCard(Project project, int displayedIndex) {
        super(FXML);
        this.project = project;
        id.setText(displayedIndex + ". ");
        title.setText(project.getTitle().title);
        description.setText(project.getDescription().description);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectCard)) {
            return false;
        }

        // state check
        ProjectCard card = (ProjectCard) other;
        return id.getText().equals(card.id.getText())
                && project.equals(card.project);
    }

    public void displayMeeting(FlowPane meetings, Project project) {
        List<Meeting> listOfMeetings = new ArrayList<Meeting>(project.getListOfMeeting());
        int meetingCount = 1;
        listOfMeetings.sort(Comparator.comparing(m -> m.getTime().getDate()));
        for (Meeting meeting: listOfMeetings) {
            meetings.getChildren().add(new Label("    " + meetingCount++ + ". " + meeting.getDescription().description + " " + meeting.getTime().time));
        }
    }
}
