package seedu.ichifund.model.loan;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;

/**
 * Loan class for storing objects of Loan type.
 */
public class Loan implements Comparable<Loan> {
    private LoanId loanId;
    private Amount amount;
    private Name name;
    private Date startDate;
    private Date endDate;
    private Description description;

    public Loan(LoanId loanId, Amount amount, Name name, Date startDate, Date endDate, Description description) {

        requireAllNonNull(loanId, amount, startDate);

        this.loanId = loanId;
        this.amount = amount;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public void setLoanId(LoanId loanId) {
        this.loanId = loanId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }



    /**
     * ONly returns true if 2 loan objects have exact same paramenters,
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return otherLoan.getLoanId().equals(getLoanId())
                && otherLoan.getAmount().equals(getAmount())
                && otherLoan.getName().equals(getName())
                && otherLoan.getEndDate().equals(getEndDate())
                && otherLoan.getStartDate().equals(getStartDate())
                && otherLoan.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, amount, name, startDate, endDate, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" LoanId: ")
                .append(getLoanId())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Name: ")
                .append(getName())
                .append(" Taken Date: ")
                .append(getStartDate())
                .append(" Deadline Date: ")
                .append(getEndDate())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

    @Override
    public int compareTo(Loan other) {
        int dateComparison = getStartDate().compareTo(other.getStartDate());
        int amountComparison = getAmount().compareTo(other.getAmount());
        if (dateComparison != 0) {
            return dateComparison;
        } else if (amountComparison != 0) {
            return amountComparison;
        } else {
            return getDescription().compareTo(other.getDescription());
        }
    }
}
