package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;

import java.util.Comparator;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Status;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;
import seedu.address.storage.SuggestionsStorage;
import seedu.address.ui.UiManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FinSec finSec;
    private final UserPrefs userPrefs;
    private FilteredList<Contact> filteredContacts;
    private FilteredList<Claim> filteredClaims;
    private FilteredList<Income> filteredIncomes;
    private final FilteredList<AutocorrectSuggestion> filteredSuggestions;
    private final FilteredList<CommandItem> filteredCommands;
    private Stack<String> savedCommand;
    private String state;

    /**
     * Initializes a ModelManager with the given finSec and userPrefs.
     */
    public ModelManager(ReadOnlyFinSec finSec, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(finSec, userPrefs);

        logger.fine("Initializing with finSec: " + finSec + " and user prefs " + userPrefs);

        this.finSec = new FinSec(finSec);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.finSec.getContactList());
        filteredClaims = new FilteredList<>(this.finSec.getClaimList());
        filteredIncomes = new FilteredList<>(this.finSec.getIncomeList());
        filteredSuggestions = new FilteredList<>(this.finSec.getAutocorrectSuggestionList());
        SuggestionsStorage.setSuggestionList(filteredSuggestions);
        filteredCommands = new FilteredList<>(this.finSec.getCommandsList());
        savedCommand = new Stack<String>();
    }

    public ModelManager() {
        this(new FinSec(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFinSecFilePath() {
        return userPrefs.getFinSecFilePath();
    }

    @Override
    public void setFinSecFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setFinSecFilePath(addressBookFilePath);
    }

    //=========== FinSec ================================================================================

    @Override
    public void setFinSec(ReadOnlyFinSec finSec) {
        this.finSec.resetData(finSec);
    }

    @Override
    public ReadOnlyFinSec getFinSec() {
        return finSec;
    }

    //=========== Contact ================================================================================

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return finSec.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        finSec.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        finSec.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        finSec.setContact(target, editedContact);
    }

    @Override
    public void sortFilteredContactListByName() {
        filteredContacts = new FilteredList<Contact>(this.finSec.getContactList().sorted(new ContactNameComparator()));
        updateFilteredContactList(p -> true);
        UiManager.startWithContacts();
    }

    @Override
    public void sortReverseFilteredContactListByName() {
        Comparator<Contact> reverseComparator = Comparator.comparing(Contact::toString).reversed();
        filteredContacts = new FilteredList<Contact>(this.finSec.getContactList().sorted(reverseComparator));
        updateFilteredContactList(p -> true);
        UiManager.startWithContacts();
    }

    //=========== Claims ================================================================================
    @Override
    public boolean hasClaim(Claim claim) {
        requireNonNull(claim);
        return finSec.hasClaim(claim);
    }

    @Override
    public void deleteClaim(Claim target) {
        finSec.removeClaim(target);
    }

    @Override
    public void addClaim(Claim claim) {
        finSec.addClaim(claim);
        updateFilteredClaimList(PREDICATE_SHOW_ALL_CLAIMS);
    }

    @Override
    public void setClaim(Claim target, Claim editedClaim) {
        requireAllNonNull(target, editedClaim);

        finSec.setClaim(target, editedClaim);
    }

    @Override
    public void approveClaim(Claim claim) {
        requireNonNull(claim);
        finSec.approveClaim(claim);
    }

    @Override
    public void rejectClaim(Claim claim) {
        requireNonNull(claim);
        finSec.rejectClaim(claim);
    }

    /** Finds the {@code contact} for a {@code claim}.
    *
    * @return true if contact has been found.
    */
    public boolean hasContactFor(Claim claim) {
        requireNonNull(claim);
        return finSec.hasContact(claim.getName());
    }

    @Override
    public void sortFilteredClaimListByName() {
        filteredClaims = new FilteredList<Claim>(this.finSec.getClaimList().sorted(new ClaimNameComparator()));
        updateFilteredClaimList(p -> true);
        UiManager.startWithClaims();
    }

    @Override
    public void sortFilteredClaimListByDate() {
        filteredClaims = new FilteredList<Claim>(this.finSec.getClaimList()
                .sorted(new ClaimDateComparator()));
        updateFilteredClaimList(p -> true);
        UiManager.startWithClaims();
    }

    @Override
    public void sortFilteredClaimListByStatus() {
        filteredClaims = new FilteredList<Claim>(this.finSec.getClaimList()
                .sorted(new ClaimStatusComparator()));
        updateFilteredClaimList(p -> true);
        UiManager.startWithClaims();
    }

    //=========== Sorts reverse way of claims list ==============

    @Override
    public void sortReverseFilteredClaimListByDate() {
        filteredClaims = new FilteredList<Claim>(this.finSec.getClaimList()
                .sorted(new ClaimDateComparator().reversed()));
        updateFilteredClaimList(p -> true);
        UiManager.startWithClaims();
    }

    @Override
    public void sortReverseFilteredClaimListByName() {
        filteredClaims = new FilteredList<Claim>(this.finSec.getClaimList().sorted(
                new ClaimNameComparator().reversed()));
        updateFilteredClaimList(p -> true);
        UiManager.startWithClaims();
    }

    @Override
    public void sortReverseFilteredClaimListByStatus() {
        filteredClaims = new FilteredList<Claim>(this.finSec.getClaimList()
                .sorted(new ClaimStatusReverseComparator()));
        updateFilteredClaimList(p -> true);
        UiManager.startWithClaims();
    }

    //=========== Incomes ================================================================================
    @Override
    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return finSec.hasIncome(income);
    }

    @Override
    public void deleteIncome(Income target) {
        finSec.removeIncome(target);
    }

    @Override
    public void addIncome(Income income) {
        finSec.addIncome(income);
        updateFilteredIncomeList(PREDICATE_SHOW_ALL_INCOMES);
    }

    @Override
    public void setIncome(Income target, Income editedIncome) {
        requireAllNonNull(target, editedIncome);

        finSec.setIncome(target, editedIncome);
    }

    @Override
    public void sortFilteredIncomeListByName() {
        filteredIncomes = new FilteredList<>(this.finSec.getIncomeList().sorted());
        updateFilteredIncomeList(p -> true);
        UiManager.startWithIncomes();
    }

    @Override
    public void sortFilteredIncomeListByDate() {
        filteredIncomes = new FilteredList<>(this.finSec.getIncomeList().sorted(new IncomeDateComparator()));
        updateFilteredIncomeList(p -> true);
        UiManager.startWithIncomes();
    }

    //=========== Sorts reverse way of incomes list ==============

    @Override
    public void sortReverseFilteredIncomeListByName() {
        filteredIncomes = new FilteredList<>(this.finSec.getIncomeList().sorted(new IncomeNameComparator().reversed()));
        updateFilteredIncomeList(p -> true);
        UiManager.startWithIncomes();
    }

    @Override
    public void sortReverseFilteredIncomeListByDate() {
        filteredIncomes = new FilteredList<>(this.finSec.getIncomeList().sorted(new IncomeDateComparator().reversed()));
        updateFilteredIncomeList(p -> true);
        UiManager.startWithIncomes();
    }

    //=========== Suggestions ============================================================================
    @Override
    public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        return finSec.hasAutocorrectSuggestion(suggestion);
    }

    @Override
    public void deleteAutocorrectSuggestion(AutocorrectSuggestion target) {
        finSec.removeAutocorrectSuggestion(target);
        SuggestionsStorage.setSuggestionList(this.filteredSuggestions);
    }

    @Override
    public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        finSec.addAutocorrectSuggestion(suggestion);
        updateFilteredAutocorrectSuggestionList(PREDICATE_SHOW_ALL_AUTOCORRECTSUGGESTIONS);
        SuggestionsStorage.setSuggestionList(this.filteredSuggestions);
    }

    @Override
    public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {
        finSec.setAutocorrectSuggestion(target, editedSuggestion);
        SuggestionsStorage.setSuggestionList(this.filteredSuggestions);
    }

    //=========== Commands ===============================================================================

    @Override
    public boolean hasCommand(CommandItem command) {
        requireAllNonNull(command);
        return finSec.hasCommand(command);
    }

    @Override
    public void deleteCommand(CommandItem target) {
        finSec.removeCommand(target);
    }

    @Override
    public void addCommand(CommandItem command) {
        finSec.addCommand(command);
        updateFilteredCommandsList(PREDICATE_SHOW_ALL_COMMANDS);
    }

    @Override
    public void setCommand(CommandItem target, CommandItem editedCommand) {
        requireAllNonNull(target, editedCommand);

        finSec.setCommand(target, editedCommand);
    }

    @Override
    public void saveCommand(String command) {
        this.savedCommand.push(command);
    }

    @Override
    public String getSavedCommand() {
        return this.savedCommand.peek();
    }

    //=========== Filtered FinSec List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code FinSec} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
        UiManager.startWithContacts();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return finSec.equals(other.finSec)
                && userPrefs.equals(other.userPrefs)
                && filteredContacts.equals(other.filteredContacts)
                && filteredIncomes.equals(other.filteredIncomes)
                && filteredClaims.equals(other.filteredClaims)
                && filteredSuggestions.equals(filteredSuggestions)
                && filteredCommands.equals(filteredCommands);
    }

    //=========== Filtered Claims List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Claim} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Claim> getFilteredClaimList() {
        return filteredClaims;
    }

    @Override
    public void updateFilteredClaimList(Predicate<Claim> predicate) {
        requireNonNull(predicate);
        filteredClaims.setPredicate(predicate);
        UiManager.startWithClaims();
    }

    //=========== Filtered Income List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Income} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        return filteredIncomes;
    }

    @Override
    public void updateFilteredIncomeList(Predicate<Income> predicate) {
        requireNonNull(predicate);
        filteredIncomes.setPredicate(predicate);
        UiManager.startWithIncomes();
    }

    //=========== Filtered suggestions list accessors =========================================================
    /**
     * Returns an unmodifiable view of the list of {@code Income} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList() {
        return filteredSuggestions;
    }

    @Override
    public void updateFilteredAutocorrectSuggestionList(Predicate<AutocorrectSuggestion> predicate) {
        filteredSuggestions.setPredicate(predicate);
    }

    //=========== Filtered commands list accesors =============================================================

    @Override
    public ObservableList<CommandItem> getFilteredCommandsList() {
        return filteredCommands;
    }

    @Override
    public void updateFilteredCommandsList(Predicate<CommandItem> predicate) {
        filteredCommands.setPredicate(predicate);
    }
}

/**
 * Compares 2 claims' names
 */
class ContactNameComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        return contact1.getName().toString().toUpperCase()
                .compareTo(contact2.getName().toString().toUpperCase());
    }
}

/**
 * Compares 2 incomes' names
 */
class IncomeNameComparator implements Comparator<Income> {
    @Override
    public int compare(Income income1, Income income2) {
        return income1.getName().toString().toUpperCase()
                .compareTo(income2.getName().toString().toUpperCase());
    }
}

/**
 * Compares 2 incomes' dates
 */
class IncomeDateComparator implements Comparator<Income> {

    @Override
    public int compare(Income income1, Income income2) {
        return income1.getDate().getLocalDate()
                .compareTo(income2.getDate().getLocalDate());
    }
}

/**
 * Compares 2 claims' names
 */
class ClaimNameComparator implements Comparator<Claim> {
    @Override
    public int compare(Claim claim1, Claim claim2) {
        return claim1.getDescription().toString().toUpperCase()
                .compareTo(claim2.getDescription().toString().toUpperCase());
    }
}

/**
 * Compares 2 claims' dates
 */
class ClaimDateComparator implements Comparator<Claim> {

    @Override
    public int compare(Claim claim1, Claim claim2) {
        return claim1.getDate().getLocalDate()
                .compareTo(claim2.getDate().getLocalDate());
    }
}

