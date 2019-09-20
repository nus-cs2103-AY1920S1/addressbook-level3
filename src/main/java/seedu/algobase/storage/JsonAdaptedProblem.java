package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.Problem.*;
import seedu.algobase.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Constructs a {@code JsonAdaptedProblem} with the given Problem details.
     */
    @JsonCreator
    public JsonAdaptedProblem(@JsonProperty("name") String name, @JsonProperty("author") String author,
            @JsonProperty("weblink") String weblink, @JsonProperty("description") String description,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.author = author;
        this.weblink = weblink;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Problem} into this class for Jackson use.
     */
    public JsonAdaptedProblem(Problem source) {
        name = source.getName().fullName;
        author = source.getAuthor().value;
        weblink = source.getWebLink().value;
        description = source.getDescription().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Tag> modelTags = new HashSet<>(problemTags);
        return new Problem(modelName, modelAuthor, modelWebLink, modelDescription, modelTags);
    }

}
