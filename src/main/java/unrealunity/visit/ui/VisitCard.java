package unrealunity.visit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import unrealunity.visit.model.person.VisitReport;

/**
 * An UI component that displays information of a {@code VisitList}.
 */
public class VisitCard extends UiPart<Region> {

    private static final String FXML = "VisitListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final VisitReport report;

    @FXML
    private HBox visitCardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label remarks;

    public VisitCard(VisitReport report, int displayedIndex) {
        super(FXML);
        this.report = report;
        id.setText(displayedIndex + ". ");
        name.setText(report.getName());
        date.setText("Visit Date: " + report.date);
        if (report.getRemarks() == null || report.getRemarks().isEmpty()) {
            remarks.setText("Remarks:\nnone");
        } else {
            remarks.setText("Remarks:\n" + report.getRemarks());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VisitCard)) {
            return false;
        }

        // state check
        VisitCard card = (VisitCard) other;
        return id.getText().equals(card.id.getText())
                && report.equals(card.report);
    }
}
