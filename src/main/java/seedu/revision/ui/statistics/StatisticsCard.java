package seedu.revision.ui.statistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.revision.model.quiz.Statistics;
import seedu.revision.ui.UiPart;

/**
 * An UI component that displays breakdown of results in a {@code Statistics}.
 */
public class StatisticsCard extends UiPart<Region> {

    private static final String FXML = "StatisticsListCard.fxml";
    public final Statistics statistics;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RevisionTool level 4</a>
     */
    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label total;
    @FXML
    private Label difficulty1;
    @FXML
    private Label difficulty2;
    @FXML
    private Label difficulty3;
    @FXML
    private Label priority;

    public StatisticsCard(Statistics statistics, int displayedIndex) {
        super(FXML);
        this.statistics = statistics;
        id.setText(displayedIndex + ". ");
        total.setText("Total: " + String.format("%.2f", statistics.getResult()) + "%");
        difficulty1.setText("Difficulty 1: " + String.format("%.2f", statistics.getResult1()) + "%");
        difficulty2.setText("Difficulty 2: " + String.format("%.2f", statistics.getResult2()) + "%");
        difficulty3.setText("Difficulty 3: " + String.format("%.2f", statistics.getResult3()) + "%");
        priority.setText("Please prioritise this type of questions: " + statistics.getPriority());
        priority.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatisticsCard)) {
            return false;
        }

        // state check
        StatisticsCard card = (StatisticsCard) other;
        return id.getText().equals(card.id.getText())
                && statistics.equals(card.statistics);
    }
}

