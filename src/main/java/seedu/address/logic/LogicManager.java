package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expense.AddExpenseCommand;
import seedu.address.logic.parser.MooLahParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyMooLah;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MooLahParser mooLahParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        mooLahParser = new MooLahParser();
    }

    @Override
    public CommandResult execute(String commandText, String commandGroup) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = mooLahParser.parseCommand(commandText, commandGroup, model.getUserPrefs());
        commandResult = command.run(model);

        save();

        return commandResult;
    }

    /**
     * Saves any changes in MooLah or in user preferences into Storage.
     * @throws CommandException If there is an IO error.
     */
    private void save() throws CommandException {
        try {
            storage.saveMooLah(model.getMooLah());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public boolean hasBudgetWithName(Description targetDescription) {
        return model.hasBudgetWithName(targetDescription);
    }

    @Override
    public StringBuilder getBasicStatistics() {
        return model.getStatistic();
    }

    @Override
    public ReadOnlyMooLah getMooLah() {
        return model.getMooLah();
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return model.getFilteredExpenseList();
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return model.getFilteredBudgetList();
    }

    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public void deleteTranspiredEvents(List<Event> eventsToBeRemoved) {
        for (Event event : eventsToBeRemoved) {
            model.deleteEvent(event);
        }

        try {
            save();
        } catch (CommandException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void addExpenseFromEvent(Event event) throws CommandException {
        Expense toBeAdded = event.convertToExpense();
        Command addExpenseCommand = new AddExpenseCommand(toBeAdded);
        addExpenseCommand.run(model);
    }
    //@Override
    //public ObservableList<Budget> getFilteredBudgetList() {
    //  return model.getFilteredBudgetList();
    //}

    @Override
    public Budget getPrimaryBudget() {
        return model.getPrimaryBudget();
    }

    @Override
    public Path getMooLahFilePath() {
        return model.getMooLahFilePath();
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
