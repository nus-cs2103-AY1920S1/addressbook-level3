package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.algobase.model.problem.Problem;

/**
 * An UI component that displays detailed information of a {@code Problem}.
 */
public class ProblemDetails extends UiPart<Region> {

    private static final String FXML = "ProblemDetails.fxml";

    private final Problem problem;

    @FXML
    private TextField name;

    @FXML
    private TextArea description;

    public ProblemDetails(Problem problem) {
        super(FXML);
        this.problem = problem;
        name.setText(problem.getName().fullName);
        description.setText(problem.getDescription().value);
    }
}
