package budgetbuddy.model.loan;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Objects;

import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Represents money owed between a debtor and several creditors when splitting a payment.
 */
public class Debtor {

    private final Person debtor;
    private final HashMap<Person, Amount> creditors = new HashMap<Person, Amount>();

    /**
     * Every field must be present and non-null.
     */
    public Debtor(Person debtor, HashMap<Person, Amount> creditors) {
        requireAllNonNull(debtor, creditors);
        this.debtor = debtor;
        this.creditors.putAll(creditors);
    }

    public Person getDebtor() {
        return debtor;
    }

    public HashMap<Person, Amount> getCreditors() {
        return creditors;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Debtor)) {
            return false;
        }

        Debtor otherDebtor = (Debtor) other;
        return debtor.equals(otherDebtor.debtor)
                && creditors.equals(otherDebtor.creditors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtor, creditors);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(debtor.getName()).append("\n");
        creditors.forEach((key, value) -> builder.append("owes ").append(key.getName()).append(" ").append(value));
        return builder.toString();
    }
}
