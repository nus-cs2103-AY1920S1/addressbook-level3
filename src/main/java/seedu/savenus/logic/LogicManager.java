package seedu.savenus.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.commands.Command;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.SaveNusParser;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.sorter.CustomSorter;
import seedu.savenus.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SaveNusParser saveNusParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        saveNusParser = new SaveNusParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = saveNusParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveMenu(model.getMenu());
            storage.saveRecs(model.getRecommendationSystem().getUserRecommendations());
            storage.savePurchaseHistory(model.getPurchaseHistory());
            storage.saveFields(model.getCustomSorter());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyMenu getMenu() {
        return model.getMenu();
    }

    @Override
    public CustomSorter getCustomSorter() {
        return model.getCustomSorter();
    }

    @Override
    public boolean getAutoSortFlag() {
        return model.getAutoSortFlag();
    }

    @Override
    public void setFoods(List<Food> list) {
        model.setFoods(list);
    }

    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return model.getFilteredFoodList();
    }

    @Override
    public ObservableList<Purchase> getPurchaseHistoryList() {
        return model.getPurchaseHistoryList();
    }

    @Override
    public Path getMenuFilePath() {
        return model.getMenuFilePath();
    }

    @Override
    public Path getPurchaseHistoryFilePath() {
        return model.getPurchaseHistoryFilePath();
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
