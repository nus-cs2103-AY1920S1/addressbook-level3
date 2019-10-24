package seedu.jarvis.model.financetracker.installment;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;

/**
 * Installment object stores a single recurring payment with its details such as description and the amount spent.
 */
public class Installment {

    private static DecimalFormat df2 = new DecimalFormat("#.00");

    private InstallmentDescription description;
    private InstallmentMoneyPaid moneySpentOnInstallment;

    public Installment(InstallmentDescription description, InstallmentMoneyPaid moneySpentOnInstallment) {
        requireNonNull(description);
        requireNonNull(moneySpentOnInstallment);
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

    public InstallmentDescription getDescription() {
        return this.description;
    }

    public InstallmentMoneyPaid getMoneySpentOnInstallment() {
        return this.moneySpentOnInstallment;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public String toString() {
        return description.getInstallmentDescription()
                + ", $" + df2.format(moneySpentOnInstallment.getInstallmentMoneyPaid());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Installment // instanceof handles nulls
                && description.equals(((Installment) other).description)
                && moneySpentOnInstallment.equals(((Installment) other).moneySpentOnInstallment));
    }

    public boolean isSameInstallment(Installment installment) {
        return this.equals(installment);
    }
}
