package seedu.deliverymans.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.deliverymans.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path customerDatabaseFilePath = Paths.get("data", "customerdatabase.json");
    private Path deliverymenDatabaseFilePath = Paths.get("data" , "deliverymendatabase.json");
    private Path restaurantDatabaseFilePath = Paths.get("data" , "restaurantdatabase.json");
    private Path orderBookFilePath = Paths.get("data" , "orderbook.json");

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
        setCustomerDatabaseFilePath(newUserPrefs.getCustomerDatabaseFilePath());
        setDeliverymenDatabaseFilePath(newUserPrefs.getDeliverymenDatabaseFilePath());
        setRestaurantDatabaseFilePath(newUserPrefs.getRestaurantDatabaseFilePath());
        setOrderBookFilePath(newUserPrefs.getOrderBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getCustomerDatabaseFilePath() {
        return customerDatabaseFilePath;
    }

    public void setCustomerDatabaseFilePath(Path customerDatabaseFilePath) {
        requireNonNull(customerDatabaseFilePath);
        this.customerDatabaseFilePath = customerDatabaseFilePath;
    }

    public Path getDeliverymenDatabaseFilePath() {
        return deliverymenDatabaseFilePath;
    }

    public void setDeliverymenDatabaseFilePath(Path deliverymenDatabaseFilePath) {
        requireNonNull(deliverymenDatabaseFilePath);
        this.deliverymenDatabaseFilePath = deliverymenDatabaseFilePath;
    }

    public Path getRestaurantDatabaseFilePath() {
        return restaurantDatabaseFilePath;
    }

    public void setRestaurantDatabaseFilePath(Path restaurantDatabaseFilePath) {
        requireNonNull(restaurantDatabaseFilePath);
        this.restaurantDatabaseFilePath = restaurantDatabaseFilePath;
    }

    public Path getOrderBookFilePath() {
        return orderBookFilePath;
    }

    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        this.orderBookFilePath = orderBookFilePath;
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
                && customerDatabaseFilePath.equals(o.customerDatabaseFilePath)
                && deliverymenDatabaseFilePath.equals(o.deliverymenDatabaseFilePath)
                && restaurantDatabaseFilePath.equals(o.restaurantDatabaseFilePath)
                && orderBookFilePath.equals(o.orderBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, customerDatabaseFilePath, deliverymenDatabaseFilePath,
                restaurantDatabaseFilePath, orderBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + customerDatabaseFilePath + " , " + deliverymenDatabaseFilePath
                + " , " + restaurantDatabaseFilePath + " , " + orderBookFilePath);
        return sb.toString();
    }

}
