package seedu.algobase.ui.details;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.problem.Difficulty;
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
    private static final Logger logger = LogsCenter.getLogger(ProblemDetails.class);

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
        requireAllNonNull(problem, uiActionExecutor);

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

        if (!Difficulty.isDefaultDifficulty(problem.getDifficulty())) {
            difficulty.setText(problem.getDifficulty().toString());
        } else {
            difficulty.setText("");
        }
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
            logger.info("Edit button clicked on Problem Details");
            logger.info(
                "Creating new UiActionDetails with type " + UiActionType.EDIT_PROBLEM
                    + " with ID of " + problem.getId()
                    + " with a name of " + name.getText()
                    + " with an author of " + author.getText()
                    + " with a weblink of " + weblink.getText()
                    + " with a description of " + description.getText()
                    + " with a difficulty of " + difficulty.getText()
                    + " with a remark of " + remark.getText()
                    + " with a source of " + source.getText()
            );

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

            logger.info("Disabling the Edit button");
            editButton.setDisable(true);
            e.consume();
        });

        this.warningDialog = new WarningDialog(
            "Are you sure you want to delete this problem?\n"
                + "This will also delete the related tasks in the plans.", (Object... objects) -> {

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
            logger.info(
                "Creating new UiActionDetails with type " + UiActionType.CLOSE_DETAILS_TAB
                    + " with model type of " + ModelType.PROBLEM
                    + " with ID of " + problem.getId()
            );

            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.CLOSE_DETAILS_TAB,
                ModelType.PROBLEM,
                problem.getId()
            ));

            // Delete the problem
            logger.info(
                "Creating new UiActionDetails with type " + UiActionType.DELETE_PROBLEM
                    + " with ID of " + problem.getId()
                    + " with an isForced value of " + Boolean.valueOf(true)
            );

            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.DELETE_PROBLEM,
                problem.getId(),
                Boolean.valueOf(true)
            ));
        });

        deleteButton.setOnMouseClicked((e) -> {
            if (!warningDialog.isShowing()) {
                logger.info("Delete button clicked - showing warning dialog");
                warningDialog.show();
            } else {
                logger.info("Delete button clicked - focusing on warning dialog");
                warningDialog.focus();
            }
            e.consume();
        });
    }
}
