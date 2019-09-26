package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

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
        mentors.addAll(source.list().stream().map(JsonAdaptedMentor::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code MentorList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MentorList toModelType() throws IllegalValueException {
        MentorList mentorList = new MentorList();
        for (JsonAdaptedMentor jsonAdaptedMentor : mentors) {
            Mentor mentor = jsonAdaptedMentor.toModelType();
            if (mentorList.hasMentor(mentor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            }
            mentorList.addMentor(mentor);
        }
        return mentorList;
    }

}
