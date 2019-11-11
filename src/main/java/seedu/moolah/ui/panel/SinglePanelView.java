package seedu.moolah.ui.panel;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.moolah.ui.UiPart;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

/**
 * Represents a Panel Manager and Viewer UiPart in the GUI which allows a single panel to be shown at a time.
 * Supports operations to enable switching which Panel is currently visible to the user in the GUI.
 */
public class SinglePanelView extends UiPart<Region> implements PanelManager {

    public static final String FXML = "PanelView.fxml";
    private Map<PanelName, Panel> panelNamePanelHashMap;
    private Panel currentPanel;
    private PanelName currentPanelName;
    @FXML
    private StackPane panelPlaceholder;

    /**
     * Constructor for the PanelView.
     */
    public SinglePanelView() {
        super(FXML);
        panelNamePanelHashMap = new HashMap<>();
        currentPanel = new PlaceholderPanel();
        currentPanelName = null;
    }

    @Override
    public void viewPanel(PanelName panelName) throws UnmappedPanelException {
        requireNonNull(panelName);
        PanelName toView = panelName.equals(PanelName.CURRENT) ? currentPanelName : panelName;
        if (!hasPanel(toView)) {
            throw new UnmappedPanelException(toView);
        }
        // hide all panels
        for (Panel p : panelNamePanelHashMap.values()) {
            p.hide();
        }
        currentPanel = getPanel(toView);
        // check to ensure nonnull
        if (currentPanel == null) {
            currentPanel = new PlaceholderPanel();
            setPanel(toView, currentPanel);
        }
        // update property
        currentPanelName = toView;
        // view
        currentPanel.view();
    }

    @Override
    public Panel getCurrentPanel() {
        return panelNamePanelHashMap.get(currentPanelName);
    }

    @Override
    public PanelName getCurrentPanelName() {
        return currentPanelName;
    }

    // -------- PanelManager Method Implementations ------------------

    @Override
    public boolean setPanel(PanelName panelName, Panel panel) {
        requireNonNull(panelName);
        requireNonNull(panel);
        requireNonNull(panel.getRoot());
        // if contains the same panel name
        // 1. hide the original panel
        // 2. remove the panel from the stackpane
        if (panelNamePanelHashMap.containsKey(panelName)) {
            Panel toOverwrite = panelNamePanelHashMap.get(panelName);
            toOverwrite.hide();
            panelPlaceholder.getChildren().remove(toOverwrite.getRoot());
        }

        // map the panel to the panel name
        panelNamePanelHashMap.put(panelName, panel);
        // put the panel in the stackpane
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
        if (p.equals(getCurrentPanel())) {
            currentPanel = new PlaceholderPanel();
        }
        p.hide();
        panelNamePanelHashMap.remove(panelName);
        panelPlaceholder.getChildren().remove(p.getRoot());

        return p;
    }

    @Override
    public String toString() {
        return panelNamePanelHashMap
                .keySet().stream().map(PanelName::toString)
                .collect(Collectors.joining("\n"));
    }
}
