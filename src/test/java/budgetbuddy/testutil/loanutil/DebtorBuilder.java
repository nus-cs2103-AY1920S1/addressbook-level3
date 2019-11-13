package budgetbuddy.testutil.loanutil;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.person.Person;

/**
 * A utility class to help with building Debtor objects.
 */
public class DebtorBuilder {

    public static final String DEFAULT_DEBTOR = "John";

    private Person debtor;
    private HashMap<Person, Amount> creditors;

    public DebtorBuilder() {
        this.debtor = new Person(new Name(DEFAULT_DEBTOR));
        this.creditors = new HashMap<Person, Amount>();
    }

    public DebtorBuilder(Debtor toCopy) {
        this.debtor = toCopy.getDebtor();
        this.creditors = new HashMap<Person, Amount>(toCopy.getCreditors());
    }

    /**
     * Sets the {@code debtor} of the {@code Debtor} that we are building.
     */
    public DebtorBuilder withDebtor(String person) {
        this.debtor = new Person(new Name(person));
        return this;
    }

    /**
     * Sets the {@code creditors} of the {@code Debtor} that we are building.
     */
    public DebtorBuilder withCreditors(List<String> creditorsPersons, List<Long> creditorsAmounts) {
        assert creditorsPersons.size() == creditorsAmounts.size();

        IntStream.range(0, creditorsPersons.size()).forEach(i -> {
            this.creditors.put(
                    new Person(new Name(creditorsPersons.get(i))),
                    new Amount(creditorsAmounts.get(i)));
        });

        return this;
    }

    public Debtor build() {
        return new Debtor(debtor, creditors);
    }
}
