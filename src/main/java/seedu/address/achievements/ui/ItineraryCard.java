package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.model.util.ItineraryStatistics;

/**
 * An UI component that displays the various achievements in a card.
 */
public class ItineraryCard {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Card for {@link ItineraryStatistics}.
     * @param itineraryStatistics
     * @return observable list of nodes that makes up the {@code ItineraryCard}
     */
    public static ObservableList<Node> make(ItineraryStatistics itineraryStatistics) {
        return FXCollections.observableArrayList(
                new AchievementsTitleLabel("Itinerary").getRoot(),
                new AchievementsDataLabel("Total Itinerary Entries: ", ""
                        + itineraryStatistics.getTotalItineraryEntries() + " / 200").getRoot(),
                new AchievementsProgressBar(itineraryStatistics.getTotalItineraryEntries() / 200.0).getRoot(),
                new AchievementsVerticalBarChart("Itinerary Entries",
                        "Date",
                        "Number",
                        itineraryStatistics.getItineraryBarChartData()).getRoot());
    }
}
