package seedu.algobase.ui.details;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.algobase.model.ModelType;
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
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private WarningDialog warningDialog;

    public ProblemDetails(Problem problem, UiActionExecutor uiActionExecutor) {
        super(FXML);
        this.problem = problem;

        editButton.setDisable(true);

        name.setText(problem.getName().fullName);
        name.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        author.setText(problem.getAuthor().value);
        author.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        weblink.setText(problem.getWebLink().value);
        weblink.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        description.setText(problem.getDescription().value);
        description.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        difficulty.setText(problem.getDifficulty().toString());
        difficulty.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        remark.setText(problem.getRemark().value);
        remark.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        source.setText(problem.getSource().value);
        source.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        editButton.setOnMouseClicked((e) -> {
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
            editButton.setDisable(true);
            e.consume();
        });

        this.warningDialog = new WarningDialog(
            "Are you sure you want to delete this problem?",
            "Delete this problem from existing tasks", (Object... objects) -> {

            boolean shouldDelete = (boolean) objects[0];
            boolean isForcedDelete = (boolean) objects[1];

            // Close the warning dialog
            if (warningDialog.isShowing()) {
                warningDialog.hide();
            }

            if (!shouldDelete) {
                return;
            }

            // Close the tab
            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.CLOSE_DETAILS_TAB,
                ModelType.PROBLEM,
                problem.getId()
            ));

            // Delete the problem
            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.DELETE_PROBLEM,
                problem.getId(),
                isForcedDelete
            ));
        });

        deleteButton.setOnMouseClicked((e) -> {
            if (!warningDialog.isShowing()) {
                warningDialog.show();
            } else {
                warningDialog.focus();
            }
            e.consume();
        });
    }
}
