package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.model.util.DiaryBookStatistics;

/**
 * An UI component that displays the various achievements in a card.
 */
public class DiaryBookCard {

    private final Logger logger = LogsCenter.getLogger(getClass());

    public static ObservableList<Node> make(DiaryBookStatistics diaryBookStatistics) {
        return FXCollections.observableArrayList(
                new AchievementsTitleLabel("Diary Book").getRoot(),
                new AchievementsDataLabel("Total Diary Entries: ",
                        "" + diaryBookStatistics.getTotalDiaryEntries() + " / 200").getRoot(),
                new AchievementsProgressBar(diaryBookStatistics.getTotalDiaryEntries() / 200.0).getRoot(),
                new AchievementsVerticalBarChart("Diary Entries", "Date", "Number",
                        diaryBookStatistics.getDiaryBarChart()).getRoot());
    }
}
