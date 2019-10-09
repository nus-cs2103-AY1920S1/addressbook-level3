package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;


/**
 * Manages a list of instalments saved by the user.
 */
public class InstallmentList {
    private ArrayList<Installment> allInstallments;
    private double totalMoneySpentOnInstallments;

    /**
     * Empty constructor to be used when there are no instalments previously stored by the user.
     */
    public InstallmentList() {
        allInstallments = new ArrayList<>();
        totalMoneySpentOnInstallments = 0;
    }

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public InstallmentList(ArrayList<Installment> allInstallments) {
        this.allInstallments = allInstallments;
        this.totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * Add installment to the list of installments
     * @param newInstallment to be added
     */
    public void addInstallment(Installment newInstallment) {
        allInstallments.add(newInstallment);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * User requests to edit a particular instalment based on its index. Either description or value can be changed,
     * but not both at the same time.
     *
     * @param installmentNumber of the installment to be edited
     * @param description of the installment to be edited
     * @param value of the installment to be edited
     */
    public void editInstallment(int installmentNumber, String description, double value) {
        if (installmentNumber < 1) {
            throw new InstallmentNotFoundException();
        } else {
            requireNonNull(description);
            Index index = Index.fromOneBased(installmentNumber);
            allInstallments.get(index.getZeroBased()).editDescription(description);
            allInstallments.get(index.getZeroBased()).editAmount(value);
            totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
        }
    }

    /**
     * Deletes instalment from the list of instalments based on the instalment number.
     *
     * @param installmentNumber of the instalment in the list
     * @return Instalment object that has been removed from the list
     */
    public Installment deleteInstallment(int installmentNumber) {
        if (installmentNumber < 1) {
            throw new InstallmentNotFoundException();
        } else {
            Index index = Index.fromOneBased(installmentNumber);
            return allInstallments.remove(index.getZeroBased());
        }
    }

    /**
     * Calculates the total monthly spending from all instalments currently subscribed to by the user.
     *
     * @return double containing the total money spent to be included in monthly expenditure
     */
    private double calculateTotalInstallmentSpending() {
        double amount = 0;
        for (Installment instalment : allInstallments) {
            amount += instalment.getMoneySpentOnInstallment();
        }
        return amount;
    }

    public double getTotalMoneySpentOnInstallments() {
        return totalMoneySpentOnInstallments;
    }

    public Installment getInstallment(int installmentNumber) {
        Index index = Index.fromOneBased(installmentNumber);
        return allInstallments.get(index.getZeroBased());
    }

    public int getNumInstallments() {
        return allInstallments.size();
    }

    @Override
    public String toString() {
        String lstInstallments = "Here are your current subscriptions: + \n";
        int index = 1;
        for (Installment installment : allInstallments) {
            lstInstallments += index + ". " + installment.toString();
        }
        return lstInstallments;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstallmentList // instanceof handles nulls
                && allInstallments.equals(((InstallmentList) other).allInstallments));
    }
}
