package seedu.moneygowhere.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.commands.Command;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.SpendingBookParser;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;
import seedu.moneygowhere.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SpendingBookParser spendingBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        spendingBookParser = new SpendingBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        storage.addCommand(commandText);

        CommandResult commandResult;
        Command command = spendingBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveSpendingBook(model.getSpendingBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    /**
     * Returns a map of spending with key and value pair representing data for the statistics chart.
     * The key represents the different tags.
     * The value represents the cumulative cost for that tag.
     *
     * @return the map of data used for the statistics
     */
    public LinkedHashMap<String, Double> getGraphData() {
        ObservableList<Spending> spendingList = model.getStatsList();
        LinkedHashMap<String, Double> graphData = new LinkedHashMap<>();
        for (Spending s : spendingList) {
            if (graphData.containsKey(s.getDate().value)) {
                graphData.put(s.getDate().value,
                    graphData.get(s.getDate().value) + Double.parseDouble(s.getCost().toString()));
            } else {
                graphData.put(s.getDate().value, Double.parseDouble(s.getCost().toString()));
            }
        }
        return graphData;
    }

    /**
     * Returns a map of spending with key and value pair representing data for the statistics chart.
     * The key represents the different tags.
     * The value represents the cumulative cost for that tag.
     *
     * @return the map of data used for the statistics
     */
    public LinkedHashMap<String, Double> getStatsData() {
        ObservableList<Spending> spendingList = model.getStatsList();
        LinkedHashMap<String, Double> statsData = new LinkedHashMap<>();
        for (Spending s : spendingList) {
            Set<Tag> tagSet = s.getTags();
            for (Tag t: tagSet) {
                if (statsData.containsKey(t.tagName)) {
                    statsData.put(t.tagName, statsData.get(t.tagName) + Double.parseDouble(s.getCost().toString()));
                } else {
                    statsData.put(t.tagName, Double.parseDouble(s.getCost().toString()));
                }
            }
        }
        return statsData;
    }

    @Override
    public ReadOnlySpendingBook getSpendingBook() {
        return model.getSpendingBook();
    }

    @Override
    public ObservableList<Spending> getFilteredSpendingList() {
        return model.getFilteredSpendingList();
    }

    @Override
    public ObservableList<Reminder> getSortedReminderList() {
        return model.getSortedReminderList();
    }

    @Override
    public Path getSpendingBookFilePath() {
        return model.getSpendingBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public String getPrevCommand() {
        return storage.getPrevCommand();
    }

    @Override
    public String getNextCommand() {
        return storage.getNextCommand();
    }
}
