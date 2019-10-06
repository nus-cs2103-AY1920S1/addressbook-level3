package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.AlfredException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entitylist.MentorList;

/**
 * An Immutable MentorList that is serializable to JSON format.
 */
@JsonRootName(value = "mentorlist")
class JsonSerializableMentorList {

    public static final String MESSAGE_DUPLICATE_ENTITY = "Mentor list contains duplicate mentor(s).";

    private final List<JsonAdaptedMentor> mentors = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMentorList} with the given mentors.
     */
    @JsonCreator
    public JsonSerializableMentorList(@JsonProperty("mentors") List<JsonAdaptedMentor> mentors) {
        this.mentors.addAll(mentors);
    }

    /**
     * Converts a given {@code MentorList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMentorList}.
     */
    public JsonSerializableMentorList(MentorList source) {
        //mentors.addAll(source.list().stream().map(JsonAdaptedMentor::new).collect(Collectors.toList()));
        mentors.addAll(source.list()
                             .stream()
                             .map((Entity m) -> new JsonAdaptedMentor((Mentor) m))
                             .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code MentorList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MentorList toModelType() throws IllegalValueException, AlfredException {
        MentorList mentorList = new MentorList();
        for (JsonAdaptedMentor jsonAdaptedMentor : mentors) {
            Mentor mentor = jsonAdaptedMentor.toModelType();
            //TODO: Check whether this checking of existing mentors is necessary with the team
            //if (mentorList.hasMentor(mentor)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            //}
            mentorList.add(mentor);
        }
        return mentorList;
    }

}
