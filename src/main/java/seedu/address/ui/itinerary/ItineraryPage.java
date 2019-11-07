package seedu.address.ui.itinerary;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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

    private final String dateFormat = "d/M/y";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

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

    @FXML
    private AnchorPane anchorPane;

    public ItineraryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    @Override
    public void fillPage() {
        nameLabel.setText(model.getPageStatus().getTrip().getName().toString());
        startDateLabel.setText("Arrival: " + model.getPageStatus()
                .getTrip().getStartDate().format(dateFormatter).toString());
        endDateLabel.setText("Start Date: " + model
                .getPageStatus().getTrip().getEndDate().format(dateFormatter).toString());
        destinationLabel.setWrapText(true);
        destinationLabel.setText("Destination: " + model.getPageStatus().getTrip().getDestination().toString());
        totalBudgetLabel.setText("Total Budget: $" + model.getPageStatus().getTrip().getBudget().toString());

        ObservableList<Day> days = model.getPageStatus().getTrip().getDayList().internalUnmodifiableList;
        // Stores the current list being displayed to user in PageStatus
        SortedList<Day> sortedDays = days.sorted(Comparator.comparing(Day::getStartDate));
        model.setPageStatus(model.getPageStatus().withNewSortedOccurrencesList(sortedDays));

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

        // Set background
        boolean isPhotoPresent = model.getPageStatus().getTrip().getPhoto().isPresent();
        if (isPhotoPresent) {
            Image img = model.getPageStatus().getTrip().getPhoto().get().getImage();
            BackgroundImage bgImg = new BackgroundImage(img,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                            false, false, true, true));
            anchorPane.setBackground(new Background(bgImg));
        }
    }
}
