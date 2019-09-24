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

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        if (!Author.isValidAuthor(author)) {
            throw new IllegalValueException(Author.MESSAGE_CONSTRAINTS);
        }
        final Author modelAuthor = new Author(author);

        if (weblink == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, WebLink.class.getSimpleName()));
        }
        if (!WebLink.isValidWeblink(weblink)) {
            throw new IllegalValueException(WebLink.MESSAGE_CONSTRAINTS);
        }
        final WebLink modelWebLink = new WebLink(weblink);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Tag> modelTags = new HashSet<>(problemTags);

        if (difficulty == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Difficulty.class.getSimpleName())
            );
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        if (remark == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Remark.class.getSimpleName())
            );
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);


        if (source == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Source.class.getSimpleName())
            );
        }
        if (!Source.isValidSource(source)) {
            throw new IllegalValueException(Source.MESSAGE_CONSTRAINTS);
        }
        final Source modelSource = new Source(source);

        return new Problem(modelName, modelAuthor, modelWebLink, modelDescription, modelTags, modelDifficulty,
                modelRemark, modelSource);
    }

}
