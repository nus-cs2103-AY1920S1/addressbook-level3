package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an expense by a person in an activity.
 * Guarantees: details are present and not null, field values are validated, immutable
 * except only isDeleted is mutable.
 */
public class Expense {
    private final int personId;
    private int[] involvedIds;
    private final Amount amount;
    private final String description;
    private final boolean isSettlement;
    private boolean isDeleted;

    /**
     * Constructor for Expense.
     * @param personId ID of the person who paid.
     * @param amount The amount paid.
     * @param description Description of the expense (can be an empty string).
     */
    public Expense(int personId, Amount amount, String description) {
        requireAllNonNull(personId, amount, description);
        this.personId = personId;
        this.amount = amount;
        this.description = description;
        this.isDeleted = false;
        this.isSettlement = false;
        involvedIds = null;
    }

    public Expense(int personId, Amount amount, String description, int ... ids) {
        this(personId, amount, description);
        requireAllNonNull(ids);
        this.involvedIds = ids;
    }

    /**
     * Constructor for Expense.
     * @param personId ID of the person who paid.
     * @param amount The amount paid.
     * @param description Description of the expense (can be an empty string).
     * @param isSettlement The flag to indicate whether this is a settlement expense.
     */
    public Expense(int personId, Amount amount, String description, boolean isSettlement) {
        requireAllNonNull(personId, amount, description);
        this.personId = personId;
        this.amount = amount;
        this.description = description;
        this.isDeleted = false;
        this.isSettlement = isSettlement;
        involvedIds = null;
    }

    public Expense(int personId, Amount amount, String description, boolean isSettlement, int ... ids) {
        this(personId, amount, description, isSettlement);
        requireAllNonNull(ids);
        this.involvedIds = ids;
    }

    /**
     * Returns an array of all the primary keys of involved people in this expense.
     * Note that this function can return null, in that case it means no list has
     * been provided to the constructor and you may assume everyone is involved.
     * Usually after adding to an activity this gets initialized by the activity.
     */
    public int[] getInvolved() {
        return involvedIds;
    }

    public void setInvolved(int[] ids) {
        involvedIds = ids;
    }

    public boolean isSettlement() {
        return isSettlement;
    }

    public Amount getAmount() {
        return amount;
    }

    public int getPersonId() {
        return personId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Marks an expense as deleted for soft-deleting expenses.
     */
    public void delete() {
        this.isDeleted = true;
    }

    /**
     * Returns true if both expenses contain the same person ID, amount and description.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Expense) {
            Expense otherExpense = (Expense) other;
            return otherExpense.getPersonId() == getPersonId()
                    && otherExpense.getAmount().equals(getAmount())
                    && otherExpense.getDescription().equals(getDescription());
        } else {
            return false;
        }
    }
}
