package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;

public class IndividualPerformanceCard extends UiPart<Region> {

    private static final String FXML = "IndividualPerformanceCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final PerformanceOverview performanceOverview;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label numTaskTitle;
    @FXML
    private Label numTasksDone;
    @FXML
    private Label taskDoneRateTitle;
    @FXML
    private Label taskDoneRate;
    @FXML
    private Label attendanceTitle;
    @FXML
    private Label attendance;
    @FXML
    private Label attendanceRateTitle;
    @FXML
    private Label attendanceRate;


    public IndividualPerformanceCard(Person person, PerformanceOverview performanceOverview, int displayedIndex) {
        super(FXML);
        this.person = person;
        this.performanceOverview = performanceOverview;

        cardPane.setFillHeight(true);

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        numTaskTitle.setText("Number of tasks done:");
        numTasksDone.setText(" " + performanceOverview.getNumOfTaskDoneOf(person));
        taskDoneRateTitle.setText("% of tasks completed:");
        taskDoneRate.setText(" " + performanceOverview.getTaskCompletionRateOf(person).getRateAsString() + "%");
        attendanceTitle.setText("Number of meetings attend:");
        attendance.setText(" " + performanceOverview.getAttendanceOf(person));
        attendanceRateTitle.setText("% of meetings attended:");
        attendanceRate.setText(" " + performanceOverview.getRateOfAttendanceOf(person).getRateAsString() + "%");
    }

    @Override public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof IndividualPerformanceCard)) {
            return false;
        }

        //state check
        IndividualPerformanceCard card = (IndividualPerformanceCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
