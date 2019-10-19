package seedu.address.itinerary.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.commands.Command;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.parser.ItineraryParser;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.*;

import java.util.logging.Logger;

public class ItineraryPage extends UiPart<VBox> implements Page {
    private final static PageType pageType = PageType.ITINERARY;
    private final static String fxmlWindow = "ItineraryWindow.fxml";

    private ResultDisplay resultDisplay;
    private EventPanel eventPanel;
    private final Logger logger = LogsCenter.getLogger(getClass());
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
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            Command command = itineraryParser.parseCommand(commandText);
            CommandResult commandResult = command.execute(model);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                //handleHelp();
            }

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
