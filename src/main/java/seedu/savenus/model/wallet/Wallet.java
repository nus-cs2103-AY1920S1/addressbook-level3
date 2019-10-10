package seedu.savenus.model.wallet;
/*
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
*/

/**
 * Represents a user's Wallet in the application.
 * Guarantees: mutable;
 * JSON File should have wallet: { currentBalance: 0, daysToExpire: 0 } property
 */
public class Wallet {
    // Default Properties
    private CurrentBalance currentBalance;
    private DaysToExpire daysToExpire;

    // private final FloatProperty currentBalanceProperty = new SimpleFloatProperty(0);
    // private final IntegerProperty daysToExpire = new SimpleIntegerProperty(0);

    public Wallet() {
        this.currentBalance = new CurrentBalance("0");
        this.daysToExpire = new DaysToExpire("0");
    }

    public Wallet(CurrentBalance currentBalance, DaysToExpire daysToExpire) {
        this.currentBalance = currentBalance;
        this.daysToExpire = daysToExpire;
    }

    public float getCurrentBalance() {
        return currentBalance.getCurrentBalance();
    }

    public String getFormattedCurrentBalance() {
        return "$" + currentBalance.toString();
    }

    public String getFormattedDaysToExpire() {
        return daysToExpire.toString() + " days";
    }

    public int getDaysToExpire() {
        return daysToExpire.getDaysToExpire();
    }


    public final void setCurrentBalance(String currentBalanceStr) {
        this.currentBalance = new CurrentBalance(currentBalanceStr);
    }

    /*
    public final void setDaysToExpire(String daysToExpireStr) {
        this.daysToExpire = new DaysToExpire(daysToExpireStr);
    }

    public final FloatProperty getCurrentBalanceProperty() {
        return currentBalance;
    }

    public final IntegerProperty getDaysToExpireProperty() {
        return daysToExpire;
    }

    public void setup(CurrentBalance currentBalance, DaysToExpire daysToExpire) {
        this.currentBalance = new SimpleFloatProperty(currentBalance.getCurrentBalance());
        this.daysToExpire = new SimpleIntegerProperty(daysToExpire.getDaysToExpire());
    }
    */

    @Override
    public String toString() {
        return "Current Balance: " + this.getCurrentBalance() + "\n"
                + "Days to Expire: " + this.getDaysToExpire();
    }
}
