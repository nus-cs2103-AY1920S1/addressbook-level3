package mams.testutil;

import mams.model.Mams;
import mams.model.person.Person;

/**
 * A utility class to help with building MAMS objects.
 * Example usage: <br>
 *     {@code Mams ab = new MamsBuilder().withPerson("John", "Doe").build();}
 */
public class MamsBuilder {

    private Mams mams;

    public MamsBuilder() {
        mams = new Mams();
    }

    public MamsBuilder(Mams mams) {
        this.mams = mams;
    }

    /**
     * Adds a new {@code Person} to the {@code Mams} that we are building.
     */
    public MamsBuilder withPerson(Person person) {
        mams.addPerson(person);
        return this;
    }

    public Mams build() {
        return mams;
    }
}
