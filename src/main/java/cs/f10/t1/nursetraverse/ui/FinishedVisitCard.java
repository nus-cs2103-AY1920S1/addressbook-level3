package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.model.visit.Visit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code Visit}.
 */
public class FinishedVisitCard extends UiPart<Region> {

    private static final String FXML = "FinishedVisitCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/patientBook-level4/issues/336">The issue on PatientBook level 4</a>
     */

    public final Visit visit;

    @FXML
    private HBox cardPane;
    @FXML
    private Label index;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label remark;
    @FXML
    private StackPane visitTasksPlaceholder;

    public FinishedVisitCard(Visit visit, int visitIndex) {
        super(FXML);
        this.visit = visit;

        //There's substantial overlap with this code here and OngoingVisitCard
        //But it is not combined as I do not want the designs to be intertwined
        //e.g. inclusion of index for example
        index.setText(Integer.toString(visitIndex));
        startDateTime.setText(visit.getStartDateTime().toString());
        if (visit.getEndDateTime().isPresent()) {
            endDateTime.setText(visit.getEndDateTime().get().toString());
        } else {
            endDateTime.setText("Unfinished (Ongoing Visit)");
        }

        String remarks = this.visit.getRemark().remark;
        if (remarks.isEmpty()) {
            remarks = "This visit has no remarks.";
        }
        remark.setText(remarks);

        VisitTaskTableView visitTaskTableView = new VisitTaskTableView(this.visit.getVisitTasks());
        visitTasksPlaceholder.getChildren().add(visitTaskTableView.getRoot());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FinishedVisitCard)) {
            return false;
        }

        // state check
        FinishedVisitCard card = (FinishedVisitCard) other;
        return visit.equals(card.visit);
    }
}
