package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Body> PREDICATE_SHOW_ALL_BODIES = unused -> true;
    Predicate<Worker> PREDICATE_SHOW_ALL_WORKERS = unused -> true;

    /**
     * Adds an executed command to the model's command history.
     * @param command a command that was executed.
     */
    void addExecutedCommand(UndoableCommand command);

    /**
     * Gets the last executed UndoableCommand.
     * @return the last executed command.
     */
    UndoableCommand getExecutedCommand();

    /**
     * Adds an undone UndoableCommand.
     * @param command an UndoableCommand that was undone.
     */
    void addUndoneCommand(UndoableCommand command);

    /**
     * Gets the last undone UndoableCommand.
     * @returnthe last undone command.
     */
    UndoableCommand getUndoneCommand();

    /**
     * * Replaces user prefs data with the data in {@code userPrefs}.
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
     * Returns true if an entity with the same identity as {@code entity} exists in Mortago.
     */
    boolean hasEntity(Entity entity);

    /**
     * Deletes the given entity.
     * The entity must exist in Mortago.
     */
    void deleteEntity(Entity target);

    /**
     * Adds the given entity.
     * {@code entity} must not already exist in Mortago.
     */
    void addEntity(Entity entity);

    /**
     * Replaces the given entity {@code target} with {@code editedEntity}.
     * {@code target} must exist in Mortago.
     * The entity identity of {@code editedEntity} must not be the same as another existing entity in Mortago.
     */
    void setEntity(Entity target, Entity editedEntity);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of workers*/
    ObservableList<Worker> getFilteredWorkerList();

    /** Returns an unmodifiable view of the filtered list of bodies */
    ObservableList<Body> getFilteredBodyList();

    /** Returns an unmodifiable view of the filtered list of fridges */
    // ObservableList<Fridge> getFilteredFridgeList();

    /** Returns an unmodifiable view of the filtered list of entities */
    ObservableList<? extends Entity> getFilteredEntityList(String entityType);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered worker list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWorkerList(Predicate<Worker> predicate);

    /**
     * Updates the filter of the filtered body list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBodyList(Predicate<Body> predicate);
}
