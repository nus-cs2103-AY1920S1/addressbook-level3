package seedu.jarvis.model.financetracker;

/**
 * Object stores a single recurring payment with its details such as description and the amount spent.
 */
public class Instalment {
    private String description;
    private double moneySpentOnInstallment;

    public Instalment(String description, double moneySpentOnInstallment) {
        this.description = description;
        this.moneySpentOnInstallment = moneySpentOnInstallment;
    }

    /**
     * GETTER METHODS
     */
    public String getDescription() {
        return this.description;
    }

    public double getMoneySpentOnInstallment() {
        return this.moneySpentOnInstallment;
    }

    /**
     * EDIT METHODS
     */
    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    public void editAmount(double newMoney) {
        this.moneySpentOnInstallment = newMoney;
    }

    @Override
    public String toString() {
        return this.description + ", " + this.moneySpentOnInstallment;
    }

}
