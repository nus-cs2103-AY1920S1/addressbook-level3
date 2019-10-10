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

    //=========== Reset Methods ==================================================================================

    public Installment(Installment installment) {
        resetData(installment);
    }

    /**
     * Resets all data from {@code description} and {@code moneySpentOnInstallment} from the given {@code installment}.
     *
     * @param installment
     */
    public void resetData(Installment installment) {
        requireNonNull(installment);
        this.description = installment.getDescription();
        this.moneySpentOnInstallment = installment.getMoneySpentOnInstallment();
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

    //=========== Common Methods ==================================================================================

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
