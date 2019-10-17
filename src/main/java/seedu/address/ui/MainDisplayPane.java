package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.scene.layout.Region;

/**
 * A class containing enumerations, storing the possible Main Display Panes to be displayed to the user.
 */
public class MainDisplayPane {

    /**
     * Enumerated main display pane names that are used as keys to generate UiParts to be displayed.
     */
    enum DisplayPane {
        MAIN,
        BIO,
        ACHVM;
    }

    private Map<DisplayPane, UiPart<Region>> map;
    private DisplayPane currPane;

    public MainDisplayPane() {
        map = new HashMap<>();
    }

    public MainDisplayPane(DisplayPane displayPane, UiPart<Region> mainPane) {
        this();
        map.put(displayPane, mainPane);
        currPane = displayPane;
    }

    /**
     * Returns a UiPart representing the Main Display Pane observed by the user.
     * @param displayPane An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    public UiPart<Region> get(DisplayPane displayPane) {
        switch (displayPane) {
        case MAIN:
            return getMappedPane(displayPane, () -> null);
        case BIO:
            return getMappedPane(displayPane, BioPane::new);
        case ACHVM:
            return getMappedPane(displayPane, AchievementsPane::new);
        default:
            return null;
        }
    }

    /**
     * Returns a UiPart to be displayed to the user, after adding it to the map of display panes, if not yet added.
     * @param displayPane An enumerated display pane to retrieve or store the corresponding type of UiPart.
     * @param newPaneSupplier A Supplier object containing the UiPart to be returned if a mapping for it does
     *                        not exist yet.
     * @return A UiPart representing the Main Display Pane observed by the user.
     */
    private UiPart<Region> getMappedPane(DisplayPane displayPane, Supplier<UiPart<Region>> newPaneSupplier) {
        UiPart<Region> mappedPane = map.get(displayPane);
        currPane = displayPane;
        if (mappedPane != null) {
            return mappedPane;
        } else {
            mappedPane = newPaneSupplier.get();
            map.put(displayPane, mappedPane);
            return mappedPane;
        }
    }


    public DisplayPane getCurrPane() {
        return currPane;
    }
}
