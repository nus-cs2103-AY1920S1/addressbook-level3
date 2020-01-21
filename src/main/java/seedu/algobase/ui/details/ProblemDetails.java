package seedu.algobase.ui.details;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.ui.UiPart;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionResult;
import seedu.algobase.ui.action.UiActionType;

/**
 * An UI component that displays detailed information of a {@code Problem}.
 */
public class ProblemDetails extends UiPart<Region> {

    private static final String FXML = "ProblemDetails.fxml";
    private static final Logger logger = LogsCenter.getLogger(ProblemDetails.class);

    private final Problem problem;
    private final UiActionExecutor uiActionExecutor;

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
        this.uiActionExecutor = uiActionExecutor;

        setFields(problem);
        addListenersToFields();
        createWarningDialog();

        editButton.setDisable(true);

    }

    /**
     * Sets the fields of the ProblemDetails with a given Problem.
     */
    public void setFields(Problem problem) {
        setFields(
            problem.getName().fullName,
            problem.getAuthor().value,
            problem.getWebLink().value,
            problem.getDescription().value,
            problem.getDifficulty().toString(),
            problem.getRemark().value,
            problem.getSource().value
        );
    }

    /**
     * Sets the fields of the ProblemDetails.
     */
    public void setFields(
        String name,
        String author,
        String weblink,
        String description,
        String difficulty,
        String remark,
        String source
    ) {
        this.name.setText(name);
        this.author.setText(author);
        this.weblink.setText(weblink);
        this.description.setText(description);
        if (!Difficulty.isDefaultDifficulty(difficulty)) {
            this.difficulty.setText(difficulty);
        } else {
            this.difficulty.setText("");
        }
        this.remark.setText(remark);
        this.source.setText(source);
    }

    /**
     * Add Listener to fields to toggle the Edit Problem Button.
     */
    public void addListenersToFields() {
        name.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        author.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        weblink.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        description.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        difficulty.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        remark.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        source.textProperty().addListener((e) -> {
            setEditableStatus();
        });
    }

    /**
     * Instantiates the warning dialog for deleting a problem.
     */
    public void createWarningDialog() {
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
    }

    /**
     * Checks if any of the fields are dirty.
     */
    public boolean isDirty() {
        return !name.getText().equals(problem.getName().fullName)
            || !author.getText().equals(problem.getAuthor().value)
            || !weblink.getText().equals(problem.getWebLink().value)
            || !description.getText().equals(problem.getDescription().value)
            || isDifficultyDirty()
            || !remark.getText().equals(problem.getRemark().value)
            || !source.getText().equals(problem.getSource().value);
    }

    /**
     * Checks if the Difficulty field is dirty.
     */
    public boolean isDifficultyDirty() {
        if (Difficulty.isDefaultDifficulty(problem.getDifficulty())) {
            return !difficulty.getText().equals("");
        }

        try {
            return !ParserUtil.parseDifficulty(difficulty.getText()).equals(problem.getDifficulty());
        } catch (ParseException e) {
            return true;
        }
    }

    /**
     * Sets the editable status of the Problem depending on if the fields are dirty.
     */
    public void setEditableStatus() {
        if (isDirty()) {
            editButton.setDisable(false);
        } else {
            editButton.setDisable(true);
        }
    }

    /**
     * Handles the MouseClick event when the user clicks on the Edit Problem button.
     */
    @FXML
    public void handleEditProblem(MouseEvent e) {
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

        UiActionResult uiActionResult = uiActionExecutor.execute(new UiActionDetails(
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

        if (uiActionResult.isSuccessfullyExecuted()) {
            logger.info("Disabling the Edit button");
            editButton.setDisable(true);
        }
        e.consume();
    }

    /**
     * Handles the MouseClick event when the user clicks on the Delete Problem button.
     */
    @FXML
    public void handleDeleteProblem(MouseEvent e) {
        if (!warningDialog.isShowing()) {
            logger.info("Delete button clicked - showing warning dialog");
            warningDialog.show();
        } else {
            logger.info("Delete button clicked - focusing on warning dialog");
            warningDialog.focus();
        }
        e.consume();
    }
}
