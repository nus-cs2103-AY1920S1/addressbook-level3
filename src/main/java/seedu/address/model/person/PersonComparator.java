package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator that enables us to sort a list of Persons in alphabetical order ignoring case.
 */
public class PersonComparator implements Comparator<Person> {

    /**
     * Compares a person to another using name. Case is ignored.
     * @param p1 Name of person 1
     * @param p2 Name of person 2
     * @return int Value of zero if (x==y), if (x < y) then value less than zero, and if (x > y)
     * then value greater than zero.
     */
    @Override
    public int compare(Person p1, Person p2) {
        String person1Name = p1.getName().toString();
        String person2Name = p2.getName().toString();
        return person1Name.compareToIgnoreCase(person2Name);
    }
}
