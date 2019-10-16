package seedu.address.ui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import seedu.address.logic.commands.diary.EnterDiaryCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * Abstraction of a vertical sidebar displayed on the right side.
 */
public class NavigationSidebarRight extends UiPart<Region> {

    private static final String FXML = "/components/SidebarRight.fxml";

    private MainWindow mainWindow;

    public NavigationSidebarRight(MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
    }

    @FXML
    void handleEnterItinerary() {
        mainWindow.executeGuiCommand(EnterItineraryPageCommand.COMMAND_WORD);
    }

    @FXML
    private void handleEnterContactsManager() {

    }

    @FXML
    private void handleEnterExpenditureManager() {

    }

    @FXML
    private void handleEnterDiary() {
        mainWindow.executeGuiCommand(EnterDiaryCommand.COMMAND_WORD);
    }

}
