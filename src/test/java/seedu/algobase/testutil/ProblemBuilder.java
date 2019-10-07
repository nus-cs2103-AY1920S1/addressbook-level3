package seedu.algobase.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.util.SampleDataUtil;

/**
 * A utility class to help with building Problem objects.
 */
public class ProblemBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_AUTHOR = "85355255";
    public static final String DEFAULT_WEBLINK = "http://open.kattis.com";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DIFFICULTY = "3.0";
    public static final String DEFAULT_REMARK = "remark placeholder";
    public static final String DEFAULT_SOURCE = "UVa";

    private Name name;
    private Author author;
    private WebLink webLink;
    private Description description;
    private Set<Tag> tags;
    private Difficulty difficulty;
    private Remark remark;
    private Source source;

    public ProblemBuilder() {
        name = new Name(DEFAULT_NAME);
        author = new Author(DEFAULT_AUTHOR);
        webLink = new WebLink(DEFAULT_WEBLINK);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        remark = new Remark(DEFAULT_REMARK);
        source = new Source(DEFAULT_SOURCE);
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
        difficulty = problemToCopy.getDifficulty();
        remark = problemToCopy.getRemark();
        source = problemToCopy.getSource();
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
    public ProblemBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /**
     * Sets the {@code WebLink} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withWeblink(String weblink) {
        this.webLink = new WebLink(weblink);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Source} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withSource(String source) {
        this.source = new Source(source);
        return this;
    }

    public Problem build() {
        return new Problem(name, author, webLink, description, tags, difficulty, remark, source);
    }

}
