package seedu.jarvis.model.userprefs;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.jarvis.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path historyManagerFilePath = Paths.get("data", "historymanager.json");
    private Path ccaTrackerFilePath = Paths.get("data", "ccatracker.json");
    private Path coursePlannerFilePath = Paths.get("data", "courseplanner.json");
    private Path plannerFilePath = Paths.get("data", "planner.json");
    private Path financeTrackerFilePath = Paths.get("data", "financetracker.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setHistoryManagerFilePath(newUserPrefs.getHistoryManagerFilePath());
        setCcaTrackerFilePath(newUserPrefs.getCcaTrackerFilePath());
        setCoursePlannerFilePath(newUserPrefs.getCoursePlannerFilePath());
        setPlannerFilePath(newUserPrefs.getPlannerFilePath());
        setFinanceTrackerFilePath(newUserPrefs.getFinanceTrackerPath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getHistoryManagerFilePath() {
        return historyManagerFilePath;
    }

    public void setHistoryManagerFilePath(Path historyManagerFilePath) {
        requireNonNull(historyManagerFilePath);
        this.historyManagerFilePath = historyManagerFilePath;
    }

    @Override
    public Path getCcaTrackerFilePath() {
        return ccaTrackerFilePath;
    }

    public void setCcaTrackerFilePath(Path ccaTrackerFilePath) {
        requireNonNull(ccaTrackerFilePath);
        this.ccaTrackerFilePath = ccaTrackerFilePath;
    }

    @Override
    public Path getCoursePlannerFilePath() {
        return coursePlannerFilePath;
    }

    public void setCoursePlannerFilePath(Path coursePlannerFilePath) {
        requireNonNull(coursePlannerFilePath);
        this.coursePlannerFilePath = coursePlannerFilePath;
    }

    @Override
    public Path getPlannerFilePath() {
        return plannerFilePath;
    }

    public void setPlannerFilePath(Path plannerFilePath) {
        requireNonNull(plannerFilePath);
        this.plannerFilePath = plannerFilePath;
    }

    @Override
    public Path getFinanceTrackerPath() {
        return financeTrackerFilePath;
    }

    public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
        requireNonNull(financeTrackerFilePath);
        this.financeTrackerFilePath = financeTrackerFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && historyManagerFilePath.equals(o.historyManagerFilePath)
                && ccaTrackerFilePath.equals(o.ccaTrackerFilePath)
                && coursePlannerFilePath.equals(o.coursePlannerFilePath)
                && plannerFilePath.equals(o.plannerFilePath)
                && financeTrackerFilePath.equals(o.financeTrackerFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        return sb.toString();
    }

}
