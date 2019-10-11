package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.jarvis.model.financetracker.exceptions.NegativeLimitException;

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

    //=========== Setter Methods ==================================================================================

    public void setPurchaseList(PurchaseList purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void setInstallmentList(InstallmentList installmentList) {
        this.installmentList = installmentList;
    }

    //=========== Getter Methods ==================================================================================

    public Purchase getPurchase(int paymentIndex) {
        return purchaseList.getPurchase(paymentIndex);
    }

    public Installment getInstallment(int instalIndex) {
        return installmentList.getInstallment(instalIndex);
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public PurchaseList getPurchaseList() {
        return purchaseList;
    }

    public InstallmentList getInstallmentList() {
        return installmentList;
    }

    //=========== Purchase List Command Methods =======================================================================

    /**
     * Adds single use payment.
     *
     * @param purchase to be added to the finance tracker
     */
    public void addSinglePayment(Purchase purchase) {
        purchaseList.addSinglePurchase(purchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber of payment to be deleted
     */
    public Purchase deleteSinglePayment(int itemNumber) {
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

    //=========== Installment List Command Methods ====================================================================

    /**
     * Adds instalment.
     *
     * @param installment to be added to the finance tracker
     */
    public void addInstallment(Installment installment) {
        installmentList.addInstallment(installment);
    }

    public Installment deleteInstallment(int instalNumber) {
        return installmentList.deleteInstallment(instalNumber);
    }

    /**
     * Deletes instalment.
     *
     * @param installmentNumber of instalment to be deleted
     * @param description to be edited
     * @param value to be edited
     */
    public void editInstallment(int installmentNumber, String description, double value) {
        installmentList.editInstallment(installmentNumber, description, value);
    }

    /**
     * Returns the total number of installments made.
     *
     * @return number of total installments
     */
    public int getTotalInstallments() {
        return installmentList.getNumInstallments();
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
