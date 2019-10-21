package budgetbuddy.testutil;

import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Kurtz";

    private Name name;
    private LoanList loans;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        loans = new LoanList();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        loans = new LoanList();
        loans.replaceList(personToCopy.getLoans());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code LoanList} of the {@code Person} that we are building.
     */
    public PersonBuilder withLoans(LoanList loans) {
        this.loans.replaceList(loans.asUnmodifiableObservableList());
        return this;
    }

    public Person build() {
        return new Person(name, loans);
    }

}
