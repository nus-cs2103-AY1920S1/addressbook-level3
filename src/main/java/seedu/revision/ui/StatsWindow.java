package seedu.revision.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.quiz.Statistics;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class StatsWindow extends Window {

    private final Logger logger = LogsCenter.getLogger(getClass());

    private MainWindow mainWindow;
    private ObservableList<Statistics> statisticsList;
    private Model model;

    // Independent Ui parts residing in this Ui container
    private PieChartPane pieChartPane;
    private ResultDisplay feedbackDisplay;
    private CommandBox commandBox;

    public StatsWindow(Stage primaryStage, MainLogic mainLogic) {
        super(primaryStage, mainLogic);
        this.model = super.mainLogic.getModel();
        this.statisticsList = model.getStatisticsList();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    @Override
    void fillInnerParts() {

    }

    /**
     * Closes the application.
     */
    @Override
    protected void handleExit() {

    }

    /**
     * Executes the command and returns the result.
     *
     * @param commandText
     */
    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException, IOException {
        return null;
    }
}
