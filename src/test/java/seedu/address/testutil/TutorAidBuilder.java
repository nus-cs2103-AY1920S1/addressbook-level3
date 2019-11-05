package seedu.address.testutil;

import seedu.address.model.TutorAid;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TutorAid ab = new TutorAidBuilder().withPerson("John", "Doe").build();}
 */
public class TutorAidBuilder {

    private TutorAid tutorAid;

    public TutorAidBuilder() {
        tutorAid = new TutorAid();
    }

    public TutorAidBuilder(TutorAid tutorAid) {
        this.tutorAid = tutorAid;
    }

    /**
     * Adds a new {@code Person} to the {@code TutorAid} that we are building.
     */
    public TutorAidBuilder withPerson(Person person) {
        tutorAid.addPerson(person);
        return this;
    }

    public TutorAid build() {
        return tutorAid;
    }
}
