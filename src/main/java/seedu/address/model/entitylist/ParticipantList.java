package seedu.address.model.entitylist;

import java.util.ArrayList;
import java.util.List;

import seedu.address.AlfredException;
import seedu.address.AlfredRuntimeException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;

/**
 * This interface serves as the new API for the model.
 * {@code ParticipantList} should behave as a singleton.
 */
public class ParticipantList extends EntityList {
    private List<Participant> participants;
    private int lastUsedId;

    /**
     * Constructor.
     */
    public ParticipantList() {
        this.participants = new ArrayList<>();
        this.lastUsedId = 0;
    }

    /**
     * Gets participant by id.
     *
     * @param id
     * @return Participant
     * @throws AlfredException if the participant to get does not exist.
     */
    public Participant get(Id id) throws AlfredException {
        for (Participant p: this.participants) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new AlfredRuntimeException("Participant to get does not exist");
    }

    /**
     * Updates participant by id.
     *
     * @param id
     * @param updatedParticipant
     * @return boolean
     */
    public boolean update(Id id, Participant updatedParticipant) {
        for (int i = 0; i < this.participants.size(); i++) {
            if (this.participants.get(i).getId() == id) {
                this.participants.set(i, updatedParticipant);
                return true;
            }
        }
        // Participant to update does not exist
        return false;
    }

    /**
     * Adds participant to the list.
     *
     * @param participant
     * @throws AlfredException if there was an error while adding.
     */
    public void add(Participant participant) throws AlfredException {
        for (Participant p: this.participants) {
            if (p.getId() == participant.getId()) {
                throw new AlfredRuntimeException("Participant already exists in list");
            }
        }
        this.participants.add(participant);
    }

    /**
     * Deletes participant by ID.
     *
     * @param id
     * @throws AlfredException if error while deleting.
     */
    public Participant delete(Id id) throws AlfredException {
        for (Participant p: this.participants) {
            if (p.getId() == id) {
                this.participants.remove(p);
                return p;
            }
        }
        throw new AlfredRuntimeException("Participant to delete does not exist");
    }

    /**
     * Gets the list but with element type Participant.
     *
     * @return List of Participants.
     */
    public List<Participant> getSpecificTypedList() {
        return this.participants;
    }

    /**
     * List the participants.
     *
     * @return List of Participants.
     */
    @Override
    public List<? extends Entity> list() {
        return this.participants;
    }

    /**
     * Checks if a given ID exists.
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean contains(Id id) {
        for (Participant p: this.participants) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates the ID for the object.
     *
     * @return ID
     */
    @Override
    public Id generateId() {
        this.lastUsedId++;
        return new Id(PrefixType.P, this.lastUsedId);
    }
}
