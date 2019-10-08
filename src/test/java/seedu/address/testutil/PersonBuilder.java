package seedu.address.testutil;

import seedu.address.model.person.Content;
import seedu.address.model.person.Person;
import seedu.address.model.person.Title;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {
    public static final String DEFAULT_TITLE = "A lecture note";
    public static final String DEFAULT_CONTENT = "Lorem ipsum dolor sit amet";

    private Title title;
    private Content content;

    public PersonBuilder() {
        title = new Title(DEFAULT_TITLE);
        content = new Content(DEFAULT_CONTENT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        title = personToCopy.getTitle();
        content = personToCopy.getContent();
    }

    /**
     * Sets the {@code Title} of the {@code Person} that we are building.
     */
    public PersonBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code Person} that we are building.
     */
    public PersonBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    public Person build() {
        return new Person(title, content);
    }
}
