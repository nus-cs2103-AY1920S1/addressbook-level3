package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;
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
    private Label dueDate;
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
        status.setStyle(task.getIsDone() ? "-fx-background-color: #96b946;" : "-fx-background-color: #ff6973;");

        dueDate.setText(task.getDueDate().format(ParserUtil.FORMATTER));
        dueDate.setWrapText(true);
        dueDate.setTextAlignment(TextAlignment.JUSTIFY);

        if (!Author.isDefaultAuthor(task.getAuthor())) {
            author.setText(task.getAuthor().value);
        } else {
            author.setText("Not specified");
            author.setStyle("-fx-text-fill: grey;");
        }
        author.setWrapText(true);
        author.setTextAlignment(TextAlignment.JUSTIFY);

        if (!Description.isDefaultDescription(task.getDescription())) {
            description.setText(task.getDescription().value);
        } else {
            description.setText("Not specified");
            description.setStyle("-fx-text-fill: grey;");
        }
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);

        if (!WebLink.isDefaultWebLink(task.getWebLink())) {
            weblink.setText(task.getWebLink().value);
        } else {
            weblink.setText("Not specified");
            weblink.setStyle("-fx-text-fill: grey;");
        }
        weblink.setWrapText(true);
        weblink.setTextAlignment(TextAlignment.JUSTIFY);

        if (!Difficulty.isDefaultDifficulty(task.getDifficulty())) {
            difficulty.setText(task.getDifficulty().toString());
        } else {
            difficulty.setText("Not specified");
            difficulty.setStyle("-fx-text-fill: grey;");
        }
        difficulty.setWrapText(true);
        difficulty.setTextAlignment(TextAlignment.JUSTIFY);

        if (!Remark.isDefaultRemark(task.getRemark())) {
            remark.setText(task.getRemark().value);
        } else {
            remark.setText("Not specified");
            remark.setStyle("-fx-text-fill: grey;");
        }
        remark.setWrapText(true);
        remark.setTextAlignment(TextAlignment.JUSTIFY);

        if (!Source.isDefaultSource(task.getSource())) {
            source.setText(task.getSource().value);
        } else {
            source.setText("Not specified");
            source.setStyle("-fx-text-fill: grey;");
        }
        source.setWrapText(true);
        source.setTextAlignment(TextAlignment.JUSTIFY);

        for (Tag tag : task.getTags()) {
            Label l = new Label(tag.getName());
            String colorStyle = "-fx-background-color: " + tag.getColor();
            l.setStyle(colorStyle);
            tags.getChildren().add(l);
        }
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
