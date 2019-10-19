package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import seedu.address.commons.core.OmniPanelTab;

/**
 * The Ui component for AutoComplete.
 */
public class TabBar extends UiPart<Region> {

    private int selectedIndex;

    @FXML
    private TilePane tabBar;

    public TabBar(OmniPanel omniPanel) {
        super("TabBar.fxml");

        ObservableList<Node> ols = tabBar.getChildren();
        selectedIndex = 0;
        Node selectedNode = ols.get(selectedIndex);

        ols.forEach(iv -> iv.getStyleClass().setAll("unselected-tab"));
        selectedNode.getStyleClass().setAll("selected-tab");
        omniPanel.setOmniPanelTab(OmniPanelTab.valueOfId(selectedNode.getId()));

        tabBar.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int size = ols.size();
                ols.forEach(iv -> iv.getStyleClass().setAll("unselected-tab"));

                switch (keyEvent.getCode()) {
                case UP:
                    selectedIndex = (size + selectedIndex + -1) % size;
                    keyEvent.consume();
                    break;
                case DOWN:
                    selectedIndex = (size + selectedIndex + 1) % size;
                    keyEvent.consume();
                    break;
                default:
                }
                Node selectedNode = ols.get(selectedIndex);
                selectedNode.getStyleClass().setAll("selected-tab");
                omniPanel.setOmniPanelTab(OmniPanelTab.valueOfId(selectedNode.getId()));
            }
        });

        ols.forEach(iv -> iv.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tabBar.requestFocus();
                Node clickedNode = (Node) event.getTarget();
                for (int i = 0; i < ols.size(); i++) {
                    Node selectedNode = ols.get(i);
                    if (selectedNode != clickedNode) {
                        selectedNode.getStyleClass().setAll("unselected-tab");
                        continue;
                    }
                    selectedIndex = i;
                    selectedNode.getStyleClass().setAll("selected-tab");
                    omniPanel.setOmniPanelTab(OmniPanelTab.valueOfId(selectedNode.getId()));
                }
            }
        }));
    }


}
