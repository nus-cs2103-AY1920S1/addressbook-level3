package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.model.activity.exceptions.PersonNotInActivityException;
import seedu.address.model.person.Person;

/**
 * Represents an Activity class containing participants ID and expenses.
 */
public class Activity {

    private final Title title;
    // Id, Balance, Transfers arrays are supposed to be one-to-one.
    private final ArrayList<Integer> participantIds;
    private final ArrayList<Double> participantBalances;
    private final ArrayList<Double> transfers;
    private final ArrayList<Expense> expenses;

    /**
     * Constructor for Activity.
     * @param title Title of the activity.
     * @param ids The people participating in the activity.
     */
    public Activity(Title title, Integer ... ids) {
        requireAllNonNull(title);
        participantIds = new ArrayList<>();
        expenses = new ArrayList<>();
        participantBalances = new ArrayList<>();
        transfers = new ArrayList<>();
        this.title = title;
        for (Integer id : ids) {
            participantIds.add(id);
        }
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
     * Invite people to the activity.
     * @param people The people that will be added into the activity.
     */
    public void invite(Person ... people) {
        for (Person p : people) {
            if (!participantIds.contains(p.getPrimaryKey())) {
                participantIds.add(p.getPrimaryKey());
                participantBalances.add(0.0); // newcomers don't owe.
                transfers.add(0.0);
            }
        }
    }

    /**
     * Remove people from the activity
     * @param people The people that will be removed from the activity.
     */
    public void disinvite(Person ... people) {
        // haven't implemented what if list does not contain that specific person
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
        for (Expense expense : expenditures) {
            expenses.add(expense);

            // We update the balance sheet
            int payer = expense.getPersonId();
            double amount = expense.getAmount().getValue();
            double splitAmount = amount / participantIds.size();
            for (int i = 0; i < participantIds.size(); i++) {
                double temp = participantBalances.get(i);
                // negative balance means you lent more than you borrowed.
                if (participantIds.get(i) == payer) {
                    participantBalances.set(i, temp - amount);
                } else {
                    participantBalances.set(i, temp + splitAmount);
                }
            }
        }
        simplifyExpenses();
    }

    private void simplifyExpenses() {
        int i = 0, j = 0, N = participantBalances.size();
        while (i != N && j != N) {
            double I, J;
            if ((I = participantBalances.get(i)) <= 0) {
                i++;
                continue;
            } else if ((J = participantBalances.get(j)) >= 0) {
                j++;
                continue;
            }

            double m = I < -J ? I : -J;
            // i gives j $m.
            System.out.println(i + " " + j + " " + m);
            transfers.set(i, transfers.get(i) + m);
            transfers.set(j, transfers.get(j) - m);
            participantBalances.set(i, I - m);
            participantBalances.set(j, J + m);
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
