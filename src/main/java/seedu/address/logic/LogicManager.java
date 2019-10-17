package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.IFridgeSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IfridgeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.food.UniqueTemplateItems;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final IfridgeParser ifridgeParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        ifridgeParser = new IfridgeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = ifridgeParser.parseCommand(commandText, model.getIFridgeSettings());
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getGroceryList());
            storage.saveTemplateList(model.getTemplateList());
            storage.saveShoppingList(model.getShoppingList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getGroceryList();
    }

    @Override
    public ObservableList<GroceryItem> getFilteredGroceryItemList() {
        return model.getFilteredGroceryItemList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
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
    public ReadOnlyWasteList getWasteList() {
        return model.getWasteList();
    }

    @Override
    public ObservableList<GroceryItem> getFilteredWasteList() {
        return model.getFilteredWasteItemList();
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
