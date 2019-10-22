package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.model.plan.Plan;


/**
 * An UI component that displays information of a {@code Plan}.
 */
public class PlanCard extends UiPart<Region> {

    private static final String FXML = "PlanListCard.fxml";

    public final Plan plan;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label planName;
    @FXML
    private Label planDescription;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    public PlanCard(Plan plan, int displayedIndex) {
        super(FXML);
        this.plan = plan;
        id.setText(displayedIndex + ". ");
        planName.setText(plan.getPlanName().fullName + " (" + plan.getTasks().size() + " tasks)");
        planDescription.setText(plan.getPlanDescription().value);
        startDate.setText(plan.getStartDate().format(ParserUtil.FORMATTER));
        endDate.setText(plan.getEndDate().format(ParserUtil.FORMATTER));
    }

    @Override
    public boolean equals(Object other) {
        // checks if same object
        if (other == this) {
            return true;
        }

        // checks if object of same class
        // handles null
        if (!(other instanceof PlanCard)) {
            return false;
        }

        // check fields equality
        PlanCard card = (PlanCard) other;
        return id.getText().equals(card.id.getText())
                && plan.equals(card.plan);
    }
}
