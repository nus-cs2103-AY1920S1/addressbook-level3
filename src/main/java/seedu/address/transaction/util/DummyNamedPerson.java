package seedu.address.transaction.util;

import java.util.HashSet;
import java.util.Set;
import seedu.address.person.model.person.Address;
import seedu.address.person.model.person.Email;
import seedu.address.person.model.person.Name;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.Phone;
import seedu.address.person.model.tag.Tag;

public class DummyNamedPerson {
    Person dummy;

    public DummyNamedPerson(String name) {
        Name n = new Name(name);
        Phone p = new Phone("12345678");
        Email e = new Email("dummy@example.com");
        Address a = new Address("blk 00 dummy st");
        Set<Tag> set = new HashSet<>();
        this.dummy = new Person(n, p, e, a, set);

    }

    public Person getDummy() {
        return this.dummy;
    }
}
