package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.autocorrectsuggestion.UniqueAutocorrectSuggestionList;
import seedu.address.model.claim.ApprovedClaim;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.RejectedClaim;
import seedu.address.model.claim.UniqueClaimsList;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commanditem.UniqueCommandItemsList;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactsList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomesList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class FinSec implements ReadOnlyFinSec {

    private final UniqueContactsList persons;
    private final UniqueClaimsList claims;
    private final UniqueIncomesList incomes;
    private final UniqueAutocorrectSuggestionList suggestions;
    private final UniqueCommandItemsList commands;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueContactsList();
        claims = new UniqueClaimsList();
        incomes = new UniqueIncomesList();
        suggestions = new UniqueAutocorrectSuggestionList();
        commands = new UniqueCommandItemsList();

    }

    public FinSec() {}

    /**
     * Creates FinSec using the FinSec in the {@code toBeCopied}
     */
    public FinSec(ReadOnlyFinSec toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.persons.setContacts(contacts);
    }

    /**
     * Replaces the contents of the claims list with {@code claims}.
     * {@code claims} must not contain duplicate claims.
     */
    public void setClaims(List<Claim> claims) {
        this.claims.setClaims(claims);
    }

    /**
     * Replaces the contents of the income list with {@code incomes}.
     * {@code incomes} must not contain duplicate incomes.
     */
    public void setIncomes(List<Income> incomes) {
        this.incomes.setIncomes(incomes);
    }

    /**
     * Replaces the contents of the income list with {@code incomes}.
     * {@code incomes} must not contain duplicate incomes.
     */
    public void setAutocorrectSuggestions(List<AutocorrectSuggestion> suggestions) {
        this.suggestions.setAutocorrectSuggestions(suggestions);
    }

    /**
     * Resets the existing data of this {@code FinSec} with {@code newData}.
     */
    public void resetData(ReadOnlyFinSec newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
        setClaims(newData.getClaimList());
        setIncomes(newData.getIncomeList());
        setAutocorrectSuggestions(newData.getAutocorrectSuggestionList());
        setCommands(newData.getCommandsList());

    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return persons.contains(contact);
    }

    /**
     * Returns true if a contact with the following name and phone identity exists in the finsec.
     */
    public boolean hasContact(Name name) {
        requireAllNonNull(name);
        for (Contact contact : persons) {
            if (contact.getName().equals((name))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code contact} with the following name and phone identity in the finsec.
     */
    public Contact findContactFor(Name name) {
        requireAllNonNull(name);
        for (Contact contact : persons) {
            if (contact.getName().equals((name))) {
                return contact;
            }
        }
        return null;
    }

    /**
     * Returns {@code contact} with the following name and phone identity declared for the {@code claim} in the finsec.
     */
    public Contact findContactFor(Claim claim) {
        requireAllNonNull(claim);
        Name claimName = claim.getName();
        for (Contact contact : persons) {
            if (contact.getName().equals((claimName))) {
                return contact;
            }
        }
        return null;
    }

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addContact(Contact p) {
        persons.add(p);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in
     * the address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        persons.setPerson(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code FinSec}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        persons.remove(key);
    }

    /**
     * Returns true if a claim with the same identity as {@code claim} exists in the address book.
     */
    public boolean hasClaim(Claim claim) {
        requireNonNull(claim);
        return claims.contains(claim);
    }

    /**
     * Adds a claim to the finSec.
     * The claim must not already exist in the finSec.
     * Ensures contact exists for claim to be added into.
     */
    public void addClaim(Claim c) {
        claims.add(c);
        addClaimIntoContact(c);
    }

    /**
     * Replaces the given claim {@code target} in the list with {@code editedClaim}.
     * {@code target} must exist in the finSec.
     * The claim identity of {@code editedClaim} must not be the same as another existing claim in the address book.
     */
    public void setClaim(Claim target, Claim editedClaim) {
        requireNonNull(editedClaim);

        claims.setClaim(target, editedClaim);
    }

    /**
     * Removes {@code key} from this {@code FinSec}.
     * {@code key} must exist in the address book.
     */
    public void removeClaim(Claim key) {
        claims.remove(key);
    }

    /**
     * Approves the given claim {@code target} in the list.
     * {@code target} must exist in the finSec.
     */
    public void approveClaim(Claim target) {
        requireNonNull(target);

        claims.setClaim(target, new ApprovedClaim(target));
    }

    /**
     * Rejects the given claim {@code target} in the list.
     * {@code target} must exist in the finSec.
     */
    public void rejectClaim(Claim target) {
        requireNonNull(target);

        claims.setClaim(target, new RejectedClaim(target));
    }

    private void addClaimIntoContact(Claim c) {
        Contact targetContact = findContactFor(c);
        targetContact.addClaim(c);
    }

    /**
     * Returns true if an income with the same identity as {@code income} exists in the address book.
     */
    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return incomes.contains(income);
    }

    /**
     * Adds an income to the address book.
     * The income must not already be existing in the address book.
     */
    public void addIncome(Income income) {
        incomes.add(income);
    }

    /**
     * Replaces the income {@code target} in the list with {@code editedIncome}.
     * {@code target} must exist in the address book.
     * The income identity of {@code editedClaim} must not be the same as another existing income in the address book.
     */
    public void setIncome(Income target, Income editedIncome) {
        requireNonNull(editedIncome);

        incomes.setIncome(target, editedIncome);
    }

    /**
     * Removes {@code income} from this {@code FinSec}.
     * {@code income} must already exist in the address book.
     */
    public void removeIncome(Income income) {
        incomes.remove(income);
    }

    /**
     * Returns true if an income with the same identity as {@code income} exists in the address book.
     */
    public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        return suggestions.contains(suggestion);
    }

    /**
     * Adds an income to the address book.
     * The income must not already be existing in the address book.
     */
    public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        suggestions.add(suggestion);
    }

    /**
     * Replaces the income {@code target} in the list with {@code editedIncome}.
     * {@code target} must exist in the address book.
     * The income identity of {@code editedClaim} must not be the same as another existing income in the address book.
     */
    public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {

        suggestions.setAutocorrectSuggestion(target, editedSuggestion);
    }

    /**
     * Removes {@code income} from this {@code FinSec}.
     * {@code income} must already exist in the address book.
     */
    public void removeAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
        suggestions.remove(suggestion);
    }
    //// util methods


    /**
     * Returns true if a command with the same identity as {@code command} exists in the address book.
     */
    public boolean hasCommand(CommandItem command) {
        requireNonNull(command);
        return commands.contains(command);
    }

    /**
     * Adds a command to the address book.
     * The command must not already exist in the address book.
     */
    public void addCommand(CommandItem e) {
        commands.add(e);
    }

    /**
     * Removes {@code key} from this {@code FinSec}.
     * {@code key} must exist in the finsec.
     */
    public void removeCommand(CommandItem key) {
        commands.remove(key);
    }

    /**
     * Replaces the contents of the commands list with {@code commands}.
     * {@code commands} must not contain duplicate commands.
     */
    public void setCommands(List<CommandItem> commands) {
        this.commands.setCommands(commands);
    }

    /**
     * Replaces the given commands {@code target} in the list with {@code editedCommands}.
     * {@code target} must exist in the address book.
     * The command identity of {@code editedCommands} must not be the same as
     * another existing command in the address book.
     */
    public void setCommand(CommandItem target, CommandItem editedCommand) {
        requireNonNull(editedCommand);

        commands.setCommand(target, editedCommand);
    }


    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<seedu.address.model.contact.Contact> getContactList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        return incomes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Claim> getClaimList() {
        return claims.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<AutocorrectSuggestion> getAutocorrectSuggestionList() {
        return suggestions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<CommandItem> getCommandsList() {
        return commands.asUnmodifiableObservableList();
    }

    /**
     * Checks whether if this FinSec object equals to another.
     * @param other The other FinSec object to be checked.
     * @return Boolean value for equality against {@code other}.
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinSec // instanceof handles nulls
                && persons.equals(((FinSec) other).persons)
                && claims.equals(((FinSec) other).claims)
                && incomes.equals(((FinSec) other).incomes));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
