package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Grade;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
public class JsonAdaptedAssignment {

    private final String assignmentName;
    private final List<JsonAdaptedGrades> grades = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given {@code assignmentName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignmentName") String assignmentName,
                                 @JsonProperty("assignmentGrades") List<JsonAdaptedGrades> grades) {
        this.assignmentName = assignmentName;
        if (grades != null) {
            this.grades.addAll(grades);
        }
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.getAssignmentName().value;
        grades.addAll(source.getGrades().stream()
                .map(JsonAdaptedGrades::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (assignmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Assignment.class.getSimpleName()));
        }
        if (!AssignmentName.isValidAssignmentName(assignmentName)) {
            throw new IllegalValueException(Assignment.MESSAGE_CONSTRAINTS);
        }
        final AssignmentName modelAssignmentName = new AssignmentName(assignmentName);
        return new Assignment(modelAssignmentName);
    }

}