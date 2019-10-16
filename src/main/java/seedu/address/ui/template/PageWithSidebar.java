package seedu.address.ui.template;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.NavigationSidebarLeft;
import seedu.address.ui.components.NavigationSidebarRight;

/**
 * Template class for windows with the navigation sidebar.
 */
public abstract class PageWithSidebar<T extends Node> extends Page<T> {
    @FXML
    protected VBox sideBarLeft;

    @FXML
    protected VBox sideBarRight;

    public PageWithSidebar(String fxmlFileName, MainWindow mainWindow, Logic logic, Model model) {
        super(fxmlFileName, mainWindow, logic, model);

        //Instantiate navbar
        sideBarRight.getChildren().clear();
        sideBarLeft.getChildren().clear();
        NavigationSidebarRight navigationSidebarRight = new NavigationSidebarRight(mainWindow);
        NavigationSidebarLeft navigationSidebarLeft = new NavigationSidebarLeft(mainWindow);
        sideBarLeft.getChildren().add(navigationSidebarLeft.getRoot());
        sideBarRight.getChildren().add(navigationSidebarRight.getRoot());
    }
}
