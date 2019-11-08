package seedu.revision.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answerable;

/**
 * An Immutable RevisionTool that is serializable to JSON format.
 */
@JsonRootName(value = "revisiontool")
class JsonSerializableRevisionTool {

    public static final String MESSAGE_DUPLICATE_ANSWERABLE = "Answerables list contains duplicate answerable(s).";

    private final List<JsonAdaptedAnswerable> answerables = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRevisionTool} with the given answerables.
     */
    @JsonCreator
    public JsonSerializableRevisionTool(@JsonProperty("answerables") List<JsonAdaptedAnswerable> answerables) {
        this.answerables.addAll(answerables);
    }

    /**
     * Converts a given {@code ReadOnlyRevisionTool} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRevisionTool}.
     */
    public JsonSerializableRevisionTool(ReadOnlyRevisionTool source) {
        answerables.addAll(source.getAnswerableList().stream()
                .map(JsonAdaptedAnswerable::new).collect(Collectors.toList()));
    }

    /**
     * Converts this revision tool into the model's {@code RevisionTool} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RevisionTool toModelType() throws IllegalValueException {
        RevisionTool revisionTool = new RevisionTool();
        for (JsonAdaptedAnswerable jsonAdaptedAnswerable : answerables) {
            Answerable answerable = jsonAdaptedAnswerable.toModelType();
            if (revisionTool.hasAnswerable(answerable)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ANSWERABLE);
            }
            revisionTool.addAnswerable(answerable);
        }
        return revisionTool;
    }
}
