package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * An Immutable ParticipantList that is serializable to JSON format.
 */
@JsonRootName(value = "participantlist")
class JsonSerializableParticipantList {

    public static final String MESSAGE_DUPLICATE_ENTITY = "Participant list contains duplicate participant(s).";

    private final List<JsonAdaptedParticipant> participants = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableParticipantList} with the given persons.
     */
    @JsonCreator
    public JsonSerializableParticipantList(@JsonProperty("participants") List<JsonAdaptedParticipant> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ParticipantList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableParticipantList}.
     */
    public JsonSerializableParticipantList(ParticipantList source) {
        persons.addAll(source.list().stream().map(JsonAdaptedParticipant::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code ParticipantList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ParticipantList toModelType() throws IllegalValueException {
        ParticipantList participantList = new ParticipantList();
        for (JsonAdaptedParticipant jsonAdaptedParticipant : participants) {
            Participant participant = jsonAdaptedParticipant.toModelType();
            if (participantList.hasParticipant(participant)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            }
            participantList.addParticipant(participant);
        }
        return participantList;
    }

}
