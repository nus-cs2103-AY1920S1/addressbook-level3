package budgetbuddy.model.util;

import budgetbuddy.model.AddressBook;
import budgetbuddy.model.ReadOnlyAddressBook;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.person.Person;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new LoanList()),
            new Person(new Name("Bernice Yu"), new LoanList()),
            new Person(new Name("Charlotte Oliveiro"), new LoanList()),
            new Person(new Name("David Li"), new LoanList()),
            new Person(new Name("Irfan Ibrahim"), new LoanList()),
            new Person(new Name("Roy Balakrishnan"), new LoanList())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
