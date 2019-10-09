package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private AppSettings appSettings = new AppSettings();
    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path timeBookFilePath = Paths.get("data", "timebook.json");
    private Path condensedModuleListFilePath = Paths.get("data", "condensed_module_list.json");
    private Path detailedModuleListFilePath = Paths.get("data", "detailed_module_list.json");
    private Path academicCalendarFilePath = Paths.get("data", "academic_calendar.json");
    private Path holidaysFilePath = Paths.get("data", "holidays.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

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
        setAppSettings(newUserPrefs.getAppSettings());
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getTimeBookFilePath() {
        return timeBookFilePath;
    }

    public void setTimeBookFilePath(Path timeBookFilePath) {
        requireNonNull(timeBookFilePath);
        this.timeBookFilePath = timeBookFilePath;
    }

    public Path getCondensedModuleListFilePath() {
        return condensedModuleListFilePath;
    }

    public void setCondensedModuleListFilePath(Path condensedModuleListFilePath) {
        this.condensedModuleListFilePath = condensedModuleListFilePath;
    }

    public Path getDetailedModuleListFilePath() {
        return detailedModuleListFilePath;
    }

    public void setDetailedModuleListFilePath(Path detailedModuleListFilePath) {
        this.detailedModuleListFilePath = detailedModuleListFilePath;
    }

    public Path getAcademicCalendarFilePath() {
        return academicCalendarFilePath;
    }

    public void setAcademicCalendarFilePath(Path academicCalendarFilePath) {
        this.academicCalendarFilePath = academicCalendarFilePath;
    }

    public Path getHolidaysFilePath() {
        return holidaysFilePath;
    }

    public void setHolidaysFilePath(Path holidaysFilePath) {
        this.holidaysFilePath = holidaysFilePath;
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

        return appSettings.equals(o.appSettings)
                && guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath)
                && timeBookFilePath.equals(o.timeBookFilePath)
                && condensedModuleListFilePath.equals(o.condensedModuleListFilePath)
                && detailedModuleListFilePath.equals(o.detailedModuleListFilePath)
                && academicCalendarFilePath.equals(o.academicCalendarFilePath)
                && holidaysFilePath.equals(o.holidaysFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appSettings, guiSettings, addressBookFilePath,
                timeBookFilePath, condensedModuleListFilePath, detailedModuleListFilePath,
                academicCalendarFilePath, holidaysFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App Settings : " + appSettings);
        sb.append("\nGui Settings : " + guiSettings);
        sb.append("\nAddressBook file location: " + addressBookFilePath);
        sb.append("\nTimeBook file location : " + timeBookFilePath);
        sb.append("\nCondensedModuleList file location : " + condensedModuleListFilePath);
        sb.append("\nDetailedModuleList file location : " + detailedModuleListFilePath);
        sb.append("\nAcademicCalendar file location : " + academicCalendarFilePath);
        sb.append("\nHolidays file location : " + holidaysFilePath);
        return sb.toString();
    }

}
