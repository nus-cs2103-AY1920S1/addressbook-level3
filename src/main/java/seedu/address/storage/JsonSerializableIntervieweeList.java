package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.IntervieweeList;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.person.Interviewee;

/**
 * An Immutable IntervieweeList that is serializable to JSON format.
 */
@JsonRootName(value = "interviewees")
public class JsonSerializableIntervieweeList {

    public static final String MESSAGE_DUPLICATE_INTERVIEWEE = "Interviewee list contains duplicate interviewee(s).";

    private final List<JsonAdaptedInterviewee> interviewees = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableIntervieweeList} with the given Interviewees.
     */
    @JsonCreator
    public JsonSerializableIntervieweeList(@JsonProperty("interviewees") List<JsonAdaptedInterviewee> interviewees) {
        this.interviewees.addAll(interviewees);
    }

    /**
     * Converts a given {@code ReadOnlyList<Interviewee>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableIntervieweeList}.
     */
    public JsonSerializableIntervieweeList(ReadOnlyList<Interviewee> source) {
        this.interviewees.addAll(source.getEntityList().stream()
                .map(JsonAdaptedInterviewee::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Interviewee list into the model's {@code IntervieweeList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public IntervieweeList toModelType() throws IllegalValueException {
        IntervieweeList intervieweeList = new IntervieweeList();

        for (JsonAdaptedInterviewee jsonAdaptedInterviewee : this.interviewees) {
            Interviewee interviewee = jsonAdaptedInterviewee.toModelType();

            if (intervieweeList.hasEntity(interviewee)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INTERVIEWEE);
            }

            intervieweeList.addEntity(interviewee);
        }

        return intervieweeList;
    }

}
