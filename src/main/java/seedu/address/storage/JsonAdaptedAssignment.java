package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
public class JsonAdaptedAssignment {

    private final String assignmentName;

    private final List<String> names = new ArrayList<>();
    private final List<String> marks = new ArrayList<>();
    private boolean isCompleted;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given {@code assignmentName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignmentName") String assignmentName,
                                 @JsonProperty("studentNames") List<String> names,
                                 @JsonProperty("studentMarks") List<String> marks,
                                 @JsonProperty("completionStatus") boolean isCompleted) {
        this.assignmentName = assignmentName;
        if (names != null && marks != null) {
            this.names.addAll(names);
            this.marks.addAll(marks);
        }
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.getAssignmentName().assignmentName;
        names.addAll(source.namesStringListFromGrades());
        marks.addAll(source.marksStringListFromGrades());
        isCompleted = source.isCompleted();
    }


    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Assignment toModelType() throws IllegalValueException {

        if (assignmentName == null) {

            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Assignment.class.getSimpleName()));
        }
        if (!AssignmentName.isValidAssignmentName(assignmentName)) {
            throw new IllegalValueException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        final AssignmentName modelAssignmentName = new AssignmentName(assignmentName);

        Assignment newAssignment = new Assignment(modelAssignmentName);
        newAssignment.setGrades(this.names, this.marks);
        newAssignment.setCompletionStatus(this.isCompleted);
        return newAssignment;
    }

}