/**
 * Compares 2 claims' statuses in natural ordering
 */
class ClaimStatusComparator implements Comparator<Claim> {

    @Override
    public int compare(Claim claim1, Claim claim2) {
        if (claim1.getStatus().equals(Status.PENDING) && claim2.getStatus().equals(Status.APPROVED)) {
            return -1;
        } else if (claim1.getStatus().equals(Status.PENDING) && claim2.getStatus().equals(Status.PENDING)) {
            return 0;
        } else if (claim1.getStatus().equals(Status.PENDING) && claim2.getStatus().equals(Status.REJECTED)) {
            return -1;
        } else if (claim1.getStatus().equals(Status.APPROVED) && claim2.getStatus().equals(Status.REJECTED)) {
            return -1;
        } else if (claim1.getStatus().equals(Status.APPROVED) && claim2.getStatus().equals(Status.APPROVED)) {
            return 0;
        } else if (claim1.getStatus().equals(Status.APPROVED) && claim2.getStatus().equals(Status.PENDING)) {
            return 1;
        } else if (claim1.getStatus().equals(Status.REJECTED) && claim2.getStatus().equals(Status.PENDING)) {
            return 1;
        } else if (claim1.getStatus().equals(Status.REJECTED) && claim2.getStatus().equals(Status.REJECTED)) {
            return 0;
        } else if (claim1.getStatus().equals(Status.REJECTED) && claim2.getStatus().equals(Status.APPROVED)) {
            return 1;
        } else {
            return 0;
        }
    }
}

/**
 * Compares 2 claims' statuses in reverse ordering
 */
class ClaimStatusReverseComparator implements Comparator<Claim> {

    @Override
    public int compare(Claim claim1, Claim claim2) {
        if (claim1.getStatus().equals(Status.PENDING) && claim2.getStatus().equals(Status.APPROVED)) {
            return 1;
        } else if (claim1.getStatus().equals(Status.PENDING) && claim2.getStatus().equals(Status.PENDING)) {
            return 0;
        } else if (claim1.getStatus().equals(Status.PENDING) && claim2.getStatus().equals(Status.REJECTED)) {
            return 1;
        } else if (claim1.getStatus().equals(Status.APPROVED) && claim2.getStatus().equals(Status.REJECTED)) {
            return 1;
        } else if (claim1.getStatus().equals(Status.APPROVED) && claim2.getStatus().equals(Status.APPROVED)) {
            return 0;
        } else if (claim1.getStatus().equals(Status.APPROVED) && claim2.getStatus().equals(Status.PENDING)) {
            return -1;
        } else if (claim1.getStatus().equals(Status.REJECTED) && claim2.getStatus().equals(Status.PENDING)) {
            return -1;
        } else if (claim1.getStatus().equals(Status.REJECTED) && claim2.getStatus().equals(Status.REJECTED)) {
            return 0;
        } else if (claim1.getStatus().equals(Status.REJECTED) && claim2.getStatus().equals(Status.APPROVED)) {
            return -1;
        } else {
            return 0;
        }
    }
}
