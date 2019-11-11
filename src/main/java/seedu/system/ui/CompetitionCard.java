package seedu.system.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.system.model.competition.Competition;

/**
 * An UI component that displays information of a {@code Competition}.
 */
public class CompetitionCard extends UiPart<Region> {
    private static final String FXML = "CompetitionListCard.fxml";
    public final Competition competition;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    public CompetitionCard(Competition competition, int displayedIndex) {
        super(FXML);
        this.competition = competition;
        id.setText(displayedIndex + ". ");
        name.setText(competition.getName().toString());
        startDate.setText("Start Date: " + competition.getStartDate().toString());
        endDate.setText("End Date: " + competition.getEndDate().toString());

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        CompetitionCard card = (CompetitionCard) other;
        return id.getText().equals(card.id.getText())
                && competition.equals(card.competition);
    }
}
