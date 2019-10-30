package seedu.system.testutil;

import seedu.system.model.Data;
import seedu.system.model.competition.Competition;
import seedu.system.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Data ab = new DataBuilder().withPerson("John", "Doe").build();}
 */
public class DataBuilder {

    private Data data;

    public DataBuilder() {
        data = new Data();
    }

    public DataBuilder(Data data) {
        this.data = data;
    }

    /**
     * Adds a new {@code Person} to the {@code Data} that we are building.
     */
    public DataBuilder withPerson(Person person) {
        data.addUniqueElement(person);
        return this;
    }

    /**
     * Adds a new {@code Competition} to the {@code Data} that we are building.
     */
    public DataBuilder withCompetition(Competition competition) {
        data.addUniqueElement(competition);
        return this;
    }

    public Data build() {
        return data;
    }
}
