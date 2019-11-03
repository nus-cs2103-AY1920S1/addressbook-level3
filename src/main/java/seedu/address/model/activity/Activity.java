package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import seedu.address.model.activity.exceptions.PersonNotInActivityException;
import seedu.address.model.person.Person;

/**
 * Represents an Activity class containing participants ID and expenses.
 */
public class Activity {

    private static int primaryKeyCounter;
    private final int primaryKey;
    private final Title title;
    private final ArrayList<Expense> expenses;

    // Id, Balance, Active arrays are all supposed to be one-to-one.
    private final ArrayList<Integer> participantIds;
    private final ArrayList<Boolean> participantActive;
    private final ArrayList<Double> participantBalances;
    // A dictionary mapping id to position in participantIds.
    private final HashMap<Integer, Integer> idDict;
    // Each [i][j] entry with value E means i owes j -E amount.
    // The actual personid has to be obtained from the id array, and i, j just
    // represent the indices in that array where you can find them.
    private final ArrayList<ArrayList<Double>> transferMatrix;
    private final ArrayList<ArrayList<Double>> debtMatrix;

    /**
     * Constructor for Activity.
     * @param primaryKey The primary key of this activity.
     * @param title Title of the activity.
     * @param ids The people participating in the activity.
     */
    public Activity(int primaryKey, Title title, Integer ... ids) {
        requireNonNull(title);
        participantIds = new ArrayList<>(ids.length);
        participantActive = new ArrayList<>(ids.length);
        idDict = new HashMap<>(ids.length);
        expenses = new ArrayList<>(ids.length);
        participantBalances = new ArrayList<>(ids.length);
        transferMatrix = new ArrayList<>(ids.length);
        debtMatrix = new ArrayList<>(ids.length);
        this.primaryKey = primaryKey;
        this.title = title;
        invite(ids);
    }

    /**
      Constructor for Activity. Sets primary key automatically.
     * @param title Title of the activity.
     * @param ids The people participating in the activity.
     */
    public Activity(Title title, Integer ... ids) {
        this(primaryKeyCounter++, title, ids);
    }

