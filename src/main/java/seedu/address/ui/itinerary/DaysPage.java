package seedu.address.ui.itinerary;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.itinerary.days.EnterCreateDayCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.NavigationSidebarLeft;
import seedu.address.ui.components.NavigationSidebarRight;
import seedu.address.ui.template.PageWithSidebar;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class DaysPage extends PageWithSidebar<AnchorPane> {

    private static final String FXML = "itinerary/days/DaysPage.fxml";

    @FXML
    private FlowPane dayThumbnailPane;

    @FXML
    private Button addButton;

    @FXML
    VBox sideBarLeft;

    @FXML
    VBox sideBarRight;


    public DaysPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        // nav bar
        sideBarRight.getChildren().clear();
        sideBarLeft.getChildren().clear();
        NavigationSidebarRight navigationSidebarRight = new NavigationSidebarRight(mainWindow);
        NavigationSidebarLeft navigationSidebarLeft = new NavigationSidebarLeft(mainWindow);
        sideBarLeft.getChildren().add(navigationSidebarLeft.getRoot());
        sideBarRight.getChildren().add(navigationSidebarRight.getRoot());

        // Filling Days
        dayThumbnailPane.getChildren().clear();
        List<Day> days = model.getPageStatus().getTrip().getDayList().internalUnmodifiableList;

        List<Node> dayThumbnails = IntStream.range(0, days.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index ->{
                    DayThumbnail dayThumbnail = new DayThumbnail(days.get(index.getZeroBased()), index);
                    return dayThumbnail.getRoot();
                }).collect(Collectors.toList());

        dayThumbnailPane.getChildren().addAll(FXCollections.observableArrayList(dayThumbnails));
    }

    @FXML
    private void handleAddDay() {
        mainWindow.executeGuiCommand(EnterCreateDayCommand.COMMAND_WORD);
    }

    @FXML
    private void handlePreferences() {
        mainWindow.executeGuiCommand(EnterPrefsCommand.COMMAND_WORD);
    }
}
