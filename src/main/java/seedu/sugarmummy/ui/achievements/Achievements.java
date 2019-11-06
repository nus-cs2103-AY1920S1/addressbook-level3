package seedu.sugarmummy.ui.achievements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.ui.UiPart;

/**
 * A ui for the user's list of achievements, displayed in tiles.
 */
public class Achievements extends UiPart<Region> {

    private static final String FXML = "Achievements.fxml";

    @FXML
    private VBox achievementsListVBox;

    public Achievements(Map<RecordType, List<Achievement>> achievementsMap) {
        super(FXML);

        List<RecordType> keyList = new ArrayList<>(achievementsMap.keySet());
        keyList.sort(Comparator.comparing(Enum::toString));

        for (RecordType recordType : keyList) {
            List<Achievement> achievementsList = new ArrayList<>(List.copyOf(achievementsMap.get(recordType)));
            Collections.reverse(achievementsList);
            AchievementsList achievementsListUi = new AchievementsList(recordType, achievementsList);
            achievementsListVBox.getChildren().add(achievementsListUi.getRoot());
        }
    }
}
