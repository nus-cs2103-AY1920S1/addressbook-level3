package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.scene.layout.Region;
import seedu.address.logic.Logic;

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
        currPaneType = DisplayPaneType.MAIN;
    }

    /**
     * Returns a UiPart representing the Main Display Pane observed by the user.
     * @param displayPaneType An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneIsToBeCreated Boolean indicating whether a new pane is to be created, regardless of whether a pane
     *                           already exists.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    public UiPart<Region> get(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated) {
        switch (displayPaneType) {
        case MAIN:
            return getMappedPane(displayPaneType, () -> new PersonListPanel(logic.getFilteredPersonList()),
                    newPaneIsToBeCreated);
        case BIO:
            return getMappedPane(displayPaneType, () -> new BioPane(logic.getFilteredUserList()), newPaneIsToBeCreated);
        case ACHVM:
            return getMappedPane(displayPaneType, AchievementsPane::new, newPaneIsToBeCreated);
        case RECM_FOOD:
            return getMappedPane(displayPaneType, () -> new FoodFlowPanel(logic.getFilterFoodList()),
                    newPaneIsToBeCreated);
        case ADD:
            return getMappedPane(displayPaneType, () -> new RecordListPanel(logic.getFilterRecordList()),
                    newPaneIsToBeCreated);
        default:
            return null;
        }
    }

    /**
     * Returns a UiPart to be displayed to the user, after adding it to the map of display panes, if not yet added.
     * @param displayPaneType An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneSupplier A Supplier object containing the UiPart to be returned if a mapping for it does
     *                        not exist yet.
     * @param newPaneIsToBeCreated Boolean indicating whether a new pane is to be created, regardless of whether a
     *                           pane of the same type already exists.
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


    public DisplayPaneType getCurrPaneType() {
        return currPaneType;
    }
}
