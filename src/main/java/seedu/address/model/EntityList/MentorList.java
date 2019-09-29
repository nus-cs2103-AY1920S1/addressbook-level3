package seedu.address.model.EntityList;

import java.util.ArrayList;
import java.util.List;
import seedu.address.model.Entity.Entity;
import seedu.address.model.Entity.Id;
import seedu.address.model.Entity.Mentor;
import seedu.address.model.Entity.PrefixType;

public class MentorList extends EntityList {
    private List<Mentor> mentors;

    /**
     * Constructor.
     *
     */
    public MentorList() {
        super();
        this.mentors = new ArrayList<>();
    }

    /**
     * Gets Mentor by ID.
     *
     * @param id
     * @return
     */
    @Override
    public Mentor get(Id id) {
        // TODO
        return null;
    }

    /**
     * Updates Mentor by ID.
     *
     * @param mentor
     * @throws Exception if error while updating
     */
    @Override
    public void update(Entity mentor) throws Exception {
        // TODO
        mentors.add((Mentor) mentor);
    }

    /**
     * Adds the mentor into the list
     *
     * @param mentor
     * @throws Exception
     */
    @Override
    public void add(Entity mentor) throws Exception {
        mentors.add((Mentor) mentor);
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(Id id) throws Exception {
        for (Mentor m: this.mentors) {
            if (m.getId() == id) {
                this.mentors.remove(m);
                return;
            }
        }
    }

    /**
     * List the mentors.
     *
     * @return List<Mentor>
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
    public boolean isContain(Id id) {
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
    public Id generateID() {
        return new Id(PrefixType.M, this.getNewIDSuffix());
    }
}
