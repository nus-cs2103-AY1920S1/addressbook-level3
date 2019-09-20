package seedu.algobase.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.algobase.model.Problem.*;
import seedu.algobase.model.Problem.WebLink;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.util.SampleDataUtil;

/**
 * A utility class to help with building Problem objects.
 */
public class ProblemBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Author author;
    private WebLink webLink;
    private Description description;
    private Set<Tag> tags;

    public ProblemBuilder() {
        name = new Name(DEFAULT_NAME);
        author = new Author(DEFAULT_PHONE);
        webLink = new WebLink(DEFAULT_EMAIL);
        description = new Description(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProblemBuilder with the data of {@code problemToCopy}.
     */
    public ProblemBuilder(Problem problemToCopy) {
        name = problemToCopy.getName();
        author = problemToCopy.getAuthor();
        webLink = problemToCopy.getWebLink();
        description = problemToCopy.getDescription();
        tags = new HashSet<>(problemToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Problem} that we are building.
     */
    public ProblemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withAddress(String address) {
        this.description = new Description(address);
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withPhone(String phone) {
        this.author = new Author(phone);
        return this;
    }

    /**
     * Sets the {@code WebLink} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withEmail(String email) {
        this.webLink = new WebLink(email);
        return this;
    }

    public Problem build() {
        return new Problem(name, author, webLink, description, tags);
    }

}
