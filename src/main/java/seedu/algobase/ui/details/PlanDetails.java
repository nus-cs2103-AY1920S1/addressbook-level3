package seedu.algobase.ui.details;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.ui.UiPart;

/**
 * An UI component that displays detailed information of a {@code Problem}.
 */
public class PlanDetails extends UiPart<Region> {

    private static final String FXML = "PlanDetails.fxml";

    private final Plan plan;

    @FXML
    private TextField planName;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextArea planDescription;

    public PlanDetails(Plan plan) {
        super(FXML);
        this.plan = plan;
        planName.setText(plan.getPlanName().fullName);
        startDate.setText(plan.getStartDate().toString());
        endDate.setText(plan.getEndDate().toString());
        planDescription.setText(plan.getPlanDescription().value);
    }
}
