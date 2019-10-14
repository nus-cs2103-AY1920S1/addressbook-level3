package seedu.address.model.entitylist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.PrefixType;

/**
 * This interface serves as the new API for the model.
 * {@code MentorList} should behave as a singleton.
 */
public class MentorList extends EntityList {
    private static int lastUsedId = 0;
    private static final String SIMILAR_MENTOR_MSG = "Mentor with the same name and either one of phone"
            + "or email already exists.";


    private final ObservableList<Mentor> mentors = FXCollections.observableArrayList();
    private final ObservableList<Mentor> unmodifiableMentors =
            FXCollections.unmodifiableObservableList(mentors);

    /**
     * Gets Mentor by ID.
     *
     * @param id
     * @return Mentor
     */
    public Mentor get(Id id) throws AlfredException {
        for (Mentor m: this.mentors) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        throw new AlfredModelException("Mentor to get does not exist");
    }

    /**
     * Updates Mentor by ID.
     *
     * @param id
     * @param updatedMentor
     * @throws ModelValidationException if a similar mentor already exists
     * @throws MissingEntityException if the id passed does not match
     */
    public void update(Id id, Mentor updatedMentor)
            throws MissingEntityException, ModelValidationException {
        // First check if the updated mentor already exists
        for (Mentor m: this.mentors) {
            if (m.isSameMentor(updatedMentor) && !m.getId().equals(updatedMentor.getId())) {
                throw new ModelValidationException(SIMILAR_MENTOR_MSG);
            }
        }

        for (int i = 0; i < this.mentors.size(); i++) {
            if (this.mentors.get(i).getId().equals(id)) {
                this.mentors.set(i, updatedMentor);
                return;
            }
        }
        throw new MissingEntityException("Mentor to update does not exist.");
    }

    /**
     * Adds the mentor into the list.
     *
     * @param mentor
     * @throws AlfredException
     */
    public void add(Mentor mentor) throws AlfredException {
        for (Mentor m: this.mentors) {
            if (m.isSameMentor(mentor) || m.getId().equals(mentor.getId())) {
                throw new AlfredModelException("Item to add already exists!");
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
            if (m.getId().equals(id)) {
                this.mentors.remove(m);
                return m;
            }
        }
        throw new AlfredModelException("Mentor to delete does not exist.");
    }

    /**
     * Returns a list but with element type Mentor.
     *
     * @return List of Mentors.
     */
    public ObservableList<Mentor> getSpecificTypedList() {
        return this.mentors;
    }

    /**
     * List the mentors.
     *
     * @return List of Mentors.
     */
    @Override
    public ObservableList<? extends Entity> list() {
        return this.mentors;
    }

    /**
     * Get the unmodifiable list of mentors.
     *
     * @return Observable List of Mentors
     */
    @Override
    public ObservableList<? extends Entity> getUnmodifiableList() {
        return this.unmodifiableMentors;
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
            if (m.getId().equals(id)) {
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
    public static Id generateId() {
        lastUsedId++;
        return new Id(PrefixType.M, lastUsedId);
    }

    /**
     * Sets the lastUsedId class attribute.
     *
     * @param number
     */
    public static void setLastUsedId(int number) {
        lastUsedId = number;
    }
}
