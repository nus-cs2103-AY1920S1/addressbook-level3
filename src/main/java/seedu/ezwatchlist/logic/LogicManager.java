package seedu.ezwatchlist.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.logic.commands.Command;
import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.WatchListParser;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.storage.Storage;
import seedu.ezwatchlist.ui.MainWindow;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final WatchListParser watchListParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        watchListParser = new WatchListParser();
    }

    @Override
    public CommandResult execute(String commandText, MainWindow mainWindow, String currentTab)
            throws CommandException, ParseException, OnlineConnectionException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        final CommandResult[] commandResult = new CommandResult[1];
        Command command = watchListParser.parseCommand(commandText, currentTab);

        if (command instanceof SearchCommand) {
            mainWindow.setIsSearchLoading();
            mainWindow.goToSearch();
            mainWindow.getResultDisplay().setFeedbackToUser("Loading...");
            Task<CommandResult> task = new Task<CommandResult>() {
                @Override
                protected CommandResult call() throws Exception {
                    return command.execute(model);
                }
            };
            task.setOnSucceeded(event -> {
                mainWindow.setIsSearchLoading();
                mainWindow.goToSearch();
                commandResult[0] = task.getValue();
                mainWindow.searchResultLogger(commandResult[0]);
            });
            new Thread(task).start();
            return null;
        } else {
            commandResult[0] = command.execute(model);
        }
        try {
            storage.saveWatchList(model.getWatchList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return commandResult[0];
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public ReadOnlyWatchList getWatchList() {
        return model.getWatchList();
    }

    @Override
    public ReadOnlyWatchList getDatabase() {
        return model.getDatabase();
    }

    @Override
    public ObservableList<Show> getUnWatchedList() {
        return model.getUnWatchedShowList();
    }

    @Override
    public ObservableList<Show> getWatchedList() {
        return model.getWatchedShowList();
    }

    @Override
    public ObservableList<Show> getFilteredShowList() {
        return model.getFilteredShowList();
    }

    @Override
    public void updateFilteredShowList(Predicate<Show> predicate) {
        model.updateFilteredShowList(predicate);
    }

    @Override
    public ObservableList<Show> getSearchResultList() {
        return model.getSearchResultList();
    }

    @Override
    public Path getWatchListFilePath() {
        return model.getWatchListFilePath();
    }

    @Override
    public Path getDatabaseFilePath() {
        return model.getDatabaseFilePath();
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
