package seedu.address.ui.template;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.commands.trips.EnterTripCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Template class for windows with the navigation sidebar.
 */
public abstract class PageWithSidebar<T extends Node> extends Page<T> {
    @FXML
    VBox sideBarLeft;

    @FXML
    VBox sideBarRight;

    public PageWithSidebar(String fxmlFileName, MainWindow mainWindow, Logic logic, Model model) {
        super(fxmlFileName, mainWindow, logic, model);

    }

//    @FXML
//    void handleEnterTripManager() {
//        mainWindow.executeGuiCommand(EnterTripManagerCommand.COMMAND_WORD);
//    }
//
//    @FXML
//    void handleEnterOverallView() {
//        mainWindow.executeGuiCommand(EnterDayPageCommand.COMMAND_WORD);
//    }

//    @FXML
//    private void handleEnterBookingsManager() {
//
//    }

//    @FXML
//    private void handleEnterInventoryManager(){
//
//    }

    @FXML
    void handleEnterItinerary() {
        mainWindow.executeGuiCommand(EnterItineraryPageCommand.COMMAND_WORD);
    }

//    @FXML
//    private void handleEnterContactsManager() {
//
//    }

//    @FXML
//    private void handleEnterExpenditureManager() {
//
//    }
//
//    @FXML
//    private void handleEnterDairy() {
//
//    }


}
