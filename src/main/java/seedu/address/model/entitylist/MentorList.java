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
    public static final String SIMILAR_MENTOR_MSG = "A similar Mentor already exists.";

    private static int lastUsedId = 0;


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
        for (Mentor m : this.mentors) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        throw new AlfredModelException("Mentor to get does not exist");
    }

    /**
     * Returns the size of ObservableList of Mentors.
     * Used to set the lastUsedId during the intialization of model in ModelManager#intialize.
     * During the intialization, MentorList is set the the MentorList gotten from storage.
     *
     * @return size Number of Mentors in MentorList
     */
    public int getSize() {
        return this.mentors.size();
    }

    /**
     * Updates Mentor by ID.
     *
     * @param id
     * @param updatedMentor
     * @throws ModelValidationException if a similar mentor already exists
     * @throws MissingEntityException   if the id passed does not match
     */
    public void update(Id id, Mentor updatedMentor)
            throws MissingEntityException, ModelValidationException {
        // First check if the updated mentor already exists
        for (Mentor m : this.mentors) {
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
     * @throws AlfredModelException
     */
    public void add(Mentor mentor) throws AlfredModelException {
        for (Mentor m : this.mentors) {
            if (m.isSameMentor(mentor) || m.getId().equals(mentor.getId())) {
                throw new AlfredModelException("Item to add already exists!");
            }
        }
        this.mentors.add(mentor);
        if (mentor.getId().getNumber() > lastUsedId) {
            lastUsedId = mentor.getId().getNumber();
        }
    }

    /**
     * Deletes Mentor by id.
     *
     * @param id
     * @throws Exception
     */
    public Mentor delete(Id id) throws AlfredException {
        for (Mentor m : this.mentors) {
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
        return this.unmodifiableMentors;
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
        for (Mentor m : this.mentors) {
            if (m.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if given {@code Entity} exists.
     */
    @Override
    public boolean contains(Entity entity) {
        if (!(entity instanceof Mentor)) {
            return false;
        }
        Mentor mentor = (Mentor) entity;
        for (Mentor m : this.mentors) {
            if (m.isSameMentor(mentor) || m.getId().equals(mentor.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this {@code MentorList} is empty.
     */
    @Override
    public boolean isEmpty() {
        return this.mentors.isEmpty();
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
     * Gets the lastUsedId class attribute.
     *
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
     * Provides a deep copy of the MentorList
     *
     * @return Deep copy of MentorList
     */
    public MentorList copy() throws AlfredModelException {
        MentorList newMList = new MentorList();
        for (Mentor m : this.mentors) {
            newMList.add(m.copy());
        }
        return newMList;
    }

    @Override
    public PrefixType getPrefix() {
        return PrefixType.M;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MentorList)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        MentorList mentorList = (MentorList) other;
        // Anyone feel free to optimize
        return this.getSpecificTypedList().stream().allMatch(mentorList::contains)
                && mentorList.getSpecificTypedList().stream().allMatch(this::contains);
    }
}
