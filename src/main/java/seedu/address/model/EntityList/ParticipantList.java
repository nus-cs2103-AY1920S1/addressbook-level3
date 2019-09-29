package seedu.address.model.EntityList;

import java.util.ArrayList;
import java.util.List;
import seedu.address.model.Entity.Email;
import seedu.address.model.Entity.Entity;
import seedu.address.model.Entity.Id;
import seedu.address.model.Entity.Name;
import seedu.address.model.Entity.Participant;
import seedu.address.model.Entity.Phone;
import seedu.address.model.Entity.PrefixType;

public class ParticipantList extends EntityList {
    private List<Participant> participants;

    /**
     * Constructor.
     */
    public ParticipantList() {
       this.participants = new ArrayList<>();
    }

    /**
     * Gets participant by id.
     *
     * @param id
     * @return
     */
    @Override
    public Participant get(Id id) {
        return new Participant(new Name("name"), new Email("email"), new Phone("999"), this.generateID());
    }

    /**
     * Updates participant by id.
     *
     * @param participant
     * @throws Exception if error while updating.
     */
    @Override
    public void update(Entity participant) throws Exception {
        // TODO: Find by ID then replace.
        participants.add((Participant) participant);
    }

    /**
     * Adds participant to the list.
     *
     * @param participant
     * @throws Exception if there was an error while adding.
     */
    @Override
    public void add(Entity participant) throws Exception {
        // TODO: Find by ID then replace.
        participants.add((Participant) participant);
    }

    /**
     * Deletes participant by ID.
     *
     * @param id
     * @throws Exception if error while deleting.
     */
    @Override
    public void delete(Id id) throws Exception {
        for (Participant p: this.participants) {
            if (p.getId() == id) {
                this.participants.remove(p);
                return;
            }
        }
    }

    /**
     * List the participants.
     *
     * @return List<Participant>
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
    public boolean isContain(Id id) {
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
    public Id generateID() {
       return new Id(PrefixType.P, this.getNewIDSuffix());
    }
}
