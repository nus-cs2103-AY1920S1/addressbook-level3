package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.model.activity.exceptions.PersonNotInActivityException;

/**
 * Represents an Activity class containing participants ID and expenses.
 */
public class Activity {
    
    private static int primaryKeyCounter;
    
    private final int primaryKey;
    private final Title title;
    private final ArrayList<Integer> participantIds;
    private final ArrayList<Expense> expenses;

    /**
     * Constructor for Activity.
     * @param primaryKey The primary key of this activity.
     * @param title Title of the activity.
     * @param ids The people participating in the activity.
     */
    public Activity(int primaryKey, Title title, Integer ... ids) {
        requireAllNonNull(title);
        participantIds = new ArrayList<>();
        expenses = new ArrayList<>();
        this.primaryKey = primaryKey;
        this.title = title;
        for (Integer id : ids) {
            participantIds.add(id);
        }
    }
    /**
     * Constructor for Activity.
     * @param title Title of the activity.
     * @param ids The people participating in the activity.
     */
    public Activity(Title title, Integer ... ids) {
        requireAllNonNull(title);
        participantIds = new ArrayList<>();
        expenses = new ArrayList<>();
        this.primaryKey = primaryKeyCounter++;
        this.title = title;
        for (Integer id : ids) {
            participantIds.add(id);
        }
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public static int getPrimaryKeyCounter() {
        return primaryKeyCounter;
    }

    public static void setPrimaryKeyCounter(int pk) {
        primaryKeyCounter = pk;
    }

    /**
     * Gets the list of id of participants in the activity.
     * @return An ArrayList containing the id participants.
     */
    public ArrayList<Integer> getParticipantIds() {
        return participantIds;
    }

    /**
     * Gets the list of expenses in the activity.
     * @return An ArrayList of expenses.
     */
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Gets the name of the activity.
     * @return A String representation of the name of the activity.
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Checks whether the person with ID is present in this activity.
     * @param personId Id of the person to check.
     * @return True if person exists, false otherwise.
     */
    public boolean hasPerson(Integer personId) {
        return participantIds.contains(personId);
    }

    /**
     * Invite a person into the activity.
     * @param personId ID of the person to be invited.
     */
    public void invite(Integer personId) {
        participantIds.add(personId);
    }

    /**
     * Remove people from the activity
     * @param personId The people that will be removed from the activity.
     */
    public void disinvite(Integer personId) {
        participantIds.remove(personId);
    }

    /**
     * Add expense to the activity
     * @param expenditures The expense(s) to be added.
     * @throws PersonNotInActivityException if any person is not found
     */
    public void addExpense(Expense ... expenditures) throws PersonNotInActivityException {
        boolean allPresent = Stream.of(expenditures)
                .map(x -> x.getPersonId())
                .allMatch(x -> participantIds.contains(x));
        if (!allPresent) {
            throw new PersonNotInActivityException();
        }
        for (int i = 0; i < expenditures.length; i++) {
            expenses.add(expenditures[i]);
        }
    }

    /**
     * Soft deletes an expense within an activity
     * @param positions The 0-indexed expense number to delete
     */
    public void deleteExpense(int ... positions) {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] > 0 && positions[i] <= expenses.size()) {
                expenses.get(positions[i] - 1).delete();
            } // if beyond range not implemented yet
        }
    }

    @Override
    public String toString() {
        return String.format("Activity \"%s\"\n", title);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, participantIds, expenses);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return otherActivity.getTitle().equals(getTitle())
                && otherActivity.getParticipantIds().equals(getParticipantIds())
                && otherActivity.getExpenses().equals(getExpenses());
    }
}
