package seedu.address.model.entitylist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;

/**
 * This interface serves as the new API for the model.
 * {@code ParticipantList} should behave as a singleton.
 */
public class ParticipantList extends EntityList {
    public static final String SIMILAR_PARTICIPANT_MSG = "A similar Participant already exists.";

    private static int lastUsedId = 0;

    private ObservableList<Participant> participants = FXCollections.observableArrayList();
    private ObservableList<Participant> unmodifiableParticipants =
            FXCollections.unmodifiableObservableList(participants);

    /**
     * Gets participant by id.
     *
     * @param id
     * @return Participant
     * @throws MissingEntityException if the participant to get does not exist.
     */
    public Participant get(Id id) throws MissingEntityException {
        for (Participant p: this.participants) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new MissingEntityException("Participant to get does not exist");
    }

    /**
     * Updates participant by id.
     *
     * @param id
     * @param updatedParticipant
     * @throws MissingEntityException if the participant to update does not exist.
     * @throws ModelValidationException if a similar participant already exists.
     */
    public void update(Id id, Participant updatedParticipant)
            throws MissingEntityException, ModelValidationException {
        // First check if the participant already exists
        for (Participant p: this.participants) {
            if (p.isSameParticipant(updatedParticipant)
                    && !p.getId().equals(updatedParticipant.getId())) {
                throw new ModelValidationException(SIMILAR_PARTICIPANT_MSG);
            }
        }

        for (int i = 0; i < this.participants.size(); i++) {
            if (this.participants.get(i).getId().equals(id)) {
                this.participants.set(i, updatedParticipant);
                return;
            }
        }
        // Participant to update does not exist
        throw new MissingEntityException("Participant to update does not exist.");
    }

    /**
     * Adds participant to the list.
     *
     * @param participant
     * @throws AlfredModelException if there was an error while adding.
     */
    public void add(Participant participant) throws AlfredModelException {
        for (Participant p: this.participants) {
            if (p.isSameParticipant(participant) || p.getId().equals(participant.getId())) {
                throw new AlfredModelException("Participant already exists in list");
            }
        }
        this.participants.add(participant);
    }

    /**
     * Deletes participant by ID.
     *
     * @param id
     * @throws MissingEntityException if entity to delete does not exist.
     */
    public Participant delete(Id id) throws MissingEntityException {
        for (Participant p: this.participants) {
            if (p.getId().equals(id)) {
                this.participants.remove(p);
                return p;
            }
        }
        throw new MissingEntityException("Participant to delete does not exist");
    }

    /**
     * Gets the list but with element type Participant.
     *
     * @return {@code ObservableList<Participant>}
     */
    public ObservableList<Participant> getSpecificTypedList() {
        return this.participants;
    }

    /**
     * List the participants.
     *
     * @return {@code ObservableList<? extends Entity>}
     */
    @Override
    public ObservableList<? extends Entity> list() {
        return this.unmodifiableParticipants;
    }

    /**
     * Lists the unmodifiable Participants.
     *
     * @return {@code ObservableList<? extends Entity>}
     */
    @Override
    public ObservableList<? extends Entity> getUnmodifiableList() {
        return this.unmodifiableParticipants;
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
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this {@code ParticipantList} is empty.
     */
    @Override
    public boolean isEmpty() {
        return this.participants.isEmpty();
    }

    /**
     * Generates the ID for the object.
     *
     * @return ID
     */
    public static Id generateId() {
        lastUsedId++;
        return new Id(PrefixType.P, lastUsedId);
    }

    /**
     * Gets the lastUsedId class attribute.
     * @return lastUsedId
     */
    public static int getLastUsedId() {
        return lastUsedId;
    }

    /**
     * Sets the lastUsedId class attribute.
     *
     * @param number
     */
    public static void setLastUsedId(int number) {
        lastUsedId = number;
    }

    /**
     * Provides a deep copy of the ParticipantList
     * @return Deep copy of ParticipantList
     */
    public ParticipantList copy() throws AlfredModelException {
        ParticipantList newPList = new ParticipantList();
        for (Participant p: this.participants) {
            newPList.add(p.copy());
        }
        return newPList;
    }
}
