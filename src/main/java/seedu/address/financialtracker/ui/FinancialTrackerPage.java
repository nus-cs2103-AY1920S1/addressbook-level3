package seedu.address.financialtracker.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.financialtracker.Model.Model;
import seedu.address.financialtracker.commands.Command;
import seedu.address.financialtracker.parser.FinancialTrackerParser;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;
import seedu.address.ui.Pages;
import seedu.address.ui.ResultDisplay;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;


/**
 * The Financial Tracker Window
 */
public class FinancialTrackerPage extends UiPart<VBox> implements Page {

    private final static PageType pageType = PageType.FINANCIAL_TRACKER;
    private static final String FXML = "FinancialTrackerWindow.fxml";

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private ExpensePanel expensePanel;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private FinancialTrackerParser financialTrackerParser;
    private Model model;

    @FXML
    private Scene financialTrackerScene;

    @FXML
    private VBox financialTrackerPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane expensePlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public FinancialTrackerPage() {
        super(FXML, new VBox());
        this.financialTrackerParser = new FinancialTrackerParser();
        this.model = new Model();
        financialTrackerScene = new Scene(financialTrackerPane);
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {
        expensePanel = new ExpensePanel(model.getFilteredExpenseList());
        expensePlaceholder.getChildren().add(expensePanel.getRoot());

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

    @Override
    public Scene getScene() {
        return financialTrackerScene;
    }

    @Override
    public PageType getPageType() {
        return pageType;
    }
}
