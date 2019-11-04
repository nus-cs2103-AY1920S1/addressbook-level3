package seedu.algobase.ui.details;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.ui.UiPart;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionType;

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
    @FXML
    private Button submitButton;

    public ProblemDetails(Problem problem, UiActionExecutor uiActionExecutor) {
        super(FXML);
        this.problem = problem;
        name.setText(problem.getName().fullName);
        author.setText(problem.getAuthor().value);
        weblink.setText(problem.getWebLink().value);
        description.setText(problem.getDescription().value);
        difficulty.setText(problem.getDifficulty().toString());
        remark.setText(problem.getRemark().value);
        source.setText(problem.getSource().value);

        submitButton.setOnMouseClicked((e) -> {
            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.EDIT_PROBLEM,
                problem.getId(),
                name.getText(),
                author.getText(),
                weblink.getText(),
                description.getText(),
                difficulty.getText(),
                remark.getText(),
                source.getText()
            ));
            e.consume();
        });
    }
}
