package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.exceptions.NegativeLimitException;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.purchase.Purchase;

/**
 * Manages the overall functionality of the Finance Tracker feature of JARVIS. The finance tracker manages purchases
 * spent by the user and instalments paid.
 */
public class FinanceTracker {
    private PurchaseList purchaseList;
    private InstallmentList installmentList;
    private double monthlyLimit;
    private double totalSpending;

    /**
     * Constructs an empty FinanceTracker to store data from the user.
     */
    public FinanceTracker() {
        purchaseList = new PurchaseList(new ArrayList<>());
        installmentList = new InstallmentList(new ArrayList<>());
        monthlyLimit = 0;
    }

    //=========== Reset Methods ==================================================================================

    /**
     * Constructs a FinanceTracker with reference from another FinanceTracker,
     * updating all existing fields from another FinanceTracker.
     *
     * @param financeTracker {@code HistoryManager} whose data this {@code HistoryManager} is taking reference from.
     */
    public FinanceTracker(FinanceTracker financeTracker) {
        this();
        resetData(financeTracker);
    }

    /**
     * Resets all data from {@code purchaseList} and {@code installmentList} from the given {@code financeTracker}.
     *
     * @param financeTracker
     */
    public void resetData(FinanceTracker financeTracker) {
        requireNonNull(financeTracker);
        this.purchaseList = new PurchaseList(financeTracker.getPurchaseList());
        this.installmentList = new InstallmentList(financeTracker.getInstallmentList());
    }

    //=========== Purchase List =======================================================================

    // ========== Setter Methods ======================================================================

    public void setPurchaseList(PurchaseList purchaseList) {
        this.purchaseList = purchaseList;
    }

    /**
     * Replaces the given purchase {@code target} in the list with {@code editedPurchase}.
     * {@code target} must exist in the finance tracker.
     * The identity of {@code editedPurchase} must not be the same as another existing purchase in the finance tracker.
     */
    public void setPurchase(Purchase target, Purchase editedPurchase) {
        requireNonNull(editedPurchase);

        purchaseList.setPurchase(target, editedPurchase);
    }

    //=========== Getter Methods ======================================================================

    public Purchase getPurchase(int paymentIndex) {
        return purchaseList.getPurchase(paymentIndex);
    }

    public ArrayList<Purchase> getPurchaseList() {
        return purchaseList.getAllPurchases();
    }


    //=========== Command Methods =====================================================================

    /**
     * Adds single use payment.
     *
     * @param purchase to be added to the finance tracker
     */
    public void addSinglePurchase(Purchase purchase) {
        purchaseList.addSinglePurchase(purchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber of payment to be deleted
     */
    public Purchase deleteSinglePurchase(int itemNumber) throws PurchaseNotFoundException {
        return purchaseList.deletePurchase(itemNumber);
    }

    /**
     * Returns the total number of purchases made.
     *
     * @return number of total purchases
     */
    public int getTotalPurchases() {
        return purchaseList.getNumPurchases();
    }

    //=========== Installment List ====================================================================

    //=========== Setter Methods ======================================================================

    public void setInstallmentList(InstallmentList installmentList) {
        this.installmentList = installmentList;
    }

    //=========== Getter Methods ======================================================================

    public Installment getInstallment(int instalIndex) throws InstallmentNotFoundException {
        return installmentList.getInstallment(instalIndex);
    }

    public ArrayList<Installment> getInstallmentList() {
        return installmentList.getAllInstallments();
    }

    /**
     * Returns the total number of installments made.
     *
     * @return number of total installments
     */
    public int getTotalInstallments() {
        return installmentList.getNumInstallments();
    }


    //=========== Command Methods =====================================================================

    /**
     * Adds instalment.
     *
     * @param installment to be added to the finance tracker
     */
    public void addInstallment(Installment installment) {
        installmentList.addInstallment(installment);
    }

    public Installment deleteInstallment(int instalNumber) throws InstallmentNotFoundException {
        return installmentList.deleteInstallment(instalNumber);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedInstallment}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     */
    public void setInstallment(Installment target, Installment editedInstallment) {
        installmentList.setInstallment(target, editedInstallment);
    }

    public boolean hasInstallment(Installment installment) {
        return installmentList.hasInstallment(installment);
    }

    //=========== General Finance Tracker =============================================================

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    public void setMonthlyLimit(double limit) {
        if (limit < 0) {
            throw new NegativeLimitException();
        } else {
            monthlyLimit = limit;
        }
    }

    /**
     * Lists all purchases and payments from this month.
     */
    public void listSpending() {
        totalSpending = purchaseList.totalSpending() + installmentList.getTotalMoneySpentOnInstallments();
        installmentList.toString();
        purchaseList.toString();
    }

    //=========== Common Methods ==================================================================================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && purchaseList.equals(((FinanceTracker) other).purchaseList)
                && installmentList.equals(((FinanceTracker) other).installmentList));
    }
}
