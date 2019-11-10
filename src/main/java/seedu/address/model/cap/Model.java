package seedu.address.model.cap;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.common.Module;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;
    Predicate<Semester> PREDICATE_SHOW_ALL_SEMESTERS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setCapUserPrefs(ReadOnlyUserPrefs capUserPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getCapUserPrefs();

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
    Path getCapLogFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setCapLogFilePath(Path capLogFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setCapLog(ReadOnlyCapLog capLog);

    /** Returns the AddressBook */
    ReadOnlyCapLog getCapLog();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasSemester(Semester semester);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteSemester(Semester target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addSemester(Semester module);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setSemester(Semester target, Semester editedModule);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Semester> getFilteredSemesterList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSemesterList(Predicate<Semester> predicate);

    /////////////MODULE OPERATIONS//////////////
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteModule(Module module);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addModule(Module module);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int getModuleCount();

    double getFilteredCapInformation();

    double getFilteredMcInformation();

    ObservableList<PieChart.Data> getFilteredGradeCounts();

    boolean downRank();

    boolean upRank();

    Image getRankImage();

    String getRankTitle();

    void updateRank(double cap);
}
