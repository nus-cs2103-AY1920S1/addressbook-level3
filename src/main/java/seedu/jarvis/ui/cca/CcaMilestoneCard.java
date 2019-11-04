package seedu.jarvis.ui.cca;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.ui.UiPart;

/**
 * An UI component that displays information of a {@code CcaMilestone}.
 */
public class CcaMilestoneCard extends UiPart<Region> {

    private static final String FXML = "CcaMilestoneCard.fxml";

    @FXML
    private Label ccaMilestoneName;

    private final CcaMilestone ccaMilestone;

    public CcaMilestoneCard(CcaMilestone ccaMilestone) {
        super(FXML);
        this.ccaMilestone = ccaMilestone;
        ccaMilestoneName.setText(ccaMilestone.toString());
    }
}
