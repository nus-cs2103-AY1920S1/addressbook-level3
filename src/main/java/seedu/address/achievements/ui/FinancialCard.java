package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.commons.core.LogsCenter;
import seedu.address.financialtracker.model.util.FinancialTrackerStatistics;

/**
 * An UI component that displays the various achievements in a card.
 */
public class FinancialCard {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Card for {@link FinancialTrackerStatistics}.
     * @param financialTrackerStatistics
     * @return observable list of nodes that makes up the {@code FinancialCard}
     */
    public static ObservableList<Node> make(FinancialTrackerStatistics financialTrackerStatistics) {
        return FXCollections.observableArrayList(
                new AchievementsTitleLabel("Financial Tracker").getRoot(),
                new AchievementsVerticalBarChart("Total Spending", "Countries",
                        "Amount", financialTrackerStatistics.getFinancialBarChartData()).getRoot(),
                new AchievementsPieChart("Total Spending",
                        financialTrackerStatistics.getFinancialPieChartData()).getRoot());
    }
}
