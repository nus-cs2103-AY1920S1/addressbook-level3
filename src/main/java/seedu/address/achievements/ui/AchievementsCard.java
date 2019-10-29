package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.achievements.logic.AchievementsLogic;
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

    public AchievementsCard(AchievementsLogic logic) {
        super(FXML);
        logger.info("" + logic.getTotalPersons());
        cardPlaceholder.getChildren().addAll(
                                             new AchievementsTitleLabel("Address Book").getRoot(),
                                             new AchievementsDataLabel("Total Contacts: ", ""
                                                     + logic.getTotalPersons(), "200").getRoot(),
                                             new AchievementsProgressBar(logic.getTotalPersons() / 200.0).getRoot(),
                                             new AchievementsHorizontalBarChart("Contacts",
                                                                                "Countries",
                                                                                "Number",
                                                                                logic.getAddressChartData()).getRoot()
                                            //new AchievementsVerticalBarChart("Contacts",
                                            //                                 "Countries",
                                            //                                 "Number").getRoot(),
                                            //new AchievementsPieChart("Countries").getRoot()
        );
    }
}
