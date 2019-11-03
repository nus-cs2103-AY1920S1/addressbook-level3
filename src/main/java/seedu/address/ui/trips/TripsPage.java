package seedu.address.ui.trips;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.HelpCommand;
import seedu.address.logic.commands.trips.EnterCreateTripCommand;
import seedu.address.logic.commands.trips.EnterTripCommand;
import seedu.address.model.Model;
import seedu.address.model.trip.Trip;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.Page;

/**
 * {@code Page} class for displaying the trip manager page.
 */
public class TripsPage extends Page<AnchorPane> {

    private static final String FXML = "trips/TripsPage.fxml";
    private static final int MAX_COLUMNS = 5;

    private int nextRowToFill = 0;
    private int nextColToFill = 0;

    @FXML
    private GridPane tripGridPane;

    @FXML
    private Button addButton;

    public TripsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        tripGridPane.getChildren().clear();
        ObservableList<Trip> trips = model.getTravelPal().getTripList();

        // Sorts trips for display
        SortedList<Trip> tripsSortedList = trips.sorted(Comparator.comparing(Trip::getStartDate));
        model.setPageStatus(model.getPageStatus().withNewSortedOccurrencesList(tripsSortedList));

        List<Node> tripCards = IntStream.range(0, tripsSortedList.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    TripCard tripCard = new TripCard(tripsSortedList.get(index.getZeroBased()), index, model);
                    int column = index.getZeroBased() % MAX_COLUMNS;
                    int row = index.getZeroBased() / MAX_COLUMNS;

                    //set starting positions of the trip cards with index
                    GridPane.setConstraints(tripCard.getRoot(), column, row,
                            1, 1,
                            HPos.CENTER, VPos.CENTER);

                    tripCard.getRoot().addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    mainWindow.executeGuiCommand(EnterTripCommand.COMMAND_WORD
                                            + " " + index.getOneBased());
                                }
                            });
                    return tripCard.getRoot();
                }).collect(Collectors.toList());

        tripGridPane.getChildren().addAll(FXCollections.observableArrayList(tripCards));
    }

    @FXML
    private void handleAddTrip() {
        mainWindow.executeGuiCommand(EnterCreateTripCommand.COMMAND_WORD);
    }

    @FXML
    private void handleHelp() {
        mainWindow.executeGuiCommand(HelpCommand.COMMAND_WORD);
    }

}
