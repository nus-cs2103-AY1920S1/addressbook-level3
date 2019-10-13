package seedu.address.financialtracker.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.financialtracker.parser.FinancialTrackerParser;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.*;


/**
 * The Financial Tracker Window
 */
public class FinancialTrackerPage implements Page {

    private final static PageType pageType = PageType.FINANCIAL_TRACKER;
    private static final String FXML = "FinancialTrackerWindow.fxml";
    private static final String FXML_FILE_FOLDER = "/view/";

    /**
     * maybe use UIparts instead?
     */
    private final FXMLLoader fxmlLoader;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;

    private final Logger logger = LogsCenter.getLogger(getClass());
    private FinancialTrackerParser financialTrackerParser;

    @FXML
    private Scene financialTrackerScene;

    @FXML
    private VBox financialTrackerPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public FinancialTrackerPage() {
        this.financialTrackerParser = new FinancialTrackerParser();
        fxmlLoader = new FXMLLoader(getFxmlFileUrl());
        fxmlLoader.setController(this);
        System.out.println(this);
        fxmlLoader.setRoot(new VBox());
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        financialTrackerScene = new Scene(financialTrackerPane);
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            Command command = financialTrackerParser.parseCommand(commandText);
            CommandResult commandResult = command.execute(null);
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

    /**
     * Changes application page.
     */
    @FXML
    private void handlePageChange(CommandResult commandResult) {
        Scene requestedPage = Pages.getPage(commandResult);
        ((Stage) this.getScene().getWindow()).setScene(requestedPage);
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        this.financialTrackerScene.getWindow().hide();
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within {@link #FXML_FILE_FOLDER}.
     */
    private static URL getFxmlFileUrl() {
        requireNonNull(FinancialTrackerPage.FXML);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + FinancialTrackerPage.FXML;
        URL fxmlFileUrl = MainApp.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }

    @Override
    public Scene getScene() {
        return financialTrackerScene;
    }

    @Override
    public PageType getPageType() {
        return pageType;
    }
}
