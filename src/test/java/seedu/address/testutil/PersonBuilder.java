package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Difficulty;
import seedu.address.model.person.Question;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_QUESTION = "Alice Pauline";
    public static final String DEFAULT_DIFFICULTY = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Question question;
    private Difficulty difficulty;
    private Address address;
    private Set<Tag> tags;

    public PersonBuilder() {
        question = new Question(DEFAULT_QUESTION);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        question = personToCopy.getQuestion();
        difficulty = personToCopy.getDifficulty();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Question} of the {@code Person} that we are building.
     */
    public PersonBuilder withQuestion(String name) {
        this.question = new Question(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Person} that we are building.
     */
    public PersonBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    public Person build() {
        return new Person(question, difficulty, address, tags);
    }

}
