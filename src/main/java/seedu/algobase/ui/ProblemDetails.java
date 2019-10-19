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
    private TextField author;
    @FXML
    private TextField weblink;
    @FXML
    private TextField difficulty;
    @FXML
    private TextField remark;
    @FXML
    private TextField source;
    @FXML
    private TextArea description;

    public ProblemDetails(Problem problem) {
        super(FXML);
        this.problem = problem;
        name.setText(problem.getName().fullName);
        author.setText(problem.getAuthor().value);
        weblink.setText(problem.getWebLink().value);
        description.setText(problem.getDescription().value);
        remark.setText(problem.getRemark().value);
        source.setText(problem.getRemark().value);
    }
}
