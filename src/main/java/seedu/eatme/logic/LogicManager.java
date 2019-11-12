package seedu.eatme.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.eatme.commons.core.GuiSettings;
import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.logic.commands.Command;
import seedu.eatme.logic.commands.CommandResult;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.logic.parser.EatMeParser;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.Model;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Review;
import seedu.eatme.model.statistics.Statistics;
import seedu.eatme.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EatMeParser eatMeParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        eatMeParser = new EatMeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = eatMeParser.parseCommand(commandText, isMainMode());
        commandResult = command.execute(model);

        try {
            storage.saveEateryList(model.getEateryList());
            storage.saveFeedList(model.getFeedList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEateryList getEateryList() {
        return model.getEateryList();
    }

    @Override
    public ObservableList<Eatery> getFilteredEateryList() {
        return model.getFilteredEateryList();
    }

    @Override
    public ObservableList<Eatery> getFilteredTodoList() {
        return model.getFilteredTodoList();
    }

    @Override
    public ObservableList<Review> getActiveReviews() {
        return model.getActiveReviews();
    }

    @Override
    public boolean isMainMode() {
        return model.isMainMode();
    }

    @Override
    public Path getEateryListFilePath() {
        return model.getEateryListFilePath();
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
    public ReadOnlyFeedList getFeedList() {
        return model.getFeedList();
    }

    @Override
    public Path getFeedListFilePath() {
        return model.getFeedListFilePath();
    }

    @Override
    public void saveFeedList() {
        try {
            storage.saveFeedList(model.getFeedList());
        } catch (IOException ioe) {
            logger.warning(FILE_OPS_ERROR_MESSAGE + ioe);
        }
    }

    @Override
    public Statistics getStatistics() {
        return model.getStatistics();
    }
}
