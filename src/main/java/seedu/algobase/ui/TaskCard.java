package seedu.algobase.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label targetDate;
    @FXML
    private Label author;
    @FXML
    private Label description;
    @FXML
    private Label weblink;
    @FXML
    private Label difficulty;
    @FXML
    private Label remark;
    @FXML
    private Label source;
    @FXML
    private FlowPane tags;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
        id.setTextAlignment(TextAlignment.JUSTIFY);
        name.setText(task.getName().fullName);
        name.setWrapText(true);
        name.setTextAlignment(TextAlignment.JUSTIFY);
        status.setText(task.getStatusIcon());
        status.setStyle(task.getIsSolved() ? "-fx-background-color: #96b946;" : "-fx-background-color: #ff6973;");
        targetDate.setText(task.getTargetDate().format(ParserUtil.FORMATTER));
        targetDate.setWrapText(true);
        targetDate.setTextAlignment(TextAlignment.JUSTIFY);
        author.setText(task.getAuthor().value);
        author.setWrapText(true);
        author.setTextAlignment(TextAlignment.JUSTIFY);
        description.setText(task.getDescription().value);
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
        weblink.setText(task.getWebLink().value);
        weblink.setWrapText(true);
        weblink.setTextAlignment(TextAlignment.JUSTIFY);
        difficulty.setText(task.getDifficulty().toString());
        difficulty.setWrapText(true);
        difficulty.setTextAlignment(TextAlignment.JUSTIFY);
        remark.setText(task.getRemark().value);
        remark.setWrapText(true);
        remark.setTextAlignment(TextAlignment.JUSTIFY);
        source.setText(task.getSource().value);
        source.setWrapText(true);
        source.setTextAlignment(TextAlignment.JUSTIFY);
        task.getTags().stream()
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
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
