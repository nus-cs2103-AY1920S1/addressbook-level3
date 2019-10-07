package seedu.jarvis.model.financetracker;

import java.util.ArrayList;

/**
 * Manages a list of instalments saved by the user.
 */
public class InstalmentList {
    private ArrayList<Instalment> allInstallments;
    private double totalMoneySpentOnInstalments = 0;

    /**
     * Empty constructor to be used when there are no instalments previously stored by the user.
     */
    public InstalmentList() {
        allInstallments = new ArrayList<>();
    }

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public InstalmentList(ArrayList<Instalment> allInstallments) {
        if (allInstallments.size() == 0) {
            //todo throw exception for no existing payments owed
        } else {
            this.allInstallments = allInstallments;
            this.totalMoneySpentOnInstalments = calculateTotalInstalmentSpending();
        }
    }

    /**
     * Add installment to the list of installments
     */
    public void addInstalment(Instalment newInstalment) {
        allInstallments.add(newInstalment);
        totalMoneySpentOnInstalments = this.calculateTotalInstalmentSpending();
    }

    /**
     * User requests to edit a particular instalment based on its index. Either description or value can be changed,
     * but not both at the same time.
     * todo parse this command before this class (assign null if no change)
     */
    public void editInstalment(int instalmentNumber, String description, double value) {
        if (instalmentNumber > allInstallments.size()) {
            //todo throw exception that instalment does not exist
        } else if (description == null) {
            allInstallments.get(instalmentNumber - 1).editAmount(value);
        } else if ((Double) value == null) {
            allInstallments.get(instalmentNumber - 1).editDescription(description);
        }
        totalMoneySpentOnInstalments = this.calculateTotalInstalmentSpending();
    }

    /**
     * Deletes instalment from the list of instalments based on the instalment number.
     * @param instalmentNumber of the instalment in the list
     * @return Instalment object that has been removed from the list
     */
    public Instalment deleteInstalment(int instalmentNumber) {
        if (instalmentNumber > allInstallments.size()) {
            //todo throw error
        }
        return allInstallments.remove(instalmentNumber - 1);
    }

    /**
     * Calculates the total monthly spending from all instalments currently subscribed to by the user.
     * @return double containing the total money spent to be included in monthly expenditure
     */
    private double calculateTotalInstalmentSpending() {
        double amount = 0;
        for (Instalment instalment : allInstallments) {
            amount += instalment.getMoneySpentOnInstallment();
        }
        return amount;
    }

    public double getTotalMoneySpentOnInstalments() {
        return this.totalMoneySpentOnInstalments;
    }

    @Override
    public String toString() {
        String lstInstallments = "Here are your current subscriptions: + \n";
        int index = 1;
        for (Instalment instalment : allInstallments) {
            lstInstallments += index + ". " + instalment.toString();
        }
        return lstInstallments;
    }
}
