package seedu.address.itinerary.ui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.itinerary.model.Itinerary;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.ReadOnlyItinerary;
import seedu.address.itinerary.parser.ItineraryParser;
import seedu.address.itinerary.storage.ItineraryStorage;
import seedu.address.itinerary.storage.JsonItineraryStorage;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CodeWindow;
import seedu.address.ui.CommandBox;
import seedu.address.ui.HelpWindow;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;
import seedu.address.ui.ResultDisplay;
import seedu.address.ui.UiPart;

import static seedu.address.address.logic.AddressBookLogicManager.FILE_OPS_ERROR_MESSAGE;

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

    private TagDropdown tagDropdown;

    private Stage primaryStage;

    private HelpWindow helpWindow;

    private CodeWindow codeWindow;

    private ItineraryStorage itineraryStorage;

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

    public ItineraryPage(Stage primaryStage) {
        super(fxmlWindow);
        this.itineraryScene = new Scene(itineraryPane);
        this.primaryStage = primaryStage;
        this.itineraryParser = new ItineraryParser();
        this.model = new Model();
        this.itineraryStorage = new JsonItineraryStorage(Paths.get("data" , "itinerary.json"));

        Itinerary itinerary = new Itinerary();

        try {
            Optional<ReadOnlyItinerary> itineraryOptional = itineraryStorage.readItinerary();
            if (itineraryOptional.isPresent()) {
                itinerary.updateItinerary(itineraryOptional.get());
            } else {
                System.out.println("Starting without a json file!");
            }
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty Itinerary");
            // todo: what to do about data? JUST OVERWRITE
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty Itinerary");
            // todo: what to do about data? JUST OVERWRITE
        }

        model.setItinerary(itinerary);

        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {

        eventPanel = new EventPanel(model.getSortedEventList());
        eventPlaceHolder.getChildren().add(eventPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, model.getActionList());
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
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        helpWindow.hide();
        primaryStage.hide();
    }

    @Override
    public Scene getScene() {
        setAccelerators();

        this.helpWindow = new HelpWindow();
        this.codeWindow = new CodeWindow();

        fillInnerParts();
        return itineraryScene;
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
            Command command = itineraryParser.parseCommand(commandText);
            model.addAction(commandText);
            CommandResult commandResult = command.execute(model);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            try {
                itineraryStorage.saveItinerary(model.getItinerary());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
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

        itineraryScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }
}
