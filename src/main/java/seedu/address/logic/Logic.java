package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.IFridgeSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.food.UniqueTemplateItems;

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
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getGroceryList()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<GroceryItem> getFilteredGroceryItemList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the TemplateList.
     *
     * @see seedu.address.model.Model#getTemplateList()
     */
    ReadOnlyTemplateList getTemplateList();

    /** Returns an unmodifiable view of the filtered list of templates */
    ObservableList<UniqueTemplateItems> getFilteredTemplateList();

    /**
     * Returns the user prefs' template list file path.
     */
    Path getTemplateListFilePath();

    ReadOnlyWasteList getWasteList();

    ObservableList<GroceryItem> getFilteredWasteList();

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
