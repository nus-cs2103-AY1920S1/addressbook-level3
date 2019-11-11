package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays the various achievements in a card.
 */
public class AchievementsCard extends UiPart<Region> {

    private static final String FXML = "achievements/AchievementsCard.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox cardPlaceholder;

    public AchievementsCard(ObservableList<Node> statisticsView) {
        super(FXML);
        cardPlaceholder.getChildren().addAll(statisticsView);
    }
}
