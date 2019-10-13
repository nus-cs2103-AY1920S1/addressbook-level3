package seedu.address.ui.itinerary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} class implementing the itinerary landing page.
 */
public class ItineraryPage extends PageWithSidebar<AnchorPane> {
    private static final String FXML = "itinerary/ItineraryPage.fxml";

    @FXML
    private HBox dayButtonsContainer;

    @FXML
    private Label nameLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label totalBudgetLabel;

    public ItineraryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    @Override
    public void fillPage() {
        nameLabel.setText(model.getPageStatus().getTrip().getName().toString());
        startDateLabel.setText(model.getPageStatus().getTrip().getStartDate().toString());
        endDateLabel.setText(model.getPageStatus().getTrip().getEndDate().toString());
        //destinationLabel.setText(model.getPageStatus().getTrip().getDestination().toString());
        totalBudgetLabel.setText(model.getPageStatus().getTrip().getBudget().toString());

        List<Day> days = model.getPageStatus().getTrip().getDayList().internalUnmodifiableList;

        List<Node> dayButtons = IntStream.range(0, days.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    Button button = new Button();
                    button.setText(Integer.toString(index.getOneBased()));
                    button.addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    mainWindow.executeGuiCommand(EnterDayCommand.COMMAND_WORD
                                            + " " + index.getOneBased());
                                }
                            });
                    return button;
                }).collect(Collectors.toList());
        dayButtonsContainer.getChildren().clear();
        dayButtonsContainer.getChildren().addAll(FXCollections.observableArrayList(dayButtons));
    }
}
