package seedu.algobase.ui.details;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.ui.UiPart;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionType;

/**
 * An UI component that displays detailed information of a {@code Problem}.
 */
public class PlanDetails extends UiPart<Region> {

    private static final String FXML = "PlanDetails.fxml";
    private static final Logger logger = LogsCenter.getLogger(PlanDetails.class);

    private final Plan plan;

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

        editButton.setDisable(true);

        planName.setText(plan.getPlanName().fullName);
        planName.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        startDate.setValue(plan.getStartDate());
        startDate.valueProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        endDate.setValue(plan.getEndDate());
        endDate.valueProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        planDescription.setText(plan.getPlanDescription().value);
        planDescription.textProperty().addListener((e) -> {
            editButton.setDisable(false);
        });

        editButton.setOnMouseClicked((e) -> {
            logger.info("Edit button clicked on Plan Details");
            logger.info(
                "Creating new UiActionDetails with type " + UiActionType.EDIT_PLAN
                    + " with ID of " + plan.getId()
                    + " with a plan name of " + planName.getText()
                    + " with a plan description of " + planDescription.getText()
                    + " with a start date of " + startDate.getValue().toString()
                    + " with an end date of " + endDate.getValue().toString()
            );

            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.EDIT_PLAN,
                plan.getId(),
                planName.getText(),
                planDescription.getText(),
                startDate.getValue(),
                endDate.getValue()
            ));

            logger.info("Disabling the Edit button");
            editButton.setDisable(true);
            e.consume();
        });

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
