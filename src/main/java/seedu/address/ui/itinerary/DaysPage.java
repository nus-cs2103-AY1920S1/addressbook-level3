package seedu.address.ui.itinerary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.itinerary.days.EnterCreateDayCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} class implementing the days view page.
 */
public class DaysPage extends PageWithSidebar<AnchorPane> {

    private static final String FXML = "itinerary/days/DaysPage.fxml";

    @FXML
    private FlowPane dayThumbnailPane;

    @FXML
    private Button addButton;

    public DaysPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        // Filling Days
        dayThumbnailPane.getChildren().clear();
        List<Day> days = model.getPageStatus().getTrip().getDayList().internalUnmodifiableList;

        List<Node> dayThumbnails = IntStream.range(0, days.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    DayThumbnail dayThumbnail = new DayThumbnail(days.get(index.getZeroBased()), index, mainWindow);
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
