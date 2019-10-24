package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path accommodationFilePath = Paths.get("data" , "accommodation.json");
    private Path activityFilePath = Paths.get("data" , "activity.json");
    private Path contactFilePath = Paths.get("data" , "contact.json");
    private Path itineraryFilePath = Paths.get("data" , "itinerary.json");
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
        setAccommodationFilePath(newUserPrefs.getAccommodationFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAccommodationFilePath() {
        return accommodationFilePath;
    }

    public void setAccommodationFilePath(Path accommodationFilePath) {
        requireNonNull(accommodationFilePath);
        this.accommodationFilePath = accommodationFilePath;
    }

    public Path getActivityFilePath() {
        return activityFilePath;
    }

    public void setActivityFilePath(Path activityFilePath) {
        requireNonNull(activityFilePath);
        this.activityFilePath = activityFilePath;
    }

    public Path getContactFilePath() {
        return contactFilePath;
    }

    public void setContactFilePath(Path contactFilePath) {
        requireNonNull(contactFilePath);
        this.contactFilePath = contactFilePath;
    }

    public Path getItineraryFilePath() {
        return itineraryFilePath;
    }

    public void setItineraryFilePath(Path itineraryFilePath) {
        requireNonNull(itineraryFilePath);
        this.itineraryFilePath = itineraryFilePath;
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
                && accommodationFilePath.equals(o.accommodationFilePath)
                && activityFilePath.equals(o.activityFilePath)
                && contactFilePath.equals(o.contactFilePath)
                && itineraryFilePath.equals(o.itineraryFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, accommodationFilePath, activityFilePath, contactFilePath, itineraryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal Accommodation data file location : " + accommodationFilePath);
        sb.append("\nLocal Activity data file location : " + activityFilePath);
        sb.append("\nLocal Contact data file location : " + contactFilePath);
        sb.append("\nLocal Itinerary data file location : " + itineraryFilePath);
        return sb.toString();
    }

}
