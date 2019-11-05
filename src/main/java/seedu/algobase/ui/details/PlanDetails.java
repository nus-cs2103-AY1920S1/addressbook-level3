package seedu.algobase.ui.details;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
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

    public PlanDetails(Plan plan, UiActionExecutor uiActionExecutor) {
        super(FXML);
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
            uiActionExecutor.execute(new UiActionDetails(
                UiActionType.EDIT_PLAN,
                plan.getId(),
                planName.getText(),
                planDescription.getText(),
                startDate.getValue(),
                endDate.getValue()
            ));
            editButton.setDisable(true);
            e.consume();
        });
    }
}
