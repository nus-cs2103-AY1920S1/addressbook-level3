package seedu.tarence.ui;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Tutorial tutorial;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label day;
    @FXML
    private Label duration;
    @FXML
    private Label weeks;

    public TutorialCard(Tutorial tutorial, int displayedIndex) {
        super(FXML);
        this.tutorial = tutorial;
        id.setText(displayedIndex + ". ");
        name.setText(tutorial.getTutName().toString());
        time.setText("Time: " + tutorial.getTimeTable().getStartTime().toString());
        day.setText("Day: " + tutorial.getTimeTable().getDay().toString().toLowerCase());
        duration.setText("Duration: " + tutorial.getTimeTable().getStartTime().toString());
        String tutorialWeeks = tutorial.getTimeTable().getWeeks().stream()
                .map(n -> n.toString())
                .collect(Collectors.joining(", "));
        weeks.setText("Weeks: " + tutorialWeeks);
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
        TutorialCard card = (TutorialCard) other;
        return id.getText().equals(card.id.getText())
                && tutorial.equals(card.tutorial);
    }
}
