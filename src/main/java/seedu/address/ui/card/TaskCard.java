package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.model.tasks.TaskSource;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class TaskCard extends Card {

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private Label taskCardName;

    @FXML
    private Label taskCardDate;

    /**
     * Constructor for the EventCard, which displays the information of a particular event.
     *
     * @param event The given event.
     */
    public TaskCard(TaskSource event) {
        super(FXML);
        taskCardName.setText(event.getDescription());
        taskCardDate.setText(event.getDueDate().toEnglishDateTime());
        taskCardName.setMinHeight(Region.USE_PREF_SIZE);
    }
}