    /**
     * Returns a new Activity instance with fields modified from the supplied activity.
     * @param activity Activity instance to duplicate.
     * @param title Title of the updated activity.
     * @return a new Activity instance with the editable fields updated.
     */
    public Activity(Activity activity, Title title) {
        participantIds = activity.participantIds;
        participantActive = activity.participantActive;
        idDict = activity.idDict;
        expenses = activity.expenses;
        participantBalances = activity.participantBalances;
        transferMatrix = activity.transferMatrix;
        debtMatrix = activity.debtMatrix;
        primaryKey = activity.primaryKey;
        this.title = title;
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
     * Returns a List containing all the IDs of the participants.
     * @return A {@code List} containing the IDs of all participants.
     */
    public List<Integer> getParticipantsIds() {
        return participantIds;
    }

    /**
     * Gets the transfer matrix.
     * @return The matrix. Every (i, j) entry reflects how much i receives from
     * j. Negative amounts means i has to give j money.
     *
     */
    public ArrayList<ArrayList<Double>> getTransferMatrix() {
        simplifyExpenses();
        return transferMatrix;
    }

    /**
     * Returns the aggregate amount owed to a specified participant in this activity. A
     * negative amount indicates this participant owes other participants.
     * @param participantId {@code Integer} ID of the participant.
     */
    public Double getTransferAmount(Integer participantId) {
        requireNonNull(participantId);

        Integer participantIndex = idDict.get(participantId);
        assert participantIndex != null;

        simplifyExpenses();

        ArrayList<Double> transfers = transferMatrix.get(participantIndex);
        return transfers.stream().reduce(0.0, (acc, amt) -> acc + amt);
    }

    /**
     * Invite people to the activity.
     * @param people The people that will be added into the activity.
     */
    public void invite(Person ... people) {
        invite(Stream.of(people)
                .map(x -> x.getPrimaryKey())
                .toArray(Integer[]::new));
    }

    /**
     * Invite people to the activity.
     * @param primaryKeys The primary keys of the people that will be added
     * into the activity.
     */
    public void invite(Integer ... primaryKeys) {
        int len = participantIds.size();
        int newlen = len + primaryKeys.length;
        for (int i = 0; i < primaryKeys.length; i++) {
            int p = primaryKeys[i];
            if (hasPerson(p)) {
                continue;
            }

            participantIds.add(p);
            idDict.put(p, participantIds.size() - 1);
            participantBalances.add(0.0); // newcomers don't owe.
            participantActive.add(false);
            for (int j = 0; j < len; j++) {
                debtMatrix.get(j).add(0.0); // extend columns
                transferMatrix.get(j).add(0.0); // extend columns
            }
            debtMatrix.add(new ArrayList<>(Collections.nCopies(newlen, 0.0)));
            transferMatrix.add(new ArrayList<>(Collections.nCopies(newlen, 0.0)));
        }
    }

    /**
     * Checks whether the person with ID is present in this activity.
     * @param personId Id of the person to check.
     * @return True if person exists, false otherwise.
     */
    public boolean hasPerson(Integer personId) {
        return idDict.containsKey(personId);
    }

    /**
     * Remove people from the activity
     * @param people The people that will be removed from the activity.
     */
    public void disinvite(Person ... people) {
        disinvite(Stream.of(people)
                .map(x -> x.getPrimaryKey())
                .toArray(Integer[]::new));
    }

    /**
     * Remove people from the activity. Does nothing if he is involved in any expenses.
     * @param primaryKeys The primary keys of the people you want to remove.
     */
    public void disinvite(Integer ... primaryKeys) {
        ArrayList<Integer> gc = new ArrayList<>(primaryKeys.length);
        for (int i = 0; i < primaryKeys.length; i++) {
            int p = primaryKeys[i];

            if (idDict.get(p) == null) {
                continue;
            }

            int pos = idDict.get(p);
            if (participantActive.get(pos)
                    || !hasPerson(p)) {
                continue;
            }

            gc.add(pos);
        }

        Collections.sort(gc);
        for (int i = 0; i < gc.size(); i++) {
            int pos = gc.get(i) - i;
            participantIds.remove(pos);
            participantBalances.remove(pos);
            participantActive.remove(pos);
            debtMatrix.remove(pos);
            transferMatrix.remove(pos);
            for (int j = 0; j < participantIds.size(); j++) {
                debtMatrix.get(j).remove(pos); // extend columns
                transferMatrix.get(j).remove(pos); // extend columns
            }
        }

        idDict.clear();
        for (int i = 0; i < participantIds.size(); i++) {
            idDict.put(participantIds.get(i), i);
        }
    }

    /**
     * Convenient function to allow adding many expenses at one go.
     * @param expenditures The Expenses you wish to add to this activity.
     */
    public void addExpense(Expense ... expenditures) throws PersonNotInActivityException {
        Stream.of(expenditures)
            .forEach(e -> addExpense(e));
    }

    /**
     * Add expense to the activity
     * @param expense The expense to be added.
     * @throws PersonNotInActivityException if any person is not found
     */
    public void addExpense(Expense expense) throws PersonNotInActivityException {
        int payer = expense.getPersonId();
        int payerPos = idDict.get(payer);
        int[] involved = expense.getInvolved(); // id of everyone involved
        int[] positionMask; // position of everyone involved
        double amount = expense.getAmount().value;

        if (!hasPerson(expense.getPersonId())) {
            throw new PersonNotInActivityException();
        }

        if (involved != null) {
            for (int i : involved) {
                if (!hasPerson(i)) {
                    throw new PersonNotInActivityException();
                }
            }
        } else {
            involved = participantIds.stream()
                    .mapToInt(x -> x)
                    .filter(x -> x != payer)
                    .toArray();
            expense.setInvolved(involved);
        }

        positionMask = IntStream.of(involved)
            .map(x -> idDict.get(x))
            .toArray();

        expenses.add(expense);

        // We update the balance sheet
        double splitAmount = amount / (involved.length + 1);

        // all this does is to just add splitAmount to the (x, payerpos) entry.
        // This signifies "x owes payerpos" $splitAmount more.
        IntStream.of(positionMask)
                .forEach(x -> debtMatrix
                        .get(x).set(payerPos,
                                debtMatrix.get(x).get(payerPos) + splitAmount));
        IntStream.of(involved)
            .forEach(x -> participantActive.set(idDict.get(x), true));
        participantActive.set(payerPos, true);
    }

    /**
     * Simplifies the expenses in the balance sheet and also updates transferMatrix.
     * See: https://pure.tue.nl/ws/portalfiles/portal/2062204/623903.pdf
     */
    private void simplifyExpenses() {
        int i = 0;
        int j = 0;
        int n = participantBalances.size();

        // negative balance means you lent more than you borrowed.
        for (int a = 0; a < debtMatrix.size(); a++) {
            double acc = 0;
            for (int b = 0; b < debtMatrix.size(); b++) {
                acc += debtMatrix.get(a).get(b);
                acc -= debtMatrix.get(b).get(a);
                transferMatrix.get(a).set(b, 0.0);
            }
            participantBalances.set(a, acc);
        }

        while (i != n && j != n) {
            double bi;
            double bj;
            if ((bi = participantBalances.get(i)) <= 0) {
                i++;
                continue;
            } else if ((bj = participantBalances.get(j)) >= 0) {
                j++;
                continue;
            }

            double m = bi < -bj ? bi : -bj;
            // i gives j $m.
            transferMatrix.get(i).set(j, transferMatrix.get(i).get(j) - m);
            transferMatrix.get(j).set(i, transferMatrix.get(j).get(i) + m);
            participantBalances.set(i, bi - m);
            participantBalances.set(j, bj + m);
        }
        System.out.println("Transfer matrix:");
        for (ArrayList<Double> a : transferMatrix) {
            System.out.println(a.toString());
        }
    }

    /**
     * Soft deletes an expense within this activity.
     * @param positions The 0-indexed expense number to delete
     */
    public void deleteExpense(int ... positions) {
        for (int i = 0; i < positions.length; i++) {
            Expense expense;
            if (positions[i] < 0 && positions[i] > expenses.size()) {
                return;
                // TODO: beyond range?
            }

            expense = expenses.get(positions[i] - 1);
            expense.delete();
            deleteExpense(expense);
        }
    }

    /**
     * Soft deletes an expense within this activity.
     * @param expense The expense to delete. If expense is not in this activity,
     * no errors will be thrown. Suggest to use the varags version instead if you
     * want bounds checking.
     */
    public void deleteExpense(Expense expense) throws PersonNotInActivityException {
        int payer = expense.getPersonId();
        int payerPos = participantIds.indexOf(payer);
        int[] involved = expense.getInvolved();
        int[] positionMask;
        double amount = expense.getAmount().value;

        if (!hasPerson(expense.getPersonId())) {
            throw new PersonNotInActivityException();
        }
        assert involved != null : "Involved should have been initialized by addExpense";

        positionMask = IntStream.of(involved)
            .map(x -> participantIds.indexOf(x))
            .toArray();
        if (!IntStream.of(positionMask).allMatch(x -> x >= 0)) {
            throw new PersonNotInActivityException();
        }

        // We update the balance sheet
        double splitAmount = amount / (involved.length + 1);

        // Revert the change made by addExpense
        IntStream.of(positionMask)
                .forEach(x -> debtMatrix
                        .get(x).set(payerPos,
                                debtMatrix.get(x).get(payerPos) - splitAmount));
    }

    @Override
    public String toString() {
        return String.format("Activity \"%s\"", title);
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
