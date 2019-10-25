package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.address.person.exceptions.DuplicateInstallmentException;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;

/**
 * Manages a list of instalments saved by the user.
 */
public class InstallmentList {
    private ObservableList<Installment> internalInstallmentList = FXCollections.observableArrayList();
    private final ObservableList<Installment> internalUnmodifiableInstallmentList =
            FXCollections.unmodifiableObservableList(internalInstallmentList);
    private double totalMoneySpentOnInstallments;

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public InstallmentList() {
        totalMoneySpentOnInstallments = 0;
    }

    //=========== Reset Methods ==================================================================================

    /**
     * Constructs an InstallmentList with reference from another InstallmentList,
     * updating all existing fields from another InstallmentList.
     */
    public InstallmentList(ObservableList<Installment> internalInstallmentList) {
        requireNonNull(internalInstallmentList);
        this.internalInstallmentList = internalInstallmentList;
    }

    public void setInstallments(List<Installment> listInstallments) {
        requireNonNull(listInstallments);

        internalInstallmentList.setAll(listInstallments);
    }

    //=========== Getter Methods ==================================================================================

    public double getTotalMoneySpentOnInstallments() {
        return calculateTotalInstallmentSpending();
    }

    public ObservableList<Installment> getInternalInstallmentList() {
        return internalInstallmentList;
    }

    public ObservableList<Installment> asUnmodifiableObservableList() {
        return internalUnmodifiableInstallmentList;
    }

    /**
     * Retrieves the installment at that particular index.
     *
     * @param installmentNumber of the installment to be retrieved as seen on the list of installments
     * @return Installment
     * @throws InstallmentNotFoundException if the index is greater than the number of installments
     */
    public Installment getInstallment(int installmentNumber) {
        Index index = Index.fromOneBased(installmentNumber);
        if (index.getZeroBased() < 0 || index.getZeroBased() >= getNumInstallments()) {
            throw new InstallmentNotFoundException();
        }
        return internalInstallmentList.get(index.getZeroBased());
    }

    /**
     * Returns true if the list contains an equivalent installment as the given argument.
     */
    private boolean contains(Installment toCheck) {
        requireNonNull(toCheck);
        return internalInstallmentList.stream().anyMatch(toCheck::isSameInstallment);
    }

    /**
     * Checks for the existence of the installment that has already been added to avoid duplicates in the list.
     *
     * @param installment that is to be newly added
     * @return boolean checking the existence of the same installment
     */
    public boolean hasInstallment(Installment installment) {
        return this.contains(installment);
    }

    public int getNumInstallments() {
        return internalInstallmentList.size();
    }

    //=========== Command Methods ==================================================================================

    /**
     * Add installment to the list of installments.
     *
     * @param newInstallment to be added
     */
    public void addInstallment(Installment newInstallment) {
        requireNonNull(newInstallment);
        internalInstallmentList.add(newInstallment);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * Add installment to the list at the given index.
     *
     * @param newInstallment to be added
     * @param zeroBasedIndex index where the installment should be added
     */
    public void addInstallment(int zeroBasedIndex, Installment newInstallment) {
        requireNonNull(newInstallment);
        internalInstallmentList.add(zeroBasedIndex, newInstallment);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * Deletes instalment from the list of instalments based on the instalment number.
     *
     * @param installmentNumber of the instalment in the list
     * @return Instalment object that has been removed from the list
     * @throws InstallmentNotFoundException if the installment does not exist
     */
    public Installment deleteInstallment(int installmentNumber) {
        try {
            Index index = Index.fromOneBased(installmentNumber);
            Installment deletedInstallment = internalInstallmentList.remove(index.getZeroBased());
            totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
            return deletedInstallment;
        } catch (IndexOutOfBoundsException e) {
            throw new InstallmentNotFoundException();
        }
    }

    /**
     * Removes installment from the list of installments.
     */
    public Installment deleteInstallment(Installment installment) {
        internalInstallmentList.remove(installment);
        return installment;
    }

    /**
     * Replaces the installment {@code target} in the list with {@code editedInstallment}.
     *
     * {@code target} must exist in the list.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     */
    public void setInstallment(Installment target, Installment editedInstallment) {
        requireAllNonNull(target, editedInstallment);

        int index = internalInstallmentList.indexOf(target);
        if (index == -1) {
            throw new InstallmentNotFoundException();
        }

        if (!target.isSameInstallment(editedInstallment) && contains(editedInstallment)) {
            throw new DuplicateInstallmentException();
        }

        internalInstallmentList.set(index, editedInstallment);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * Calculates the total monthly spending from all instalments currently subscribed to by the user.
     *
     * @return double containing the total money spent to be included in monthly expenditure
     */
    private double calculateTotalInstallmentSpending() {
        double amount = 0;
        for (Installment instalment : internalInstallmentList) {
            amount += instalment.getMoneySpentOnInstallment().getInstallmentMoneyPaid();
        }
        return amount;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstallmentList // instanceof handles nulls
                && internalInstallmentList.equals(((InstallmentList) other).internalInstallmentList));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are your current subscriptions: ");
        for (Installment installment : internalInstallmentList) {
            sb.append(installment);
            sb.append("\n");
        }
        return sb.toString();
    }
}
