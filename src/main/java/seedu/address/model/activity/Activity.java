package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Person;
import seedu.address.model.activity.Title;

/**
 * Represents an Activity class containing participants and expenses.
 */
public class Activity {

    private final Title title;
    private final ArrayList<Person> participants = new ArrayList<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();

    /**
     * Constructor for Activity.
     * @param title Title of the activity.
     * @param people The people participating in the activity.
     */
    public Activity(Title title, Person ... people) {
        requireAllNonNull(title);
        this.title = title;
        for (Person person : people) {
            participants.add(person);
        }
    }

    /**
     * Gets the list of participants in the activity.
     * @return An ArrayList containing the participants.
     */
    public ArrayList<Person> getParticipants() {
        return participants;
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
     * Invite people to the activity.
     * @param people The people that will be added into the activity.
     */
    public void invite(Person ... people) {
        for (int i = 0; i < people.length; i++) {
            // TODO: implement check for person with same name, add only if
            // different name
            participants.add(people[i]);
        }
    }

    /**
     * Remove people from the activity
     * @param people The people that will be removed from the activity.
     */
    public void disinvite(Person ... people) {
        for (int i = 0; i < people.length; i++) {
            if (participants.contains(people[i])) {
                participants.remove(people[i]);
            }
            // haven't implemented what if list does not contain that specific person
        }
    }

    /**
     * Add expense to the activity
     * @param expenditures The expense(s) to be added.
     */
    public void addExpense(Expense ... expenditures) {
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


}
