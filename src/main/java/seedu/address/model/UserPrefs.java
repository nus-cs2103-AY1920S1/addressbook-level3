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
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path customerBookFilePath = Paths.get("data" , "customerbook.json");
    private Path phoneBookFilePath = Paths.get("data" , "phonebook.json");
    private Path scheduleBookFilePath = Paths.get("data" , "schedulebook.json");
    private Path orderBookFilePath = Paths.get("data" , "orderbook.json");
    private Path archivedOrderBookFilePath = Paths.get("data", "archivedbook.json");

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
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
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

    public Path getCustomerBookFilePath() {
        return customerBookFilePath;
    }

    public void setCustomerBookFilePath(Path customerBookFilePath) {
        requireNonNull(customerBookFilePath);
        this.customerBookFilePath = customerBookFilePath;
    }

    public Path getPhoneBookFilePath() {
        return phoneBookFilePath;
    }

    public void setPhoneBookFilePath(Path phoneBookFilePath) {
        requireNonNull(phoneBookFilePath);
        this.phoneBookFilePath = phoneBookFilePath;
    }

    public Path getScheduleBookFilePath() {
        return scheduleBookFilePath;
    }

    public void setScheduleBookFilePath(Path scheduleBookFilePath) {
        requireNonNull(scheduleBookFilePath);
        this.scheduleBookFilePath = scheduleBookFilePath;
    }

    public Path getOrderBookFilePath() {
        return orderBookFilePath;
    }

    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        this.orderBookFilePath = orderBookFilePath;
    }

    public Path getArchivedOrderBookFilePath() {
        return archivedOrderBookFilePath;
    }

    public void setArchivedOrderBookFilePath(Path archivedOrderBookFilePath) {
        requireNonNull(orderBookFilePath);
        this.archivedOrderBookFilePath = archivedOrderBookFilePath;
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
                && addressBookFilePath.equals(o.addressBookFilePath)
                && customerBookFilePath.equals(o.customerBookFilePath)
                && phoneBookFilePath.equals(o.phoneBookFilePath)
                && scheduleBookFilePath.equals(o.scheduleBookFilePath)
                && orderBookFilePath.equals(o.orderBookFilePath)
                && archivedOrderBookFilePath.equals(o.archivedOrderBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nCustomer data file location : " + customerBookFilePath);
        sb.append("\nPhone data file location : " + phoneBookFilePath);
        sb.append("\nSchedule data file location : " + scheduleBookFilePath);
        sb.append("\nOrder data file location : " + orderBookFilePath);
        return sb.toString();
    }

}
