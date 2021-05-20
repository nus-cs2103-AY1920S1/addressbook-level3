package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;




/**
 * Unmodifiable view of a fin sec.
 */
public interface ReadOnlyFinSec {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Contact> getContactList();

    /**
     * Returns an unmodifiable view of the income list.
     * This list will not contain any duplicate incomes.
     */
    ObservableList<Income> getIncomeList();

    /**
     * Returns an unmodifiable view of the claims list.
     * This list will not contain any duplicate claims.
     */
    ObservableList<Claim> getClaimList();

    /**
     * Returns an unmodifiable view of the claims list.
     * This list will not contain any duplicate claims.
     */
    ObservableList<AutocorrectSuggestion> getAutocorrectSuggestionList();

    /**
     * Returns an unmodifiable view of the commands list.
     * This list will not contain any duplicate commands.
     */
    ObservableList<CommandItem> getCommandsList();

}
