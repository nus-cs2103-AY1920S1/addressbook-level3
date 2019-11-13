package budgetbuddy.ui.tab;

import static java.util.Objects.requireNonNull;

import budgetbuddy.ui.panel.DisplayPanel;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;

/**
 * Represents an abstract tab component that displays its corresponding panel when selected.
 */
public abstract class PanelTab extends Tab {

    private DisplayPanel primaryPanel;
    private DisplayPanel secondaryPanel;
    private boolean isPrimaryPanelSelected;

    public PanelTab(DisplayPanel panel, String tabName) {
        this(panel, null, tabName);
    }

    public PanelTab(DisplayPanel primaryPanel, DisplayPanel secondaryPanel, String tabName) {
        this.primaryPanel = primaryPanel;
        this.secondaryPanel = secondaryPanel;
        this.isPrimaryPanelSelected = true;

        setClosable(false);
        setContent(primaryPanel.getRoot());

        // Create tab
        Label label = new Label(tabName);
        label.setMinWidth(Region.USE_PREF_SIZE);

        setGraphic(label);
    }

    public void setPrimaryPanel() {
        setContent(primaryPanel.getRoot());
        isPrimaryPanelSelected = true;
    }

    public void setSecondaryPanel() {
        requireNonNull(secondaryPanel);
        setContent(secondaryPanel.getRoot());
        isPrimaryPanelSelected = false;
    }

    public boolean hasSecondaryPanel() {
        return secondaryPanel != null;
    }

    public boolean isPrimaryPanelSelected() {
        return isPrimaryPanelSelected;
    }
}
