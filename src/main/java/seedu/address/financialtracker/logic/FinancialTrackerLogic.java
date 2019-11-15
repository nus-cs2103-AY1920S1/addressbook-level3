package seedu.address.financialtracker.logic;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.financialtracker.logic.parser.FinancialTrackerParser;
import seedu.address.financialtracker.model.FinancialTracker;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.util.FinancialTrackerStatistics;
import seedu.address.financialtracker.model.util.SampleDataUtil;
import seedu.address.financialtracker.storage.FinancialTrackerStorage;
import seedu.address.financialtracker.storage.JsonFinancialTrackerStorage;
import seedu.address.financialtracker.ui.CountriesDropdown;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The main FinancialTrackerLogic of the app.
 */
public class FinancialTrackerLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(FinancialTrackerLogic.class);

    private final Model financialTrackerModel;
    private final FinancialTrackerStorage storage;
    private final FinancialTrackerParser financialTrackerParser;

    public FinancialTrackerLogic() {
        Model financialTrackerModel;
        this.storage = new JsonFinancialTrackerStorage(Paths.get("data", "financialtracker.json"));
        financialTrackerParser = new FinancialTrackerParser();
        try {
            Optional<FinancialTracker> financialTrackerOptional = storage.readFinancialTracker();
            if (financialTrackerOptional.isPresent()) {
                financialTrackerModel = new Model(financialTrackerOptional.get());
            } else {
                financialTrackerModel = new Model(SampleDataUtil.getSampleData());
                logger.info("Data file not found. Will be starting with a sample FinancialTracker");
            }
        } catch (DataConversionException e) {
            financialTrackerModel = new Model();
            logger.info("Data file not in the correct format. Will be starting with an empty Financial Tracker");
        } catch (IOException e) {
            financialTrackerModel = new Model();
            logger.info("Problem while reading from the file. Will be starting with an empty Financial Tracker");
        }
        this.financialTrackerModel = financialTrackerModel;
    }

    /**
     * Link dependencies to Financial Tracker model.
     */
    public void addDependencies(CountriesDropdown countriesDropdown) {
        this.financialTrackerModel.addDependencies(countriesDropdown);
    }

    /**
     * Use this logic class to execute commands which utilize model and storage.
     * @param commandText user input
     * @return CommandResult, see {@Code CommandResult}
     * @throws CommandException if the command executed with error
     * @throws ParseException   if user inputted wrong format of command
     */
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = financialTrackerParser.parseCommand(commandText);
        commandResult = command.execute(financialTrackerModel);

        try {
            storage.saveFinancialTracker(financialTrackerModel.getFinancialTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Returns an observe only expense list with current country specified in
     * financial tracker.
     */
    public ObservableList<Expense> getExpenseList() {
        return financialTrackerModel.getExpenseList();
    }

    /**
     * Set the Financial Tracker's current country key.
     * @param field country to be set
     */
    public void setCountry(String field) {
        this.financialTrackerModel.setCountry(field);
    }

    public FinancialTrackerStatistics getStatistics() {
        return new FinancialStatisticsManager();
    }

    /**
     * Local class for {@link FinancialTrackerStatistics}
     */
    private class FinancialStatisticsManager implements FinancialTrackerStatistics {

        @Override
        public ObservableList<PieChart.Data> getFinancialPieChartData() {
            return financialTrackerModel.getFinancialPieChartData();
        }

        @Override
        public XYChart.Series<String, Number> getFinancialBarChartData() {
            return financialTrackerModel.getFinancialBarChartData();
        }
    }

    // public GuiSettings getGuiSettings() { return userPrefsModel.getGuiSettings();
    // }
    //
    // public void setGuiSettings(GuiSettings guiSettings) {
    // userPrefsModel.setGuiSettings(guiSettings); }
}
