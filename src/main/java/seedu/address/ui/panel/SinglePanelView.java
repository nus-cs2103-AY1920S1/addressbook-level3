package seedu.address.ui.panel;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;
import seedu.address.ui.panel.exceptions.UnmappedPanelException;

/**
 * Represents a Panel Manager and Viewer UiPart in the GUI which allows a single panel to be shown at a time.
 * Supports operations to enable switching which Panel is currently visible to the user in the GUI.
 */
public class SinglePanelView extends UiPart<Region> implements PanelManager {

    public static final String FXML = "PanelView.fxml";
    private Map<PanelName, Panel> panelNamePanelHashMap;
    private Panel currentPanel;
    @FXML
    private StackPane panelPlaceholder;

    /**
     * Constructor for the PanelView.
     */
    public SinglePanelView() {
        super(FXML);
        panelNamePanelHashMap = new HashMap<>();
        currentPanel = new PlaceholderPanel();
    }

    /**
     * Switches the in-view Panel to the Panel mapped to the specified Panel Name, if there is a Panel
     * mapped to that name.
     * @param panelName The Panel Name used to request a change of the in-view Panel.
     */
    public void viewPanel(PanelName panelName) throws UnmappedPanelException {
        requireNonNull(panelName);
        if (panelName.equals(PanelName.CURRENT)) {
            if (currentPanel != null) {
                throw new UnmappedPanelException(panelName);
            }
        } else if (!hasPanel(panelName)) {
            throw new UnmappedPanelException(panelName);
        }
        for (Panel p : panelNamePanelHashMap.values()) {
            p.hide();
        }
        panelNamePanelHashMap.get(panelName).view();
        currentPanel = getPanel(panelName);
    }

    public Panel getCurrentPanel() {
        return currentPanel;
    }

    // -------- PanelManager Method Implementations ------------------

    @Override
    public boolean setPanel(PanelName panelName, Panel panel) {
        requireNonNull(panelName);
        requireNonNull(panel);
        requireNonNull(panel.getRoot());
        if (panelNamePanelHashMap.containsKey(panelName)) {
            Panel toOverwrite = panelNamePanelHashMap.get(panelName);
            toOverwrite.hide();
            panelPlaceholder.getChildren().remove(toOverwrite.getRoot());
        }
        panelNamePanelHashMap.put(panelName, panel);
        panelPlaceholder.getChildren().add(panel.getRoot());
        // prevent new panel from being shown unless requested to be viewed.
        panel.hide();
        return panelPlaceholder.getChildren().contains(panel.getRoot());
    }

    @Override
    public boolean hasPanel(PanelName panelName) {
        requireNonNull(panelName);
        // hashmap has panel name
        return panelNamePanelHashMap.containsKey(panelName)
                // not mapped to null object
                && panelNamePanelHashMap.get(panelName) != null
                // mapped panel is child of stackPane
                && panelPlaceholder.getChildren().contains(panelNamePanelHashMap.get(panelName).getRoot());
    }

    @Override
    public Panel getPanel(PanelName panelName) throws UnmappedPanelException {
        requireNonNull(panelName);
        if (!hasPanel(panelName)) {
            throw new UnmappedPanelException(panelName);
        }
        return panelNamePanelHashMap.get(panelName);
    }

    @Override
    public Panel removePanel(PanelName panelName) throws UnmappedPanelException {
        if (!panelNamePanelHashMap.containsKey(panelName)) {
            throw new UnmappedPanelException(panelName);
        }

        Panel p = panelNamePanelHashMap.get(panelName);
        p.hide();
        panelNamePanelHashMap.remove(panelName);
        panelPlaceholder.getChildren().remove(p.getRoot());

        return p;
    }

    @Override
    public String toString() {
        StringBuilder available = new StringBuilder();
        for (PanelName panelName : panelNamePanelHashMap.keySet()) {
            available.append(panelName.toString());
            available.append("\n");
        }
        return available.toString();
    }
}
