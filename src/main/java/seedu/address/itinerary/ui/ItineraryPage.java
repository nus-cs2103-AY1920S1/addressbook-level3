package seedu.address.itinerary.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.Model.Model;
import seedu.address.itinerary.commands.Command;
import seedu.address.itinerary.parser.ItineraryParser;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;
import seedu.address.ui.ResultDisplay;
import seedu.address.ui.UiPart;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ItineraryPage extends UiPart<VBox> implements Page {
    private static final PageType pageType = PageType.ITINERARY;
    private static final String fxmlWindow = "ItineraryWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private ResultDisplay resultDisplay;
    private EventPanel eventPanel;
    private ItineraryParser itineraryParser;
    private Model model;

    @FXML
    private Scene itineraryScene;

    @FXML
    private BorderPane itineraryPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane eventPlaceHolder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public ItineraryPage() {
        super(fxmlWindow);
        this.itineraryScene = new Scene(itineraryPane);
        this.itineraryParser = new ItineraryParser();
        this.model = new Model();
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {
        eventPanel = new EventPanel(model.getFilteredEventList());
        eventPlaceHolder.getChildren().add(eventPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    public PageType getPageType() {
        return pageType;
    }

    public Scene getScene() {
        return itineraryScene;
    }

    /**
     * Changes application page.
     */
    @SuppressWarnings("unused")
    @FXML
    private void handlePageChange(CommandResult commandResult) {
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        this.itineraryScene.getWindow().hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see AddressBookLogic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            Command command = itineraryParser.parseCommand(commandText);
            model.addAction(commandText);
            CommandResult commandResult = command.execute(model);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowPage()) {
                handlePageChange(commandResult);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
