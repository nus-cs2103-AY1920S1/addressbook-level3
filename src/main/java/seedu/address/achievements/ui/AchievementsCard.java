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
        cardPlaceholder.getChildren().addAll(
                                             new AchievementsTitleLabel("Address Book").getRoot(),
                                             new AchievementsDataLabel("Total Contacts: ",
                                                     logic.getTotalPersons() + " / 200").getRoot(),
                                             new AchievementsProgressBar(logic.getTotalPersons() / 200.0).getRoot(),
                                             new AchievementsHorizontalBarChart("Contacts",
                                                                                "Number",
                                                                                "Countries",
                                                                                logic.getAddressChartData()).getRoot()
                                            //new AchievementsVerticalBarChart("Contacts",
                                            //                                 "Countries",
                                            //                                 "Number").getRoot(),
                                            //new AchievementsPieChart("Countries").getRoot()
        );

        cardPlaceholder.getChildren().addAll(
                new AchievementsTitleLabel("Diary Book").getRoot(),
                new AchievementsDataLabel("Total Diary Entries: ", ""
                        + logic.getTotalDiaryEntries() + " / 200").getRoot(),
                new AchievementsProgressBar(logic.getTotalDiaryEntries() / 200.0).getRoot(),
                new AchievementsLineChart("Diary Entries",
                        "Date",
                        "Number",
                        logic.getDiaryChartData()).getRoot());
        cardPlaceholder.getChildren().addAll(
                new AchievementsTitleLabel("Financial Tracker").getRoot(),
                new AchievementsVerticalBarChart("Total Spending",
                        "Countries",
                        "Amount",
                        logic.getFinancialBarChartData()).getRoot(),
                new AchievementsPieChart("Total Spending", logic.getFinancialPieChartData()).getRoot());

        cardPlaceholder.getChildren().addAll(
                new AchievementsTitleLabel("Itinerary").getRoot(),
                new AchievementsDataLabel("Total Itinerary Entries: ", ""
                        + logic.getTotalItineraryEntries() + " / 200").getRoot(),
                new AchievementsProgressBar(logic.getTotalItineraryEntries() / 200.0).getRoot(),
                new AchievementsLineChart("Itinerary Entries",
                        "Date",
                        "Number",
                        logic.getItineraryLineChartData()).getRoot());

        cardPlaceholder.getChildren().addAll(
                new AchievementsTitleLabel("Calendar").getRoot(),
                new AchievementsDataLabel("Total Number of Trip Days: ", ""
                        + logic.getNumberOfDaysTrip()).getRoot(),
                new AchievementsDataLabel("Total Number of Days Of Vacation: ", ""
                        + logic.getNumberOfDaysVacation()).getRoot(),
                new AchievementsDataLabel("Total Number of Trips: ", ""
                        + logic.getNumberOfTrip()).getRoot(),
                new AchievementsProgressBar(logic.getPercentageTrip()).getRoot());
    }
}
