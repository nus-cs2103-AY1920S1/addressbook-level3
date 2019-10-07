package seedu.algobase.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Problem}.
 */
class JsonAdaptedProblem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Problem's %s field is missing!";

    private final String name;
    private final String author;
    private final String weblink;
    private final String description;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String difficulty;
    private final String remark;
    private final String source;

    /**
     * Constructs a {@code JsonAdaptedProblem} with the given Problem details.
     */
    @JsonCreator
    public JsonAdaptedProblem(@JsonProperty("name") String name, @JsonProperty("author") String author,
                              @JsonProperty("weblink") String weblink, @JsonProperty("description") String description,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                              @JsonProperty("difficulty") String difficulty,
                              @JsonProperty("remark") String remark, @JsonProperty("source") String source) {
        this.name = name;
        this.author = author;
        this.weblink = weblink;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.difficulty = difficulty;
        this.remark = remark;
        this.source = source;
    }

    /**
     * Converts a given {@code Problem} into this class for Jackson use.
     */
    public JsonAdaptedProblem(Problem problem) {
        name = problem.getName().fullName;
        author = problem.getAuthor().value;
        weblink = problem.getWebLink().value;
        description = problem.getDescription().value;
        tagged.addAll(problem.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        difficulty = Double.toString(problem.getDifficulty().value);
        remark = problem.getRemark().value;
        source = problem.getSource().value;
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's {@code Problem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Problem.
     */
    public Problem toModelType() throws IllegalValueException {
        final List<Tag> problemTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            problemTags.add(tag.toModelType());
        }

        final Name modelName = retrieveName(name);
        final Author modelAuthor = retrieveAuthor(author);
        final WebLink modelWebLink = retrieveWeblink(weblink);
        final Description modelDescription = retrieveDescription(description);
        final Difficulty modelDifficulty = retrieveDifficulty(difficulty);
        final Remark modelRemark = retrieveRemark(remark);
        final Source modelSource = retrieveSource(source);
        final Set<Tag> modelTags = new HashSet<>(problemTags);

        return new Problem(modelName, modelAuthor, modelWebLink, modelDescription, modelTags, modelDifficulty,
                modelRemark, modelSource);
    }

    /**
     * Converts a name in string format to a PlanName Object.
     *
     * @param name name in string format.
     * @return the corresponding PlanName Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Name retrieveName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Converts an author in string format to an Author Object.
     *
     * @param author author in string format.
     * @return the corresponding Author Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Author retrieveAuthor(String author) throws IllegalValueException {
        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }

        if (Author.isDefaultAuthor(author)) {
            return Author.DEFAULT_AUTHOR;
        }

        if (!Author.isValidAuthor(author)) {
            throw new IllegalValueException(Author.MESSAGE_CONSTRAINTS);
        }

        return new Author(author);
    }

    /**
     * Converts a weblink in string format to a Weblink Object.
     *
     * @param weblink weblink in string format.
     * @return the corresponding Weblink Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public WebLink retrieveWeblink(String weblink) throws IllegalValueException {
        if (weblink == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, WebLink.class.getSimpleName()));
        }

        if (WebLink.isDefaultWeblink(weblink)) {
            return WebLink.DEFAULT_WEBLINK;
        }

        if (!WebLink.isValidWeblink(weblink)) {
            throw new IllegalValueException(WebLink.MESSAGE_CONSTRAINTS);
        }

        return new WebLink(weblink);
    }

    /**
     * Converts a description in string format to a PlanDescription Object.
     *
     * @param description description in string format.
     * @return the corresponding PlanDescription Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Description retrieveDescription(String description) throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        if (Description.isDefaultDescription(description)) {
            return Description.DEFAULT_DESCRIPTION;
        }

        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        return new Description(description);
    }

    /**
     * Converts a difficulty in string format to a Difficulty Object.
     *
     * @param difficulty difficulty in string format.
     * @return the corresponding Difficulty Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Difficulty retrieveDifficulty(String difficulty) throws IllegalValueException {
        if (difficulty == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Difficulty.class.getSimpleName())
            );
        }

        if (Difficulty.isDefaultDifficulty(difficulty)) {
            return Difficulty.DEFAULT_DIFFICULTY;
        }

        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }

        return new Difficulty(difficulty);
    }

    /**
     * Converts a remark in string format to a Remark Object.
     *
     * @param remark remark in string format.
     * @return the corresponding Remark Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Remark retrieveRemark(String remark) throws IllegalValueException {
        if (remark == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Remark.class.getSimpleName())
            );
        }

        if (Remark.isDefaultRemark(remark)) {
            return Remark.DEFAULT_REMARK;
        }

        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }

        return new Remark(remark);
    }

    /**
     * Converts a source in string format to a Source Object.
     *
     * @param source source in string format.
     * @return the corresponding Source Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Source retrieveSource(String source) throws IllegalValueException {
        if (source == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Source.class.getSimpleName())
            );
        }

        if (Source.isDefaultSource(source)) {
            return Source.DEFAULT_SOURCE;
        }

        if (!Source.isValidSource(source)) {
            throw new IllegalValueException(Source.MESSAGE_CONSTRAINTS);
        }

        return new Source(source);
    }
}
