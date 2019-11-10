package seedu.sugarmummy.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import seedu.sugarmummy.logic.Logic;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.biography.User;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.time.YearMonth;
import seedu.sugarmummy.model.time.YearMonthDay;
import seedu.sugarmummy.ui.achievements.AchievementsPane;
import seedu.sugarmummy.ui.biography.BioPane;
import seedu.sugarmummy.ui.calendar.CalendarEntryRawList;
import seedu.sugarmummy.ui.calendar.CalendarMonthScrollPanel;
import seedu.sugarmummy.ui.recmf.FoodFlowPanel;
import seedu.sugarmummy.ui.records.RecordListPanel;
import seedu.sugarmummy.ui.statistics.AverageGraphPane;

/**
 * This is a class that stores and processes the possible Main Display Panes to be displayed to the user.
 */
public class MainDisplayPane {

    private Map<DisplayPaneType, UiPart<Region>> typeToPaneMap;
    private DisplayPaneType currPaneType;
    private Logic logic;

    public MainDisplayPane(Logic logic) {
        this.logic = logic;
        typeToPaneMap = new HashMap<>();
    }

    /**
     * Returns a UiPart representing the Main Display Pane observed by the user.
     *
     * @param displayPaneType      an enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneIsToBeCreated boolean indicating whether a new pane is to be created, regardless of whether a pane
     *                             already exists.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    public UiPart<Region> get(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated) {
        assert !displayPaneType.equals(DisplayPaneType.CALENDAR_MONTH);
        switch (displayPaneType) {
        case BIO:
            ObservableList<User> filteredUserList = logic.getFilteredUserList();
            BioPane previousBioPane = (BioPane) typeToPaneMap.get(DisplayPaneType.BIO);
            Image previousDp = previousBioPane != null ? previousBioPane.getImg() : null;

            if (!filteredUserList.isEmpty() && previousDp != null && filteredUserList.get(0).getDpPath().toString()
                    .equals(previousBioPane.getDpPath())) {
                return getMappedPane(displayPaneType, () -> new BioPane(filteredUserList, previousDp,
                                logic.getFontColour(), logic.getBackground()),
                        newPaneIsToBeCreated);
            } else {
                return getMappedPane(displayPaneType, () -> new BioPane(filteredUserList,
                                logic.getFontColour(), logic.getBackground()),
                        newPaneIsToBeCreated);
            }
        case ACHVM:
            Map<RecordType, List<Achievement>> achievementsMap = logic.getAchievementsMap();
            AchievementsPane previousAchievementsPane = (AchievementsPane) typeToPaneMap.get(DisplayPaneType.ACHVM);
            Map<RecordType, List<Achievement>> previousMap = previousAchievementsPane != null
                    ? previousAchievementsPane.getAchievementsMap()
                    : null;
            if (!achievementsMap.isEmpty() && previousMap != null && logic.currAchievementsMapIsSameAs(previousMap)) {
                return getMappedPane(displayPaneType, () -> new AchievementsPane(achievementsMap,
                                logic.getFilteredUserList()),
                        false);
            } else {
                return getMappedPane(displayPaneType, () -> new AchievementsPane(achievementsMap,
                                logic.getFilteredUserList()),
                        newPaneIsToBeCreated);
            }

        case CHANGE_FOOD:
        case RESET_FOOD:
            return getMappedPane(displayPaneType, () -> new FoodFlowPanel(logic.getFoodList()),
                newPaneIsToBeCreated);

        case RECM_FOOD:
            return getMappedPane(displayPaneType, () -> new FoodFlowPanel(logic.getFilterFoodList()),
                    newPaneIsToBeCreated);

        case RECM_MIXED_FOOD:
            return getMappedPane(displayPaneType, () -> new FoodFlowPanel(logic.getMixedFoodList()),
                    true);

        case ADD:
        case LIST:
        case DELETE:
            return getMappedPane(displayPaneType, () -> new RecordListPanel(logic.getFilterRecordList()),
                    newPaneIsToBeCreated);
        case AVERAGE:
            return getMappedPane(displayPaneType, () -> new AverageGraphPane(logic.getAverageMap(),
                    logic.getAverageType(), logic.getRecordType()), newPaneIsToBeCreated);
        case CALENDAR_ENTRY:
            return getMappedPane(displayPaneType, () -> new CalendarEntryRawList(logic.getFilteredCalendarEntryList()),
                    newPaneIsToBeCreated);
        case NONE:
            return null;
        default:
            assert false : displayPaneType + " is not recognised inside MainDisplayPane class.";
            return null;
        }
    }

    /**
     * Returns a calendar pane representing the Main Display Pane observed by the user.
     */
    public UiPart<Region> get(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated,
            YearMonth yearMonth, Optional<YearMonthDay> yearMonthDay, boolean isShowingWeek) {
        return getMappedPane(displayPaneType, () -> new CalendarMonthScrollPanel(yearMonth, yearMonthDay, isShowingWeek,
                logic.getFilteredCalendarEntryList(), logic.getToday()), newPaneIsToBeCreated);

    }

    /**
     * Returns a UiPart to be displayed to the user, after adding it to the map of display panes, if not yet added.
     *
     * @param displayPaneType   An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneSupplier   A Supplier object containing the UiPart to be returned if a mapping for it does not
     *                          exist yet, unless new pane is given to be created regardless.
     * @param isToCreateNewPane Boolean indicating whether a new pane is to be created, regardless of whether a pane
     *                          of the same type already exists.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    private UiPart<Region> getMappedPane(DisplayPaneType displayPaneType,
            Supplier<UiPart<Region>> newPaneSupplier, boolean isToCreateNewPane) {
        UiPart<Region> mappedPane = typeToPaneMap.get(displayPaneType);
        currPaneType = displayPaneType;
        if (mappedPane == null || isToCreateNewPane) {
            mappedPane = newPaneSupplier.get();
            typeToPaneMap.put(displayPaneType, mappedPane);
        }
        return mappedPane;
    }

    public DisplayPaneType getCurrPaneType() {
        return currPaneType;
    }
}
