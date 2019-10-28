package seedu.address.model.util;

import static seedu.address.model.util.SampleEvents.LECTURE1;
import static seedu.address.model.util.SampleEvents.LECTURE2;
import static seedu.address.model.util.SampleEvents.LECTURE3;
import static seedu.address.model.util.SampleEvents.LECTURE4;
import static seedu.address.model.util.SampleEvents.LECTURE5;
import static seedu.address.model.util.SampleEvents.LUNCH1;
import static seedu.address.model.util.SampleEvents.LUNCH2;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.User;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.tag.Tag;

/**
 * Sample PersonList.
 */
public class SamplePersonList {

    public static final PersonDescriptor ALICE = new PersonDescriptor(
            new Name("Alice"),
            new Phone("91239123"),
            new Email("alice@gmail.com"),
            new Address("Kent Ridge Drive"),
            Remark.emptyRemark(),
            null
    );

    public static final PersonDescriptor BOBBY = new PersonDescriptor(
            new Name("Bobby"),
            new Phone("81259728"),
            new Email("bobby@gmail.com"),
            new Address("Hougang road"),
            new Remark("Best friend"),
            getTagSet("Friends")
    );

    public static final PersonDescriptor CHARLIE = new PersonDescriptor(
            new Name("Charlie"),
            new Phone("91642323"),
            new Email("charlie@gmail.com"),
            new Address("Sembawang ring road"),
            new Remark("Project mate"),
            getTagSet("Friends")
    );

    public static final PersonDescriptor DENISE = new PersonDescriptor(
            new Name("DENISE"),
            new Phone("88642263"),
            new Email("denise@gmail.com"),
            new Address("Kovan road 77"),
            new Remark("Neighbour"),
            getTagSet("Friends", "Neighbour")
    );

    public static final PersonDescriptor EMILY = new PersonDescriptor(
            new Name("Emily"),
            new Phone("98674512"),
            new Email("emily@gmail.com"),
            new Address("Boon Lay road 69"),
            new Remark("Friend"),
            getTagSet("Friends")
    );

    /**
     * Generates a sample PersonList.
     */
    public static PersonList generateSamplePersonList() {

        try {
            User user = new User(ALICE);
            user.addEvent(LUNCH1);
            user.addEvent(LECTURE1);

            PersonList personList = new PersonList(user);

            Person bobby = new Person(BOBBY);
            bobby.addEvent(LUNCH2);
            bobby.addEvent(LECTURE2);
            personList.addPerson(bobby);

            Person charlie = new Person(CHARLIE);
            charlie.addEvent(LUNCH2);
            charlie.addEvent(LECTURE3);
            personList.addPerson(charlie);

            Person denise = new Person(DENISE);
            denise.addEvent(LUNCH2);
            denise.addEvent(LECTURE4);
            personList.addPerson(denise);

            Person emily = new Person(EMILY);
            emily.addEvent(LECTURE5);
            personList.addPerson(emily);

            return personList;

        } catch (EventClashException e) {
            e.printStackTrace();
            System.out.println("NULL PERSONLIST");
            return null;
        }

    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
