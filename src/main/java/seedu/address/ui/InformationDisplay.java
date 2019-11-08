package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.performance.Event;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} when selected.
 */
public class InformationDisplay extends UiPart<Region> {

    public static final String NO_PERSON_SELECTED_MESSAGE = "";
    private static final String FXML = "InformationDisplay.fxml";

    public final Person person;
    public final String attendance;
    public final ArrayList<Event> athleteEvents;

    @FXML
    private FlowPane tags;
    @FXML
    private GridPane informationBlock;
    @FXML
    private ImageView photo;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label attendanceRate;
    @FXML
    private VBox imageHolder;
    @FXML
    private VBox informationBox;
    @FXML
    private VBox performanceDisplay;

    public InformationDisplay(Person person, String attendance, ArrayList<Event> athleteEvents) {
        super(FXML);
        this.person = person;
        this.attendance = attendance;
        this.athleteEvents = athleteEvents;
        displayPersonalInfo();
        performanceDisplay();

    }

    /**
     * Resize the image when window size changes
     */
    public void resizeImage() {
        photo.fitHeightProperty().bind(imageHolder.heightProperty().subtract(40));
        photo.fitWidthProperty().bind(imageHolder.widthProperty());
    }

    /**
     * Displays the personal information of the selected person
     */
    public void displayPersonalInfo() {
        name.setText(this.person.getName().fullName);
        phone.setText(this.person.getPhone().value);
        address.setText(this.person.getAddress().value);
        address.setPrefWidth(150);
        address.setWrapText(true);
        email.setText(this.person.getEmail().value);
        gender.setText(this.person.getGender().genderOfPerson);
        photo.setImage(new Image(this.person.getPhoto().filePath));
        photo.setPreserveRatio(true);
        photo.setSmooth(true);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add((new TagLayout(tag.tagName)).getRoot()));
        attendanceRate.setText(attendance);
    }

    /**
     * Displays the performance of the selected person.
     */
    public void performanceDisplay() {
        if (!athleteEvents.isEmpty()) {
            for (Event event : athleteEvents) {
                String eventName = event.getName();
                String mostRecentDate = event.getLatestTiming(person)[0];
                String mostRecentTiming = event.getLatestTiming(person)[1];
                String personalBest = event.getPersonalBest(person)[0];
                String personalBestDate = event.getPersonalBest(person)[1];
                PersonPerformanceDisplay athletePerformance = new PersonPerformanceDisplay(eventName, personalBestDate,
                                                                                           personalBest,
                                                                                           mostRecentDate,
                                                                                           mostRecentTiming);
                performanceDisplay.getChildren().add(athletePerformance.getRoot());

            }
        } else {
            PersonPerformanceDisplay athletePerformance = new PersonPerformanceDisplay("-", "-",
                                                                                       "-", "-",
                                                                                       "-");
            performanceDisplay.getChildren().add(athletePerformance.getRoot());
        }
    }



}
