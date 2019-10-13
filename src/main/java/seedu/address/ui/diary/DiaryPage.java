package seedu.address.ui.diary;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.NavigationSidebarLeft;
import seedu.address.ui.components.NavigationSidebarRight;
import seedu.address.ui.template.PageWithSidebar;

public class DiaryPage extends PageWithSidebar<BorderPane> {
    private static final String FXML = "diary/DiaryPage.fxml";

    @FXML
    private VBox sideBarLeft;

    @FXML
    private VBox sideBarRight;

    public DiaryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);

        sideBarRight.getChildren().clear();
        sideBarLeft.getChildren().clear();
        NavigationSidebarRight navigationSidebarRight = new NavigationSidebarRight(mainWindow);
        NavigationSidebarLeft navigationSidebarLeft = new NavigationSidebarLeft(mainWindow);
        sideBarLeft.getChildren().add(navigationSidebarLeft.getRoot());
        sideBarRight.getChildren().add(navigationSidebarRight.getRoot());
    }

    @Override
    public void fillPage() {

    }
}
