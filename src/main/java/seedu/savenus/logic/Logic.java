package seedu.savenus.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.wallet.Wallet;

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
     * Returns the Menu.
     *
     * @see seedu.savenus.model.Model#getMenu()
     */
    ReadOnlyMenu getMenu();

    Wallet getWallet();

    CustomSorter getCustomSorter();

    AliasList getAliasList();

    boolean getAutoSortFlag();

    /** Returns an unmodifiable view of the filtered list of food */
    ObservableList<Food> getFilteredFoodList();

    /** Returns an unmodifiable view of the purchase history */
    ObservableList<Purchase> getPurchaseHistoryList();

    void setFoods(List<Food> list);

    /**
     * Gets back the original list.
     */
    ObservableList<Food> getFoods();

    /**
     * Returns the user prefs' menu file path.
     */
    Path getMenuFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user's savings history.
     */
    ReadOnlySavingsAccount getSavingsHistory();
}
