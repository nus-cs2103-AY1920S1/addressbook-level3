package seedu.ezwatchlist.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.logic.commands.Command;
import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.WatchListParser;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.storage.Storage;

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
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = watchListParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveWatchList(model.getWatchList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyWatchList getWatchList() {
        return model.getWatchList();
    }

    @Override
    public ObservableList<Show> getFilteredShowList() {
        return model.getFilteredShowList();
    }

    @Override
    public Path getWatchListFilePath() {
        return model.getWatchListFilePath();
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
