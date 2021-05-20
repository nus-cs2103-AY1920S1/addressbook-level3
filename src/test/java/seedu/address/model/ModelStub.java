package seedu.address.model;

import java.nio.file.Path;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }



    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getFinSecFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFinSecFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFinSec(ReadOnlyFinSec finSec) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyFinSec getFinSec() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasContact(Contact contact) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteContact(Contact target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addContact(Contact contact) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasClaim(Claim claim) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteClaim(Claim target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addClaim(Claim claim) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void approveClaim(Claim claim) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void rejectClaim(Claim claim) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setClaim(Claim target, Claim editedClaim) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasContactFor(Claim target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasIncome(Income income) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteIncome(Income target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addIncome(Income income) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setIncome(Income target, Income editedIncome) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCommand(CommandItem command) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCommand(CommandItem command) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCommand(CommandItem command) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCommand(CommandItem command, CommandItem editedCommand) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveCommand(String command) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getSavedCommand() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredContactListByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReverseFilteredContactListByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Claim> getFilteredClaimList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredClaimListByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredClaimListByDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredClaimListByStatus() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReverseFilteredClaimListByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReverseFilteredClaimListByDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReverseFilteredClaimListByStatus() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredClaimList(Predicate<Claim> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredIncomeListByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFilteredIncomeListByDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReverseFilteredIncomeListByName() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReverseFilteredIncomeListByDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredIncomeList(Predicate<Income> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAutocorrectSuggestionList(Predicate<AutocorrectSuggestion> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<CommandItem> getFilteredCommandsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredCommandsList(Predicate<CommandItem> predicate) {
        throw new AssertionError("This method should not be called.");
    }
}
