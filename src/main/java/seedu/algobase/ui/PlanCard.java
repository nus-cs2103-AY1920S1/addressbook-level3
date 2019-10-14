package seedu.algobase.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.task.Task;


/**
 * An UI component that displays information of a {@code Plan}.
 */
public class PlanCard extends UiPart<Region> {

    private static final String FXML = "PlanListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    @FXML
    private FlowPane tasks;

    public PlanCard(Plan plan, int displayedIndex) {
        super(FXML);
        this.plan = plan;
        id.setText(displayedIndex + ". ");
        planName.setText(plan.getPlanName().fullName);
        planDescription.setText(plan.getPlanDescription().value);
        startDate.setText(plan.getStartDate().format(Plan.FORMATTER));
        endDate.setText(plan.getEndDate().format(Plan.FORMATTER));
        plan.getTasks().stream()
                .sorted(Comparator.comparing(Task::getDateTime))
                .forEach(task -> tasks.getChildren().add(new Label(task.getProblem().toString() + '\n'
                + task.getIsSolved().toString() + '\n' + task.getDateTime().toString())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlanCard)) {
            return false;
        }

        // state check
        PlanCard card = (PlanCard) other;
        return id.getText().equals(card.id.getText())
                && plan.equals(card.plan);
    }
}
