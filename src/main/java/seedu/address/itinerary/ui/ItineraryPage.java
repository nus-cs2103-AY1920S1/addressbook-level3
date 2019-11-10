package seedu.address.itinerary.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.logic.ItineraryLogic;
import seedu.address.itinerary.logic.parser.ItineraryParser;
import seedu.address.itinerary.storage.ItineraryStorage;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CodeWindow;
import seedu.address.ui.CommandBox;
import seedu.address.ui.HelpWindow;
import seedu.address.ui.Page;
import seedu.address.ui.PageManager;
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

    private TagDropdown tagDropdown;

    private HelpWindow helpWindow;

    private CodeWindow codeWindow;

    private ItineraryStorage itineraryStorage;

    private ItineraryLogic itineraryLogic;

    private Stage primaryStage;

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
    private ClearCommandWindow clearCommandWindow;

    private String[] possibleSuggestions = {
        // For basic command
        "greet", "summary", "goto calendar", "goto financial_tracker", "goto diary",
        "goto main", "goto achievements", "goto address_book", "exit", "list", "help", "history", "clear",
        // For the add command
        "add title/", "add title/ date/ time/ l/ d/",
        // For the edit, done and delete command
        "delete ", "edit ", "done ",
        // For the sort event
        "sort by/", "title", "location", "completion", "priority", "chronological",
        // For the search event
        "search", "search title/ date/ time/ l/ d/ tag/"
    };

    public ItineraryPage(ItineraryLogic itineraryLogic) {
        super(fxmlWindow);
        this.primaryStage = primaryStage;
        this.itineraryParser = new ItineraryParser();

        this.itineraryLogic = itineraryLogic;

        setAccelerators();

        this.helpWindow = new HelpWindow();
        this.codeWindow = new CodeWindow();

        fillInnerParts();
        this.clearCommandWindow = new ClearCommandWindow(itineraryLogic);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {

        eventPanel = new EventPanel(itineraryLogic.getSortedEventList());
        eventPlaceHolder.getChildren().add(eventPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, itineraryLogic.getActionList(),
                possibleSuggestions);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        tagDropdown = new TagDropdown();

        // ------------- defining HBox layout --------------
        HBox hBox = new HBox();
        hBox.getChildren().addAll(commandBox.getRoot(), tagDropdown.getRoot());
        hBox.setSpacing(10);
        HBox.setHgrow(commandBox.getRoot(), Priority.ALWAYS);
        // ------------- defining HBox layout --------------

        commandBoxPlaceholder.getChildren().add(hBox);

    }

    /**
     * Opens the code window or focuses on it if it's already opened.
     */
    @FXML
    public void handleCode() {
        if (!codeWindow.isShowing()) {
            codeWindow.show();
        } else {
            codeWindow.focus();
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the clear command window or focuses on it if it's already opened.
     */
    @FXML
    public void handleClear() {
        if (!clearCommandWindow.isShowing()) {
            clearCommandWindow.show();
        } else {
            clearCommandWindow.focus();
        }
    }

    @Override
    public void closeResources() {
        clearCommandWindow.hide();
        helpWindow.hide();
        codeWindow.hide();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        closeResources();
        PageManager.closeWindows();
    }

    @Override
    public PageType getPageType() {
        return pageType;
    }

    /**
     * Executes the command and returns the result.
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {

            //update tagging dropDown menu
            tagDropdown.updateDropdownText();
            CommandResult commandResult = itineraryLogic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowClear()) {
                handleClear();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        itineraryPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    public String[] getPossibleSuggestions() {
        return possibleSuggestions;
    }

    @Override
    public Parent getParent() {
        return itineraryPane;
    }
}
