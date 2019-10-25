package seedu.ifridge.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.logic.parser.IFridgeParser;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.ReadOnlyWasteList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteReport;
import seedu.ifridge.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final IFridgeParser ifridgeParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        ifridgeParser = new IFridgeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = ifridgeParser.parseCommand(commandText, model.getIFridgeSettings());
        commandResult = command.execute(model);

        try {
            storage.saveGroceryList(model.getGroceryList());
            storage.saveTemplateList(model.getTemplateList());
            storage.saveShoppingList(model.getShoppingList());
            storage.saveWasteList(model.getWasteArchive());
            storage.saveBoughtList(model.getBoughtList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyGroceryList getGroceryList() {
        return model.getGroceryList();
    }

    @Override
    public ObservableList<GroceryItem> getFilteredGroceryItemList() {
        return model.getFilteredGroceryItemList();
    }

    @Override
    public Path getGroceryListFilePath() {
        return model.getGroceryListFilePath();
    }

    @Override
    public ReadOnlyTemplateList getTemplateList() {
        return model.getTemplateList();
    }

    @Override
    public ObservableList<UniqueTemplateItems> getFilteredTemplateList() {
        return model.getFilteredTemplateList();
    }

    @Override
    public Path getTemplateListFilePath() {
        return model.getTemplateListFilePath();
    }

    @Override
    public ObservableList<TemplateItem> getFilteredTemplateToBeShown() {
        return model.getFilteredTemplateToBeShown();
    }

    @Override
    public Name getNameTemplateToBeShown() {
        return model.getNameTemplateToBeShown();
    }

    @Override
    public ReadOnlyWasteList getWasteList() {
        return model.getWasteList();
    }

    @Override
    public ObservableList<GroceryItem> getFilteredWasteList() {
        return model.getFilteredWasteItemList();
    }

    @Override
    public WasteReport getWasteReport() {
        return model.getWasteReport();
    }

    @Override
    public Path getWasteListFilePath() {
        return model.getWasteListFilePath();
    }

    @Override
    public ReadOnlyShoppingList getShoppingList() {
        return model.getShoppingList();
    }

    @Override
    public ObservableList<ShoppingItem> getFilteredShoppingList() {
        return model.getFilteredShoppingList();
    }

    @Override
    public Path getShoppingListFilePath() {
        return model.getShoppingListFilePath();
    }

    @Override
    public ReadOnlyGroceryList getBoughtList() {
        return model.getBoughtList();
    }

    @Override
    public ObservableList<GroceryItem> getFilteredBoughtList() {
        return model.getFilteredBoughtItemList();
    }

    @Override
    public Path getBoughtListFilePath() {
        return model.getBoughtListFilePath();
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
    public IFridgeSettings getIFridgeSettings() {
        return model.getIFridgeSettings();
    }

    @Override
    public void setIFridgeSettings(IFridgeSettings iFridgeSettings) {
        model.setIFridgeSettings(iFridgeSettings);
    }
}
