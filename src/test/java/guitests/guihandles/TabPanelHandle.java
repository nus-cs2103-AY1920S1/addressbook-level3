package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provide a handle for {@Code TabPanel}
 */
public class TabPanelHandle extends NodeHandle<Node> {
    public static final String TAB_PANEL_FIELD_ID = "#tabPanel";



    protected TabPanelHandle(Node rootNode) {
        super(rootNode);
    }
}
