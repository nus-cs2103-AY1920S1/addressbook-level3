package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";

    private Name name;
    private Phone phone;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        tags = new HashSet<>(personToCopy.getTags());
    }

    public PersonBuilder(String name, String phone, String... tags) {
        this.name = new Name(name);
        this.phone = new Phone(phone);
        this.tags = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        try {
            this.name = ParserUtil.parseName(name);
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        try {
            this.tags = ParserUtil.parseTags(Arrays.asList(tags));
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        try {
            this.phone = ParserUtil.parsePhone(phone);
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
