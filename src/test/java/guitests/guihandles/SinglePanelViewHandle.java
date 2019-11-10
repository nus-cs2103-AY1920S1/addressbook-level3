package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provides a handle for {@code SinglePanelView} containing the list of {@code Expense}.
 */
public class SinglePanelViewHandle extends NodeHandle<Node> {

    public static final String SINGLE_PANEL_VIEW_ID = "#panelPlaceholder";

    public SinglePanelViewHandle(Node node) {
        super(node);
    }
}
