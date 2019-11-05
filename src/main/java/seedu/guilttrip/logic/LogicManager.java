package seedu.guilttrip.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.logic.parser.GuiltTripParser;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.model.statistics.DailyStatistics;
import seedu.guilttrip.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final GuiltTripParser guiltTripParser;
    private boolean addressBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        guiltTripParser = new GuiltTripParser();

        //Set addressBookModified to true whenever the models' addressbook is modified.
        model.getAddressBook().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, IllegalArgumentException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        try {
            Command command = guiltTripParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Finance tracker modified, saving to file");
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyGuiltTrip getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public DoubleProperty getTotalExpenseForPeriod() {
        return model.getTotalExpenseForPeriod();
    }

    @Override
    public DoubleProperty getTotalIncomeForPeriod() {
        return model.getTotalIncomeForPeriod();
    }

    @Override
    public ObservableList<DailyStatistics> getListOfStatsForBarChart() {
        return model.getListOfStatsForBarChart();
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForExpense() {
        return model.getListOfStatsForExpense();
    }

    @Override
    public ObservableList<CategoryStatistics> getListOfStatsForIncome() {
        return model.getListOfStatsForIncome();
    }

    @Override
    public ObservableList<Entry> getFilteredExpenseAndIncomeList() {
        return model.getFilteredExpensesAndIncomes();
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return model.getFilteredExpenses();
    }

    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        return model.getFilteredIncomes();
    }

    @Override
    public ObservableList<AutoExpense> getFilteredAutoExpenseList() {
        return model.getFilteredAutoExpenses();
    }

    @Override
    public ObservableList<Wish> getFilteredWishList() {
        return model.getFilteredWishes();
    }

    @Override
    public ObservableList<Reminder> getFilteredReminders() {
        return model.getFilteredReminders();
    }

    @Override
    public ObservableList<Condition> getFilteredConditions() {
        return model.getFilteredConditions();
    }

    public ObservableList<Budget> getFilteredBudgetList() {
        return model.getFilteredBudgets();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
