package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.InterviewerList;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.person.Interviewer;

/**
 * An Immutable InterviewerList that is serializable to JSON format.
 */
@JsonRootName(value = "interviewers")
public class JsonSerializableInterviewerList {

    public static final String MESSAGE_DUPLICATE_INTERVIEWER = "Interviewer list contains duplicate interviewer(s).";

    private final List<JsonAdaptedInterviewer> interviewers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInterviewerList} with the given Interviewers.
     */
    @JsonCreator
    public JsonSerializableInterviewerList(@JsonProperty("interviewers") List<JsonAdaptedInterviewer> interviewers) {
        this.interviewers.addAll(interviewers);
    }

    /**
     * Converts a given {@code ReadOnlyList<Interviewer>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInterviewerList}.
     */
    public JsonSerializableInterviewerList(ReadOnlyList<Interviewer> source) {
        this.interviewers.addAll(source.getEntityList().stream()
                .map(JsonAdaptedInterviewer::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Interviewer list into the model's {@code InterviewerList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public InterviewerList toModelType() throws IllegalValueException {
        InterviewerList interviewerList = new InterviewerList();

        for (JsonAdaptedInterviewer jsonAdaptedInterviewer : this.interviewers) {
            Interviewer interviewer = jsonAdaptedInterviewer.toModelType();

            if (interviewerList.hasEntity(interviewer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INTERVIEWER);
            }

            interviewerList.addEntity(interviewer);
        }

        return interviewerList;
    }

}
