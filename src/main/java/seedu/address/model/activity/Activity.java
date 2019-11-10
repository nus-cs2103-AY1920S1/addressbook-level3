package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import seedu.address.commons.util.Triplet;
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
    // Used for internal computation. Adjacency matrix of our debts. Should not
    // be used outside of the context of the debt algorithm.
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
     * Returns a {@code List} containing all expenses in this {@code Activity}.
     * @return A {@code List} of {@code Expense} instances associated with this {@code Activity}.
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Gets all the expenses in this activity that are not settlements.
     */
    public List<Expense> getNonSettlementExpenses() {
        return expenses.stream()
            .filter(x -> !x.isSettlement())
            .collect(Collectors.toList());
    }

    /**
     * Gets all the expenses in this activity that are settlements.
     */
    public List<Expense> getSettlementExpenses() {
        return expenses.stream()
            .filter(x -> x.isSettlement())
            .collect(Collectors.toList());
    }

    /**
     * Returns the total spending computed from all non-deleted expenses of this {@code Activity}.
     * @return The total spending of this {@code Activity} as a {@code double}.
     */
    public double getTotalSpending() {
        return this.getNonSettlementExpenses().stream()
                .filter(expense -> !expense.isDeleted())
                .map(expense -> expense.getAmount().value)
                .reduce(0.00, (acc, amt) -> acc + amt);
    }

    /**
     * Gets the name of the activity.
     * @return A {@code String} representation of the name of the activity.
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Returns a List containing all the IDs of the participants.
     * @return A {@code List} containing the IDs of all participants.
     */
    public List<Integer> getParticipantIds() {
        return participantIds;
    }

    /**
     * Returns the number of participants of this {@code Activity}.
     * @return The number of participants as an {@code int}.
     */
    public int getParticipantCount() {
        return participantIds.size();
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
     * Gets the amount the first person owes the second.
     */
    public double getOwed(int firstId, int secondId) {
        return getTransferMatrix().get(idDict.get(firstId))
                .get(idDict.get(secondId));
    }

    /**
     * Returns the aggregate amount owed to a specified participant in this
     * activity. A negative amount indicates this participant owes other
     * participants.
     * @param participantId {@code Integer} ID of the participant.
     */
    public Double getTransferAmount(Integer participantId) {
        requireNonNull(participantId);

        Integer participantIndex = idDict.get(participantId);
        assert participantIndex != null : "Participant supplied should be involved in this activity!";

        simplifyExpenses();

        ArrayList<Double> transfers = transferMatrix.get(participantIndex);
        return transfers.stream().reduce(0.0, (acc, amt) -> acc + amt);
    }

    /**
     * Returns a {@code List} of triplets, describing all the settlements required
     * to resolve all debt in this {@code Activity}. Each triplet describes a settlement,
     * comprising a sender, recipient, and the transfer amount, in that order. The
     * sender and recipient are specified by their primary key.
     * @return A {@code List} of {@code Triplet}, each describing a required settlement.
     */
    public List<Triplet<Integer, Integer, Double>> getSolution() {
        List<Triplet<Integer, Integer, Double>> sol = new ArrayList<>();

        simplifyExpenses();

        int numParticipants = getParticipantCount();

        for (int i = 0; i < numParticipants; i++) {
            ArrayList<Double> row = transferMatrix.get(i);
            for (int j = i; j < numParticipants; j++) {
                double transferAmt = row.get(j);

                // i and j do not owe each other anything
                if (transferAmt == 0.0) {
                    continue;
                }

                if (row.get(j) < 0) {
                    // i owes j some amount (i --> j)
                    sol.add(new Triplet<>(participantIds.get(i), participantIds.get(j), -transferAmt));
                } else {
                    // j owes i some amount (j --> i)
                    sol.add(new Triplet<>(participantIds.get(j), participantIds.get(i), transferAmt));
                }
            }
        }

        return sol;
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

        expenses.add(expense);

        if (expense.isDeleted()) {
            return;
        }

        positionMask = IntStream.of(involved)
            .map(x -> idDict.get(x))
            .toArray();


        // We update the balance sheet
        double splitAmount;
        if (expense.isSettlement()) {
            double debt = getOwed(involved[0], payer);
            if (debt < 0) {
                return;
            } else if (amount == 0) {
                splitAmount = debt;
            } else {
                splitAmount = amount;
            }
        } else {
            splitAmount = amount / (involved.length + 1);
        }

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
    }

    /**
     * Soft deletes an expense within this activity.
     * @param position The 0-indexed expense number to delete
     */
    public void deleteExpense(int position) {
        Expense expense = expenses.get(position);
        expense.delete();
        deleteExpense(expense);

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
        double splitAmount = expense.isSettlement() ? amount : amount / (involved.length + 1);

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
