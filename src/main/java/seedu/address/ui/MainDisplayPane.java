package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.YearMonth;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.YearMonthDay;
import seedu.address.ui.bio.BioPane;
import sugarmummy.recmfood.ui.FoodFlowPanel;

/**
 * A class containing enumerations, storing the possible Main Display Panes to be displayed to the user.
 */
public class MainDisplayPane {

    private Map<DisplayPaneType, UiPart<Region>> map;
    private DisplayPaneType currPaneType;
    private Logic logic;

    public MainDisplayPane(Logic logic) {
        this.logic = logic;
        map = new HashMap<>();
    }

    /**
     * Returns a UiPart representing the Main Display Pane observed by the user.
     *
     * @param displayPaneType      An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneIsToBeCreated Boolean indicating whether a new pane is to be created, regardless of whether a pane
     *                             already exists.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    public UiPart<Region> get(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated) {
        assert !displayPaneType.equals(DisplayPaneType.CALENDAR_MONTH);
        switch (displayPaneType) {
        case BIO:
            ObservableList<User> filteredUserList = logic.getFilteredUserList();
            BioPane previousPane = (BioPane) map.get(DisplayPaneType.BIO);
            Image previousDp = previousPane != null ? previousPane.getImg() : null;

            if (!filteredUserList.isEmpty() && previousDp != null && filteredUserList.get(0).getDpPath().toString()
                .equals(previousPane.getDpPath())) {
                return getMappedPane(displayPaneType, () -> new BioPane(filteredUserList, previousDp,
                        logic.getFontColour(), logic.getBackground()),
                    newPaneIsToBeCreated);
            } else {
                return getMappedPane(displayPaneType, () -> new BioPane(filteredUserList,
                        logic.getFontColour(), logic.getBackground()),
                    newPaneIsToBeCreated);
            }
        case ACHVM:
            return getMappedPane(displayPaneType, AchievementsPane::new, newPaneIsToBeCreated);
        case RECM_FOOD:
            return getMappedPane(displayPaneType, () -> new FoodFlowPanel(logic.getFilterFoodList()),
                newPaneIsToBeCreated);
        case ADD:
        case LIST:
        case DELETE:
            return getMappedPane(displayPaneType, () -> new RecordListPanel(logic.getFilterRecordList()),
                newPaneIsToBeCreated);
        case AVERAGE:
            return getMappedPane(displayPaneType, () -> new AverageGraphPane(logic.getAverageMap(),
                logic.getAverageType(), logic.getRecordType()), newPaneIsToBeCreated);
        default:
            return null;
        }
    }

    /**
     * Returns a calendar pane representing the Main Display Pane observed by the user.
     */
    public UiPart<Region> get(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated,
                              YearMonth yearMonth, Optional<YearMonthDay> yearMonthDay, boolean isShowingWeek) {
        return getMappedPane(displayPaneType, () -> new CalendarMonthScrollPanel(yearMonth, yearMonthDay, isShowingWeek,
            logic.getFilteredCalendarEntryList()), newPaneIsToBeCreated);

    }

    /**
     * Returns a UiPart to be displayed to the user, after adding it to the map of display panes, if not yet added.
     *
     * @param displayPaneType      An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneSupplier      A Supplier object containing the UiPart to be returned if a mapping for it does not
     *                             exist yet, unless new pane is given to be created regardless.
     * @param newPaneIsToBeCreated Boolean indicating whether a new pane is to be created, regardless of whether a pane
     *                             of the same type already exists.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    private UiPart<Region> getMappedPane(DisplayPaneType displayPaneType,
                                         Supplier<UiPart<Region>> newPaneSupplier, boolean newPaneIsToBeCreated) {
        UiPart<Region> mappedPane = map.get(displayPaneType);
        currPaneType = displayPaneType;
        if (mappedPane == null || newPaneIsToBeCreated == true) {
            mappedPane = newPaneSupplier.get();
            map.put(displayPaneType, mappedPane);
        }
        return mappedPane;
    }

    /**
     * Returns a UiPart to be displayed to the user. If a panel of the same type already exists, it simply returns the
     * existing panel.
     *
     * @param displayPaneType An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneSupplier A Supplier object containing the UiPart to be returned if a mapping for it does not exist
     *                        yet.
     * @return A UiPart representing the Main Display Pane observed by the user, and is simply the existing part of the
     *     same type if it already exists in the mapping of this MainDisplayPane object.
     */
    private UiPart<Region> getMappedPane(DisplayPaneType displayPaneType,
                                         Supplier<UiPart<Region>> newPaneSupplier) {
        return getMappedPane(displayPaneType, newPaneSupplier, false);
    }


    public DisplayPaneType getCurrPaneType() {
        return currPaneType;
    }
}
