package com.typee.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import com.typee.commons.core.GuiSettings;
import com.typee.model.engagement.Engagement;
import com.typee.model.person.Person;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Engagement> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * REDUNDANT.
     * Returns true if the person is in the address book.
     */
    boolean hasEngagement(Engagement engagement);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteEngagement(Engagement target);

    /**
     * Adds the given engagement.
     * {@code engagement} must not already exist in the address book.
     */
    void addEngagement(Engagement engagement);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setEngagement(Engagement target, Engagement editedEngagement);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Engagement> getFilteredEngagementList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEngagementList(Predicate<Engagement> predicate);
}
