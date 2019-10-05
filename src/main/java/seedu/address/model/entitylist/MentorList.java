package seedu.address.model.entitylist;

import java.util.ArrayList;
import java.util.List;

import seedu.address.AlfredException;
import seedu.address.AlfredRuntimeException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.PrefixType;

/**
 * This interface serves as the new API for the model.
 * {@code MentorList} should behave as a singleton.
 */
public class MentorList extends EntityList {
    private List<Mentor> mentors;
    private int lastUsedId;

    /**
     * Constructor.
     */
    public MentorList() {
        this.mentors = new ArrayList<>();
        this.lastUsedId = 0;
    }

    /**
     * Gets Mentor by ID.
     *
     * @param id
     * @return Mentor
     */
    public Mentor get(Id id) throws AlfredException {
        for (Mentor m: this.mentors) {
            if (m.getId() == id) {
                return m;
            }
        }
        throw new AlfredRuntimeException("Mentor to get does not exist");
    }

    /**
     * Updates Mentor by ID.
     *
     * @param id
     * @param updatedMentor
     * @return boolean
     */
    public boolean update(Id id, Mentor updatedMentor) {
        for (int i = 0; i < this.mentors.size(); i++) {
            if (this.mentors.get(i).getId() == id) {
                this.mentors.set(i, updatedMentor);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the mentor into the list.
     *
     * @param mentor
     * @throws AlfredException
     */
    public void add(Mentor mentor) throws AlfredException {
        for (Mentor m: this.mentors) {
            if (m.getId() == mentor.getId()) {
                throw new AlfredRuntimeException("Item to add already exists!");
            }
        }
        this.mentors.add(mentor);
    }

    /**
     * Deletes Mentor by id.
     *
     * @param id
     * @throws Exception
     */
    public Mentor delete(Id id) throws AlfredException {
        for (Mentor m: this.mentors) {
            if (m.getId() == id) {
                this.mentors.remove(m);
                return m;
            }
        }
        throw new AlfredRuntimeException("Mentor to delete does not exist.");
    }

    /**
     * Returns a list but with element type Mentor.
     *
     * @return List of Mentors.
     */
    public List<Mentor> getSpecificTypedList() {
        return this.mentors;
    }

    /**
     * List the mentors.
     *
     * @return List of Mentors.
     */
    @Override
    public List<? extends Entity> list() {
        return this.mentors;
    }

    /**
     * Checks if a given ID exists.
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean contains(Id id) {
        for (Mentor m: this.mentors) {
            if (m.getId() == id) {
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
        return new Id(PrefixType.M, this.lastUsedId);
    }
}
