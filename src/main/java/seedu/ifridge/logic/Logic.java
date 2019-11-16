package seedu.ifridge.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.logic.parser.exceptions.ParseException;
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

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the GroceryList.
     *
     * @see seedu.ifridge.model.Model#getGroceryList()
     */
    ReadOnlyGroceryList getGroceryList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<GroceryItem> getFilteredGroceryItemList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getGroceryListFilePath();

    /**
     * Returns the TemplateList.
     *
     * @see seedu.ifridge.model.Model#getTemplateList()
     */
    ReadOnlyTemplateList getTemplateList();

    /** Returns an unmodifiable view of the filtered list of templates */
    ObservableList<UniqueTemplateItems> getFilteredTemplateList();

    /**
     * Returns the user prefs' template list file path.
     */
    Path getTemplateListFilePath();

    ObservableList<TemplateItem> getFilteredTemplateToBeShown();

    Name getNameTemplateToBeShown();

    ReadOnlyWasteList getWasteList();

    ObservableList<GroceryItem> getFilteredWasteList();

    WasteReport getWasteReport();

    Path getWasteListFilePath();

    /**
     * Returns the ShoppingList.
     *
     * @see Model#getShoppingList()
     */
    ReadOnlyShoppingList getShoppingList();

    /** Returns an unmodifiable view of the filtered list of shopping items */
    ObservableList<ShoppingItem> getFilteredShoppingList();

    /**
     * Returns the user prefs' shopping list file path.
     */
    Path getShoppingListFilePath();

    ReadOnlyGroceryList getBoughtList();

    ObservableList<GroceryItem> getFilteredBoughtList();

    Path getBoughtListFilePath();
    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' iFridge settings.
     */
    IFridgeSettings getIFridgeSettings();

    /**
     * Set the user prefs' iFridge settings.
     */
    void setIFridgeSettings(IFridgeSettings iFridgeSettings);
}
