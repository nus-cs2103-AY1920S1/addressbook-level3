package seedu.address.logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;

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
    CommandResult execute(String commandText) throws CommandException, ParseException, IOException, URISyntaxException;

    CommandResult executeUnknownInput(String commandText) throws CommandException, ParseException,
            IOException, URISyntaxException;

    CommandResult executeClear(String commandText) throws CommandException;

    /**
     * Returns the FinSec.
     *
     * @see seedu.address.model.Model#getFinSec()
     */
    ReadOnlyFinSec getFinSec();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Contact> getFilteredContactList();

    /** Returns an unmodifiable view of the filtered list of claims */
    ObservableList<Claim> getFilteredClaimList();

    /** Returns an unmodifiable view of the filtered list of incomes */
    ObservableList<Income> getFilteredIncomeList();

    /** Returns an unmodifiable view of the filtered list of incomes */
    ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList();

    /** Returns an unmodifiable view of the filtered list of incomes */
    ObservableList<CommandItem> getFilteredCommandsList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getFinSecFilePath();

    /**
     * Returns the model.
     */
    Model getModel();
    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
