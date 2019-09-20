package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.algobase.model.Problem.Problem;

import java.util.Comparator;

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
     * @see <a href="https://github.com/se-edu/algobase-level4/issues/336">The issue on AlgoBase level 4</a>
     */

    public final Problem problem;

    @FXML
    private HBox cardPane;
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

    public ProblemCard(Problem problem, int displayedIndex) {
        super(FXML);
        this.problem = problem;
        id.setText(displayedIndex + ". ");
        name.setText(problem.getName().fullName);
        phone.setText(problem.getPhone().value);
        address.setText(problem.getDescription().value);
        email.setText(problem.getWebLink().value);
        problem.getTags().stream()
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
        if (!(other instanceof ProblemCard)) {
            return false;
        }

        // state check
        ProblemCard card = (ProblemCard) other;
        return id.getText().equals(card.id.getText())
                && problem.equals(card.problem);
    }
}
