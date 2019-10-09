package seedu.jarvis.model.financetracker;

import java.util.ArrayList;

/**
 * Manages the overall functionality of the Finance Tracker feature of JARVIS. The finance tracker manages purchases
 * spent by the user and instalments paid.
 */
public class FinanceTracker {
    private PurchaseList purchaseList;
    private InstallmentList installmentList;
    private double monthlyLimit = 0;
    private double totalSpending;

    public FinanceTracker() {
        //todo assign all fields from existing model
        purchaseList = new PurchaseList(new ArrayList<>());
        installmentList = new InstallmentList(new ArrayList<>());
    }

    public void setPurchaseList(PurchaseList purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void setInstallmentList(InstallmentList installmentList) {
        this.installmentList = installmentList;
    }

    public Purchase getPayment(int paymentIndex) {
        return this.purchaseList.getPurchase(paymentIndex);
    }

    public Installment getInstallment(int instalIndex) {
        return this.installmentList.getInstallment(instalIndex);
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
     * @param installment
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

    public int getTotalInstallments() {
        return installmentList.getNumInstallments();
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
        totalSpending = purchaseList.totalSpending() + installmentList.getTotalMoneySpentOnInstallments();
        installmentList.toString(); //todo print this out nicely on UI
        purchaseList.toString(); //todo print this out nicely on UI
    }
}
