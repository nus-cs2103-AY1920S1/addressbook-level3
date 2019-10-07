package seedu.jarvis.model.financetracker;

import java.util.ArrayList;

/**
 * Manages the overall functionality of the Finance Tracker feature of JARVIS. The finance tracker manages purchases
 * spent by the user and instalments paid.
 */
public class FinanceTracker {
    private PurchaseList purchaseList;
    private InstalmentList instalmentList;
    private double monthlyLimit = 0;
    private double totalSpending;

    public FinanceTracker() {
        //todo assign all fields from existing model
        purchaseList = new PurchaseList(new ArrayList<>());
        instalmentList = new InstalmentList(new ArrayList<>());
    }

    public void setPurchaseList(PurchaseList purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void setInstalmentList(InstalmentList instalmentList) {
        this.instalmentList = instalmentList;
    }

    public Purchase getPayment(int paymentIndex) {
        return this.purchaseList.getPurchase(paymentIndex);
    }

    public Instalment getInstalment(int instalIndex) {
        return this.instalmentList.getInstalment(instalIndex);
    }

    /**
     * Adds single use payment.
     * @param purchase
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

    public int getTotalPurchases() {
        return purchaseList.getNumPurchases();
    }

    /**
     * Adds instalment.
     *
     * @param instalment
     */
    public void addInstalment(Instalment instalment) {
        instalmentList.addInstalment(instalment);
    }

    public Instalment deleteInstalment(int instalNumber) {
        return instalmentList.deleteInstalment(instalNumber);
    }
    /**
     * Deletes instalment.
     *
     * @param instalmentNumber of instalment to be deleted
     * @param description to be edited
     * @param value to be edited
     */
    public void editInstalment(int instalmentNumber, String description, double value) {
        instalmentList.editInstalment(instalmentNumber, description, value);
    }

    public int getTotalInstalments() {
        return instalmentList.getNumInstalments();
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    public void setMonthlyLimit(double limit) {
        assert limit > 0 : "your spending limit cannot be negative!";
        this.monthlyLimit = limit;
    }

    public double getMonthlyLimit() {
        return this.monthlyLimit;
    }

    /**
     * Lists all purchases and payments from this month.
     */
    public void listSpending() {
        totalSpending = purchaseList.totalSpending() + instalmentList.getTotalMoneySpentOnInstalments();
        instalmentList.toString(); //todo print this out nicely on UI
        purchaseList.toString(); //todo print this out nicely on UI
    }
}
