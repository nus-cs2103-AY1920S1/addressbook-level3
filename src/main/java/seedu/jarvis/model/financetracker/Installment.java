package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

/**
 * Installment object stores a single recurring payment with its details such as description and the amount spent.
 */
public class Installment {
    private String description;
    private double moneySpentOnInstallment;

    public Installment(String description, double moneySpentOnInstallment) {
        requireNonNull(description);
        this.description = description;
        this.moneySpentOnInstallment = moneySpentOnInstallment;
    }

    //=========== Getter Methods ==================================================================================

    public String getDescription() {
        return this.description;
    }

    public double getMoneySpentOnInstallment() {
        return this.moneySpentOnInstallment;
    }

    //=========== Edit Methods ==================================================================================

    /**
     * Edits the description of an existing installment.
     *
     * @param newDescription to replace original description of installment
     */
    public void editDescription(String newDescription) {
        requireNonNull(newDescription);
        this.description = newDescription;
    }

    public void editAmount(double newMoney) {
        moneySpentOnInstallment = newMoney;
    }

    @Override
    public String toString() {
        return description + ", " + moneySpentOnInstallment;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Installment // instanceof handles nulls
                && description.equals(((Installment) other).description)
                && moneySpentOnInstallment == ((Installment) other).moneySpentOnInstallment);
    }
}
