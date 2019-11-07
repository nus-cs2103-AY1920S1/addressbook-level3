package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final Logic logic;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label projectHeader;
    @FXML
    private FlowPane projects;
    @FXML
    private Label taskHeader;
    @FXML
    private FlowPane tasksAssigned;
    @FXML
    private Label meetingHeader;
    @FXML
    private FlowPane meetingsAttended;

    public PersonCard(Person person, int displayedIndex, Logic logic) {
        super(FXML);
        this.person = person;
        this.logic = logic;

        File imgFile = new File(person.getProfilePicture().toString());

        try {
            Image img = new Image(new FileInputStream(imgFile));
            profilePicture.setImage(img);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        person.getProjects().stream()
                .forEach(project -> projects.getChildren().add(new Label(project)));
        Logger.getGlobal().warning("isRun");
        projectHeader.setText("Projects:");
        projects.setOrientation(Orientation.VERTICAL);
        projects.setPrefWrapLength(100);

        setTasks();
        setMeetings();
    }

    private void setTasks() {
        int taskCount = 0;
        taskHeader.setText("Tasks assigned: ");
        for (String projectTitle : person.getProjects()) {

            if (!person.getPerformance().getTaskAssignment().containsKey(projectTitle)) {
                continue;
            }

            List<Task> taskList = person.getPerformance().getTaskAssignment().get(projectTitle);
            for (Task task : taskList) {
                tasksAssigned.getChildren().add(new Label("    " + ++taskCount + ". " + projectTitle + ": " + task.toString()));
            }
        }
        tasksAssigned.setOrientation(Orientation.VERTICAL);
        tasksAssigned.setPrefWrapLength(100);
    }

    private void setMeetings() {
        int meetingCount = 0;
        meetingHeader.setText("Meetings attended:");
        for (String projectTitle : person.getProjects()) {

            if (!person.getPerformance().getMeetingsAttended().containsKey(projectTitle)) {
                continue;
            }

            List<Meeting> meetingList = person.getPerformance().getMeetingsAttended().get(projectTitle);
            List<Meeting> sortedMeetings = meetingList.stream().sorted(Comparator.comparing(m -> m.getTime().getDate())).collect(Collectors.toList());

            for (Meeting meeting : sortedMeetings) {
                meetingsAttended.getChildren().add(new Label("    "
                        + ++meetingCount + ". "
                        + projectTitle + ": "
                        + meeting.getDescription().toString() + " on " + meeting.getTime().toString()));
            }
        }

        meetingsAttended.setOrientation(Orientation.VERTICAL);
        meetingsAttended.setPrefWrapLength(100);
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
    }

    @FXML
    private void handleDrop(DragEvent event) throws FileNotFoundException, CommandException, IllegalValueException {
        List<File> files = event.getDragboard().getFiles();
        File imgFile = files.get(0);
        Image img = new Image(new FileInputStream(imgFile));
        profilePicture.setImage(img);
        logic.executeImageDrop(imgFile, person);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
