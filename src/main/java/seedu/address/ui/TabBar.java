package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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

        Platform.runLater(() -> omniPanel.setOmniPanelTab(OmniPanelTab.tabOfIndex(selectedIndex)));

        ObservableList<Node> ols = tabBar.getChildren();

        tabBar.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            int size = ols.size();

            switch (keyEvent.getCode()) {
            case DOWN:
                selectedIndex = (size + selectedIndex + 1) % size;
                break;
            case UP:
                selectedIndex = (size + selectedIndex - 1) % size;
                break;
            case RIGHT:
                omniPanel.regainOmniPanelSelector();
                return;
            default:
                return;
            }
            keyEvent.consume();
            omniPanel.setOmniPanelTab(OmniPanelTab.tabOfIndex(selectedIndex));
        });

        ols.forEach(iv -> iv.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            omniPanel.setOmniPanelTab(OmniPanelTab.tabOfIndex(ols.indexOf(mouseEvent.getTarget())));
            tabBar.requestFocus();
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
