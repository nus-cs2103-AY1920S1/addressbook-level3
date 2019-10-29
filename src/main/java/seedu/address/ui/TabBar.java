package seedu.address.ui;

import javafx.application.Platform;
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

    private int selectedIndex = 0;

    @FXML
    private TilePane tabBar;

    public TabBar(OmniPanel omniPanel) {
        super("TabBar.fxml");

        ObservableList<Node> ols = tabBar.getChildren();

        Platform.runLater(() -> omniPanel.setOmniPanelTab(OmniPanelTab.tabOfIndex(selectedIndex)));

        tabBar.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int size = ols.size();

                switch (keyEvent.getCode()) {
                case UP:
                    selectedIndex = (size + selectedIndex + -1) % size;
                    keyEvent.consume();
                    break;
                case DOWN:
                    selectedIndex = (size + selectedIndex + 1) % size;
                    keyEvent.consume();
                    break;
                case RIGHT:
                    omniPanel.regainOmniPanelSelector();
                    break;
                default:
                }
                selectTabUsingIndex(selectedIndex);
                omniPanel.setOmniPanelTab(OmniPanelTab.tabOfIndex(selectedIndex));
            }
        });

        ols.forEach(iv -> iv.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tabBar.requestFocus();
                selectedIndex = ols.indexOf(event.getTarget());
                selectTabUsingIndex(selectedIndex);
                omniPanel.setOmniPanelTab(OmniPanelTab.tabOfIndex(selectedIndex));
            }
        }));
    }

    /**
     * Selects the TabBar's tile using index.
     */
    public void selectTabUsingIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        ObservableList<Node> ols = tabBar.getChildren();
        ols.forEach(iv -> iv.getStyleClass().setAll("unselected-tab"));
        ols.get(selectedIndex).getStyleClass().setAll("selected-tab");
    }
}
