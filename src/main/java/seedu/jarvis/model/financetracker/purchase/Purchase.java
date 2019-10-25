package seedu.jarvis.model.financetracker.purchase;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Purchase object stores a single payment including its details such as the description and the money spent.
 */
public class Purchase {

    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static DecimalFormat df2 = new DecimalFormat("#.00");

    private PurchaseDescription description;
    private PurchaseMoneySpent moneySpent;
    private LocalDate dateOfPurchase;

    public Purchase(PurchaseDescription description, PurchaseMoneySpent moneySpent, LocalDate dateOfPurchase) {
        requireNonNull(description);
        requireNonNull(moneySpent);
        requireNonNull(dateOfPurchase);
        this.description = description;
        this.moneySpent = moneySpent;
        this.dateOfPurchase = dateOfPurchase;
    }

    //=========== Reset Methods ==================================================================================

    public Purchase(Purchase purchase) {
        resetData(purchase);
    }

    /**
     * Resets all data from {@code description}, {@code moneySpent} and {@code dateOfPurchase} from the given
     * {@code purchase}.
     *
     * @param purchase
     */
    public void resetData(Purchase purchase) {
        requireNonNull(purchase);
        this.description = purchase.getDescription();
        this.moneySpent = purchase.getMoneySpent();
        this.dateOfPurchase = purchase.getDateOfPurchase();
    }

    //=========== Getter Methods ==================================================================================

    public PurchaseDescription getDescription() {
        return description;
    }

    public PurchaseMoneySpent getMoneySpent() {
        return moneySpent;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    /**
     * Retrieves the DateTimeFormatter used for Event and Deadline tasks
     */
    public static DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public String toString() {
        return description.getPurchaseDescription()
                + " ($" + df2.format(moneySpent.getPurchaseAmount())
                + ") on: " + dateOfPurchase;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Purchase // instanceof handles nulls
                && description.equals(((Purchase) other).description)
                && moneySpent.equals(((Purchase) other).moneySpent)
                && dateOfPurchase.equals(((Purchase) other).dateOfPurchase));
    }

    public boolean isSamePurchase(Purchase purchase) {
        return this.equals(purchase);
    }
}
