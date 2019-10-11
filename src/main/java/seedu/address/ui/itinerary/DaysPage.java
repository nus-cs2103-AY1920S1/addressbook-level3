package seedu.address.ui.itinerary;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.itinerary.days.EnterCreateDayCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.Page;
import seedu.address.ui.template.PageWithSidebar;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class DaysPage extends Page<AnchorPane> {

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
