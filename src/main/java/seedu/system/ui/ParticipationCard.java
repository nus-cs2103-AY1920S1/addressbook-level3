package seedu.system.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.system.commons.core.LogsCenter;
import seedu.system.model.participation.Participation;

/**
 * An UI component that displays information of a {@code Participation}.
 */
public class ParticipationCard extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ParticipationCard.class);
    private static final String FXML = "ParticipationListCard.fxml";

    private final Participation participation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label competition;
    @FXML
    private Label attempts;

    public ParticipationCard(Participation participation, int displayedIndex) {
        super(FXML);
        this.participation = participation;
        id.setText(displayedIndex + ". ");
        name.setText(participation.getPerson().getName().toString());
        competition.setText(participation.getCompetition().toString());
        attempts.setText(participation.getThreeLiftScore());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ParticipationCard)) {
            return false;
        }

        // state check
        ParticipationCard card = (ParticipationCard) other;
        return id.getText().equals(card.id.getText())
                && participation.equals(card.participation);
    }
}
