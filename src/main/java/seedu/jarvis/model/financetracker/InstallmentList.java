package seedu.jarvis.model.financetracker;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Manages a list of instalments saved by the user.
 */
public class InstallmentList {
    private ArrayList<Installment> allInstallments;
    private double totalMoneySpentOnInstallments = 0;

    /**
     * Empty constructor to be used when there are no instalments previously stored by the user.
     */
    public InstallmentList() {
        allInstallments = new ArrayList<>();
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
     */
    public void addInstallment(Installment newInstallment) {
        allInstallments.add(newInstallment);
        totalMoneySpentOnInstallments = this.calculateTotalInstallmentSpending();
    }

    /**
     * User requests to edit a particular instalment based on its index. Either description or value can be changed,
     * but not both at the same time.
     * todo parse this command before this class (assign null if no change)
     */
    public void editInstallment(int installmentNumber, String description, double value) {
        //todo add exception for if the instalment does not exist and edit tests accordingly
        Objects.requireNonNull(description);
        allInstallments.get(installmentNumber - 1).editDescription(description);
        allInstallments.get(installmentNumber - 1).editAmount(value);
        totalMoneySpentOnInstallments = this.calculateTotalInstallmentSpending();
    }

    /**
     * Deletes instalment from the list of instalments based on the instalment number.
     * @param installmentNumber of the instalment in the list
     * @return Instalment object that has been removed from the list
     */
    public Installment deleteInstallment(int installmentNumber) {
        //todo check if the number is within the size of the list and edit tests accordingly
        return allInstallments.remove(installmentNumber - 1);
    }

    /**
     * Calculates the total monthly spending from all instalments currently subscribed to by the user.
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
        return this.totalMoneySpentOnInstallments;
    }

    public Installment getInstallment(int instalNum) {
        return allInstallments.get(instalNum - 1);
    }

    public int getNumInstallments() {
        return this.allInstallments.size();
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
}
