package seedu.algobase.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Problem}.
 */
public class ProblemCard extends UiPart<Region> {

    private static final String FXML = "ProblemListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Problem problem;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
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

    public ProblemCard(Problem problem, int displayedIndex) {
        super(FXML);
        this.problem = problem;
        id.setText(displayedIndex + ". ");
        name.setText(problem.getName().fullName);
        author.setText(problem.getAuthor().value);
        description.setText(problem.getDescription().value);
        weblink.setText(problem.getWebLink().value);
        difficulty.setText(problem.getDifficulty().toString());
        remark.setText(problem.getRemark().value);
        source.setText(problem.getSource().value);

        for(Tag tag : problem.getTags()) {
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
        if (!(other instanceof ProblemCard)) {
            return false;
        }

        // state check
        ProblemCard card = (ProblemCard) other;
        return id.getText().equals(card.id.getText())
                && problem.equals(card.problem);
    }
}
