package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Rating;
import seedu.address.model.person.Answer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Question;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_QUESTION = "1 + 1";
    public static final String DEFAULT_ANSWER = "2";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_RATING = "good";

    private Question question;
    private Answer answer;
    private Email email;
    private Rating rating;
    private Set<Tag> tags;

    public PersonBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new Answer(DEFAULT_ANSWER);
        email = new Email(DEFAULT_EMAIL);
        rating = new Rating(DEFAULT_RATING);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        question = personToCopy.getQuestion();
        answer = personToCopy.getAnswer();
        email = personToCopy.getEmail();
        rating = personToCopy.getRating();
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
     * Sets the {@code Rating} of the {@code Person} that we are building.
     */
    public PersonBuilder withRating (String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Person} that we are building.
     */
    public PersonBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(question, answer, email, rating, tags);
    }

}
