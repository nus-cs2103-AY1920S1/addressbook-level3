package budgetbuddy.model.transaction;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;

/**
 * Represents a Transaction in a TransactionList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {

    private Date date;
    private Amount amount;
    private Direction direction;
    private Account account;
    private Description description;
    private Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Transaction(Date date, Amount amount, Direction direction, Description description,
                       Account account, Category... categories) {
        requireAllNonNull(date, amount, direction);
        this.date = date;
        this.amount = amount;
        this.direction = direction;
        this.account = account;
        this.description = description;
        this.categories.addAll(Arrays.asList(categories));
    }

    public Date getDate() {
        return date;
    }

    public Amount getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account toSet) {
        if (this.account != null) {
            // we have to remove the association for the old account as well
            this.account.deleteTransaction(this);
        }
        this.account = toSet;
    }

    public Direction getDirection() {
        return direction;
    }

    public Description getDescription() {
        return description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * Returns true if both Transactions have all the same fields (date, amount, description, categories).
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getDate().equals(getDate())
                && otherTransaction.amount.equals(amount)
                && otherTransaction.direction.equals(direction)
                && otherTransaction.account.equals(account)
                && otherTransaction.description.equals(description)
                && otherTransaction.categories.equals(categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, direction, account, description, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Amount: ")
                .append(getAmount())
                .append(direction.toString())
                .append(" on ")
                .append(getDate())
                .append(" in account: ")
                .append(getAccount())
                .append(" Description: ")
                .append(getDescription())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
