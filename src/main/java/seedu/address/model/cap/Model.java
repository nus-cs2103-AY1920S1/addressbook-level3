package seedu.address.model.cap;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.cap.module.Semester;
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
     * Returns the user prefs' modulo file path.
     */
    Path getCapLogFilePath();

    /**
     * Sets the user prefs' modulo file path.
     */
    void setCapLogFilePath(Path capLogFilePath);

    /**
     * Replaces modulo data with the data in {@code modulo}.
     */
    void setCapLog(ReadOnlyCapLog capLog);

    /** Returns the CapLog */
    ReadOnlyCapLog getCapLog();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the modulo.
     */
    boolean hasSemester(Semester semester);

    /**
     * Deletes the given module.
     * The semester must exist in the modulo.
     */
    void deleteSemester(Semester target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the modulo.
     */
    void addSemester(Semester module);

    /**
     * Replaces the given semester {@code target} with {@code editedPerson}.
     * {@code target} must exist in the modulo.
     * The module identity of {@code editedSemester} must not be the same as another existing module in the modulo.
     */
    void setSemester(Semester target, Semester editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Semester> getFilteredSemesterList();

    /**
     * Updates the filter of the filtered semester list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSemesterList(Predicate<Semester> predicate);

    /////////////MODULE OPERATIONS//////////////
    /**
     * Returns true if a module with the same identity as {@code module} exists in the modulo.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the modulo.
     */
    void deleteModule(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the modulo.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the modulo.
     * The module identity of {@code eeditedModule} must not be the same as another existing module in the modulo.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     */
    FilteredList<Module> getFilteredListbyTime();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     */
    void setSortedList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
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
