package seedu.address.financialtracker.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.financialtracker.logic.parser.FinancialTrackerParser;
import seedu.address.financialtracker.model.FinancialTracker;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.storage.FinancialTrackerStorage;
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

    public FinancialTrackerLogic(Model financialTrackerModel, FinancialTrackerStorage storage) {
        this.financialTrackerModel = financialTrackerModel;
        this.storage = storage;
        financialTrackerParser = new FinancialTrackerParser();
        try {
            Optional<FinancialTracker> financialTrackerOptional = storage.readFinancialTracker();
            financialTrackerOptional.ifPresent(financialTrackerModel::updateFinancialTracker);
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty Financial Tracker");
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty Financial Tracker");
        }
    }

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

    public FinancialTracker getFinancialTracker() {
        return financialTrackerModel.getFinancialTracker();
    }

    public ObservableList<Expense> getExpenseList() {
        return financialTrackerModel.getExpenseList();
    }

    public Path getFinancialTrackerFilePath() {
        return Paths.get("data" , "financialtracker.json");
    }

    public void setCountry(String field) {
        this.financialTrackerModel.setCountry(field);
    }

    //public GuiSettings getGuiSettings() { return userPrefsModel.getGuiSettings(); }

    //public void setGuiSettings(GuiSettings guiSettings) { userPrefsModel.setGuiSettings(guiSettings); }
}
