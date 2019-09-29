package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Participant;
import seedu.address.model.entitylist.ParticipantList;


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
    public JsonSerializableParticipantList(@JsonProperty("participants") List<JsonAdaptedParticipant> participants) {
        this.participants.addAll(participants);
    }

    /**
     * Converts a given {@code ParticipantList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableParticipantList}.
     */
    public JsonSerializableParticipantList(ParticipantList source) {
        // participants.addAll(source.list().stream().map(JsonAdaptedParticipant::new).collect(Collectors.toList()));
        participants.addAll(source.list()
                             .stream()
                             .map((Entity p) -> new JsonAdaptedParticipant((Participant) p))
                             .collect(Collectors.toList()));
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
            //TODO: Check whether this checking of existing participants is necessary with the team
            //if (participantList.hasParticipant(participant)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            //}
            participantList.add(participant);
        }
        return participantList;
    }

}
