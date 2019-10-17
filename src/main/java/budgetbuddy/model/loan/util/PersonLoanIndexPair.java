package budgetbuddy.model.loan.util;

import budgetbuddy.commons.core.index.Index;

/**
 * A pair of two indices: one indicating a person, the other indicating a loan.
 */
public class PersonLoanIndexPair {

    private final Index personIndex;
    private final Index loanIndex;

    public PersonLoanIndexPair(Index personIndex, Index loanIndex) {
        this.personIndex = personIndex;
        this.loanIndex = loanIndex;
    }

    public Index getPersonIndex() {
        return personIndex;
    }

    public Index getLoanIndex() {
        return loanIndex;
    }

    @Override
    public String toString() {
        return String.format("<Person: %d, Loan: %d>", personIndex.getOneBased(), loanIndex.getOneBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonLoanIndexPair)) {
            return false;
        }

        return personIndex.equals(((PersonLoanIndexPair) other).personIndex)
                && loanIndex.equals(((PersonLoanIndexPair) other).loanIndex);
    }
}
