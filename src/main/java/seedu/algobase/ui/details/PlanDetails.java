package seedu.algobase.ui.details;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.ui.UiPart;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionResult;
import seedu.algobase.ui.action.UiActionType;

/**
 * An UI component that displays detailed information of a {@code Problem}.
 */
public class PlanDetails extends UiPart<Region> {

    private static final String FXML = "PlanDetails.fxml";
    private static final Logger logger = LogsCenter.getLogger(PlanDetails.class);

    private final Plan plan;
    private final UiActionExecutor uiActionExecutor;

    @FXML
    private TextField planName;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextArea planDescription;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private WarningDialog warningDialog;

    public PlanDetails(Plan plan, UiActionExecutor uiActionExecutor) {
        super(FXML);
        requireAllNonNull(plan, uiActionExecutor);

        this.plan = plan;
        this.uiActionExecutor = uiActionExecutor;

        setFields(plan);
        addListenersToFields();
        createWarningDialog();

        editButton.setDisable(true);
    }

    /**
     * Sets the fields of the PlanDetails with a given plan.
     */
    public void setFields(Plan plan) {
        setFields(
            plan.getPlanName().fullName,
            plan.getPlanDescription().value,
            plan.getStartDate(),
            plan.getEndDate()
        );
    }

    /**
     * Sets the fields of the PlanDetails.
     */
    public void setFields(
        String planName,
        String planDescription,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this.planName.setText(planName);
        this.planDescription.setText(planDescription);
        this.startDate.setValue(startDate);
        this.endDate.setValue(endDate);
    }

    /**
     * Add Listener to fields to toggle the Edit Plan Button.
     */
    public void addListenersToFields() {
        planName.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        planDescription.textProperty().addListener((e) -> {
            setEditableStatus();
        });

        startDate.valueProperty().addListener((e) -> {
            setEditableStatus();
        });

        endDate.valueProperty().addListener((e) -> {
            setEditableStatus();
        });
    }

    /**
     * Instantiates the warning dialog for deleting a problem.
     */
    public void createWarningDialog() {
        this.warningDialog = new WarningDialog(
            "Are you sure you want to delete this plan?", (Object... objects) -> {

            boolean shouldDelete = (boolean) objects[0];

            // Close the warning dialog
            if (warningDialog.isShowing()) {
                logger.info("Closing the Warning Dialog");
                warningDialog.hide();
            }

            if (!shouldDelete) {
                return;
            }

            // Close the tab
            logger.info(
                "Creating new UiActionDetails with type " + UiActionType.CLOSE_DETAILS_TAB
                    + " with model type of " + ModelType.PLAN
                    + " with ID of " + plan.getId()
            );

            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.CLOSE_DETAILS_TAB,
                ModelType.PLAN,
                plan.getId()
            ));

            // Delete the plan
            logger.info(
                "Creating new UiActionDetails with type " + UiActionType.DELETE_PLAN
                    + " with ID of " + plan.getId()
            );

            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.DELETE_PLAN,
                plan.getId()
            ));
        });
    }

    /**
     * Checks if any of the fields are dirty.
     */
    public boolean isDirty() {
        return !planName.getText().equals(plan.getPlanName().fullName)
            || !startDate.getValue().equals(plan.getStartDate())
            || !endDate.getValue().equals(plan.getEndDate())
            || !planDescription.getText().equals(plan.getPlanDescription().value);
    }

    /**
     * Sets the editable status of the Plan depending on if the fields are dirty.
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
    public void handleEditPlan(MouseEvent e) {
        logger.info("Edit button clicked on Plan Details");
        logger.info(
            "Creating new UiActionDetails with type " + UiActionType.EDIT_PLAN
                + " with ID of " + plan.getId()
                + " with a plan name of " + planName.getText()
                + " with a plan description of " + planDescription.getText()
                + " with a start date of " + startDate.getValue().toString()
                + " with an end date of " + endDate.getValue().toString()
        );

        UiActionResult uiActionResult = uiActionExecutor.execute(new UiActionDetails(
            UiActionType.EDIT_PLAN,
            plan.getId(),
            planName.getText(),
            planDescription.getText(),
            startDate.getValue(),
            endDate.getValue()
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
    public void handleDeletePlan(MouseEvent e) {
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
