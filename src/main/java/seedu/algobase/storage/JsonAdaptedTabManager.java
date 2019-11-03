package seedu.algobase.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.gui.TabManager;

/**
 * Jackson-friendly version of {@link TabManager}.
 */
public class JsonAdaptedTabManager {

    private final int displayTabIndex;
    private final int detailsTabIndex;
    private final List<JsonAdaptedTab> tabsData = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTabManager} with the given Tab Manager.
     */
    @JsonCreator
    public JsonAdaptedTabManager(@JsonProperty("displayTabIndex") int displayTabIndex,
                                 @JsonProperty("detailsTabIndex") int detailsTabIndex,
                                 @JsonProperty("tabsData") List<JsonAdaptedTab> tabs) {
        this.displayTabIndex = displayTabIndex;
        this.detailsTabIndex = detailsTabIndex;
        this.tabsData.addAll(tabs);
    }

    /**
     * Converts a given {@code TabManager} into this class for Jackson use.
     */
    public JsonAdaptedTabManager(TabManager tabManager) {
        this.displayTabIndex = tabManager.getDisplayTabPaneIndex().getValue().intValue();
        this.detailsTabIndex = tabManager.getDetailsTabPaneIndex().getValue().intValue();
        tabsData.addAll(tabManager.getTabsDataList().stream()
            .map(JsonAdaptedTab::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted TabManager object into the model's {@code TabManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TabManager.
     */
    public TabManager toModelType(AlgoBase algoBase) throws IllegalValueException {
        TabManager tabManager = new TabManager();
        for (JsonAdaptedTab jsonAdaptedTab : tabsData) {
            tabManager.openDetailsTab(jsonAdaptedTab.toModelType(algoBase));
        }
        tabManager.switchDisplayTab(Index.fromZeroBased(displayTabIndex));
        tabManager.switchDetailsTab(Index.fromZeroBased(detailsTabIndex));
        return tabManager;
    }
}
