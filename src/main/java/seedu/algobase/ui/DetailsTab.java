package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;

/**
 * An UI component that displays tab content.
 */
public class DetailsTab extends UiPart<Region> {

    private static final String FXML = "DetailsTab.fxml";

    @FXML
    private Tab tabContentPlaceholder;

    public DetailsTab(String name) {
        super(FXML);
        tabContentPlaceholder = new Tab(name);
    }

    public DetailsTab(String name, UiPart<Region> uiPart) {
        super(FXML);
        tabContentPlaceholder = new Tab(name, uiPart.getRoot());
    }

    public Tab getTab() {
        return tabContentPlaceholder;
    }
}
