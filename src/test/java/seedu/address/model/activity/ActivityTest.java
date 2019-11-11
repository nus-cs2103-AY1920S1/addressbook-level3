package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.activity.exceptions.PersonNotInActivityException;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TypicalActivities;
import seedu.address.testutil.TypicalPersons;

public class ActivityTest {

    @Test
    public void debtAlgo_checkTwoPersons() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        Amount treefiddy = new Amount(30);
        Expense one = new Expense(aid, treefiddy, "testing");
        Expense two = new Expense(eid, treefiddy, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.ELLE)
            .addExpense(one)
            .addExpense(two)
            .build();

        ArrayList<ArrayList<Double>> transfermatrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E
                    new ArrayList<>(List.of(0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0))
                    ));

        assertEquals(a.getTransferMatrix(), transfermatrix);
    }

    @Test
    public void debtAlgo_checkThreePersons() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        int gid = TypicalPersons.GEORGE.getPrimaryKey();
        Amount bout = new Amount(30);
        Amount tree = new Amount(60);
        Amount fiddy = new Amount(90);
        Expense one = new Expense(aid, bout, "testing");
        Expense two = new Expense(eid, tree, "testing");
        Expense three = new Expense(gid, fiddy, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);

        // In the end, A owes G $30. G owes A -$30 just for bookkeeping.
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E    G
                    new ArrayList<>(List.of(0.0, 0.0, -30.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(30.0, 0.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());

        one = new Expense(eid, bout, "testing");
        two = new Expense(gid, tree, "testing");
        three = new Expense(aid, fiddy, "testing");

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);

        // In the end, E owes G $30.
        matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E    G
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, -30.0)),
                    new ArrayList<>(List.of(0.0, 30.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());
    }

    @Test
    public void debtAlgo_checkSettleDebts() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        int gid = TypicalPersons.GEORGE.getPrimaryKey();
        Amount bout = new Amount(30);
        Amount tree = new Amount(60);
        Amount fiddy = new Amount(90);
        Expense one = new Expense(aid, bout, "testing");
        Expense two = new Expense(eid, tree, "testing");
        Expense three = new Expense(gid, fiddy, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);

        Expense settle = new Expense(aid, new Amount(0), "", true, gid);
        a.addExpense(settle);

        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E    G
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);

        Expense settlepartial = new Expense(aid, new Amount(1), "", true, gid);
        a.addExpense(settlepartial);

        ArrayList<ArrayList<Double>> matrixreloaded = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E    G
                    new ArrayList<>(List.of(0.0, 0.0, -29.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(29.0, 0.0, 0.0))
                    ));

        assertEquals(matrixreloaded, a.getTransferMatrix());
    }
    @Test
    public void debtAlgo_checkThreePersonsButOnlyTwoAreInvolved() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        Amount bout = new Amount(30);
        Amount tree = new Amount(60);
        Expense one = new Expense(aid, bout, "testing", eid);
        Expense two = new Expense(eid, tree, "testing", aid);

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        a.addExpense(one);
        a.addExpense(two);

        // In the end, A owes G $30. G owes A -$30 just for bookkeeping.
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E    G
                    new ArrayList<>(List.of(0.0, -15.0, 0.0)),
                    new ArrayList<>(List.of(15.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());

        one = new Expense(eid, bout, "testing", aid);
        two = new Expense(aid, tree, "testing", eid);

        a.addExpense(one);
        a.addExpense(two);

        // In the end, E owes G $30.
        matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    E    G
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());
    }

    @Test
    public void debtAlgo_checkFourPersons() {
        // Hey I swear I drew the graph manually and calculated it
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int bid = TypicalPersons.BOB.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        int gid = TypicalPersons.GEORGE.getPrimaryKey();
        Amount its = new Amount(4);
        Amount bout = new Amount(8);
        Amount tree = new Amount(10);
        Amount fiddy = new Amount(20);
        Expense one = new Expense(aid, its, "testing");
        Expense two = new Expense(bid, bout, "testing");
        Expense three = new Expense(eid, tree, "testing");
        Expense four = new Expense(gid, fiddy, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        a.addExpense(one, two, three, four);

        // In the end, A owes G $30. G owes A -$30 just for bookkeeping.
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    B    E    G
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, -6.5)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, -2.5)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, -0.5)),
                    new ArrayList<>(List.of(6.5, 2.5, 0.5, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());
    }

    @Test
    public void debtAlgo_invitePersons() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int bid = TypicalPersons.BOB.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        int gid = TypicalPersons.GEORGE.getPrimaryKey();
        Amount its = new Amount(3);
        Amount bout = new Amount(6);
        Amount tree = new Amount(9);
        Amount fiddy = new Amount(10);
        Expense one = new Expense(aid, its, "testing");
        Expense two = new Expense(bid, bout, "testing");
        Expense three = new Expense(eid, tree, "testing");
        Expense four = new Expense(gid, fiddy, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .build();

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);
        a.invite(TypicalPersons.GEORGE);
        a.addExpense(four);

        // In the end, A owes G $30. G owes A -$30 just for bookkeeping.
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    B    E    G
                    new ArrayList<>(List.of(0.0, 0.0, -0.5, -5.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, -2.5)),
                    new ArrayList<>(List.of(0.5, 0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(5.0, 2.5, 0.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());
    }

    @Test
    public void debtAlgo_deleteExpense_success() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int bid = TypicalPersons.BOB.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        int gid = TypicalPersons.GEORGE.getPrimaryKey();
        Amount its = new Amount(3);
        Amount bout = new Amount(6);
        Amount tree = new Amount(9);
        Amount fiddy = new Amount(10);
        Expense one = new Expense(aid, its, "testing");
        Expense two = new Expense(bid, bout, "testing");
        Expense three = new Expense(eid, tree, "testing");
        Expense four = new Expense(gid, fiddy, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .build();

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);
        a.invite(TypicalPersons.GEORGE);
        a.addExpense(four);
        a.deleteExpense(one);
        a.deleteExpense(two);
        a.deleteExpense(three);
        a.deleteExpense(four);

        // In the end, A owes G $30. G owes A -$30 just for bookkeeping.
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(
                List.of(
                    // (Same for rows)       A    B    E    G
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0)),
                    new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0))
                    ));

        assertEquals(matrix, a.getTransferMatrix());
    }

    @Test
    public void deleteExpenseMissingPerson_fail() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int bid = TypicalPersons.BOB.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();
        Amount its = new Amount(3);
        Amount bout = new Amount(6);
        Amount tree = new Amount(9);
        Expense one = new Expense(aid, its, "testing");
        Expense two = new Expense(bid, bout, "testing");
        Expense three = new Expense(eid, tree, "testing");

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .build();

        a.addExpense(one);
        a.addExpense(two);
        assertThrows(PersonNotInActivityException.class, () -> a.deleteExpense(three));
    }

    @Test
    public void addDeletedExpense() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        Amount treefiddy = new Amount(30);
        Expense one = new Expense(aid, treefiddy, "testing");
        one.delete();

        Activity a = new ActivityBuilder()
                .withTitle("test")
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.ELLE)
                .addExpense(one)
                .build();

        ArrayList<ArrayList<Double>> transfermatrix = new ArrayList<>(
                List.of(
                        //                       A    E
                        new ArrayList<>(List.of(0.0, 0.0)),
                        new ArrayList<>(List.of(0.0, 0.0))
                ));

        // Debt matrix not updated while expense list is updated
        assertEquals(a.getTransferMatrix(), transfermatrix);
        assertEquals(a.getExpenses(), List.of(one));
    }

    @Test
    public void activity_disinvitePersons_success() {
        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        Activity b = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        b.disinvite(TypicalPersons.ELLE.getPrimaryKey());
        b.disinvite(TypicalPersons.GEORGE, TypicalPersons.BOB);
        b.disinvite(TypicalPersons.BOB, TypicalPersons.ALICE);
        a.disinvite(TypicalPersons.GEORGE.getPrimaryKey(),
                TypicalPersons.ELLE.getPrimaryKey());
        a.disinvite(TypicalPersons.BOB, TypicalPersons.ALICE);

        assertEquals(a, b);
    }

    @Test
    public void disinviteRandomPersonsStatusChecked() {
        int aid = TypicalPersons.ALICE.getPrimaryKey();
        int bid = TypicalPersons.BOB.getPrimaryKey();
        int eid = TypicalPersons.ELLE.getPrimaryKey();

        Amount its = new Amount(3);
        Amount bout = new Amount(6);
        Amount tree = new Amount(9);

        Expense one = new Expense(aid, its, "testing", bid, eid);
        Expense two = new Expense(bid, bout, "testing", aid, eid);
        Expense three = new Expense(eid, tree, "testing", aid, bid);

        Activity a = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        Activity b = new ActivityBuilder()
            .withTitle("test")
            .addPerson(TypicalPersons.ALICE)
            .addPerson(TypicalPersons.BOB)
            .addPerson(TypicalPersons.ELLE)
            .addPerson(TypicalPersons.GEORGE)
            .build();

        a.addExpense(one, two, three);
        b.addExpense(one, two, three);
        a.disinvite(aid, bid);
        a.disinvite(eid);

        assertEquals(a, b); // as though nothing happened

        a.disinvite(TypicalPersons.GEORGE);
        assertNotEquals(a, b);
    }

    @Test
    public void getParticipantIds() {
        Activity lunch = TypicalActivities.LUNCH;
        ArrayList<Integer> expectedIds = new ArrayList<Integer>();
        expectedIds.add(TypicalPersons.BENSON.getPrimaryKey());
        expectedIds.add(TypicalPersons.CARL.getPrimaryKey());

        assertEquals(lunch.getParticipantIds(), expectedIds);
    }

    @Test
    public void getParticipantCount_staticActivities_correctCount() {
        Activity emptyActivity = TypicalActivities.BREAKFAST_EMPTY;
        assertEquals(0, emptyActivity.getParticipantCount());

        Activity activityWithParticipants = TypicalActivities.LUNCH;
        assertEquals(2, activityWithParticipants.getParticipantCount());
    }

    @Test
    public void getParticipantCount_participantsChange_updatesCount() {
        Activity activity = new ActivityBuilder()
                .withTitle("Activity with changing participants")
                .addPerson(TypicalPersons.ALICE)
                .addPerson(TypicalPersons.BOB)
                .build();

        activity.invite(TypicalPersons.CARL);
        assertEquals(3, activity.getParticipantCount());

        activity.disinvite(TypicalPersons.ALICE, TypicalPersons.CARL);
        assertEquals(1, activity.getParticipantCount());
    }

    @Test
    public void getTotalSpending_noDeletedItems_excludesSettlements() {
        Activity activity = new ActivityBuilder()
                .withTitle("Activity with static expenses and settlements")
                .addPerson(TypicalPersons.AMY)
                .addPerson(TypicalPersons.BENSON)
                .build();

        List<Integer> ids = activity.getParticipantIds();

        // 2 expenses and 1 partial settlement
        // Amounts are specifically chosen to avoid floating-point errors
        Expense appetiser = new Expense(ids.get(0), new Amount(12.00), "Appetiser");
        Expense mainCourse = new Expense(ids.get(1), new Amount(24.00), "Main course");
        Expense aliceToBob = new Expense(ids.get(0), new Amount(3.00), "Partial repayment", true, ids.get(1));

        activity.addExpense(appetiser, mainCourse, aliceToBob);

        assertEquals(36.00, activity.getTotalSpending());
    }

    @Test
    public void getTotalSpending_insertAndSoftDeleteItems_updatesSum() {
        Activity activity = new ActivityBuilder()
                .withTitle("Activity with insertions and soft deletions")
                .addPerson(TypicalPersons.ANDY)
                .addPerson(TypicalPersons.BOB)
                .addPerson(TypicalPersons.CARL)
                .build();

        List<Integer> ids = activity.getParticipantIds();

        // 3 expenses: index 0, 1, 2
        // Amounts are specifically chosen to avoid floating-point errors
        Expense transport = new Expense(ids.get(0), new Amount(210.75), "Bus tickets");
        Expense hotel = new Expense(ids.get(1), new Amount(301.50), "Resort stay");
        Expense archery = new Expense(ids.get(2), new Amount(69.00), "Archery activity", ids.get(0));

        // 1 partial settlement (index 3) and 1 full settlement (index 4)
        Expense carlToAndy = new Expense(ids.get(2), new Amount(8.50), "Half settlement", true, ids.get(0));
        Expense carlToBob = new Expense(ids.get(2), new Amount(107.75), "Full settlement", true, ids.get(1));

        activity.addExpense(transport, hotel, archery, carlToAndy, carlToBob);

        assertEquals(581.25, activity.getTotalSpending());

        // Insertion of new expense (index 5) -> adds new amount to sum
        Expense errorExpense = new Expense(ids.get(0), new Amount(60.00), "Added in error");
        activity.addExpense(errorExpense);
        assertEquals(641.25, activity.getTotalSpending());

        // Invalidation of newly added expense -> removes added amount from sum
        activity.deleteExpense(5);
        assertEquals(581.25, activity.getTotalSpending());

        // Invalidation of existing expense -> removes that amount from sum
        activity.deleteExpense(2);
        assertEquals(512.25, activity.getTotalSpending());

        // Invalidation of existing settlement -> no change to sum
        activity.deleteExpense(3);
        assertEquals(512.25, activity.getTotalSpending());

        // Insertion of new full settlement (index 6) -> no change to sum
        Expense carlToAndyFull = new Expense(ids.get(2), new Amount(40.00), "New full settlement", true, ids.get(0));
        activity.addExpense(carlToAndyFull);
        assertEquals(512.25, activity.getTotalSpending());

        // Invalidation of newly added settlement -> no change to sum
        activity.deleteExpense(6);
        assertEquals(512.25, activity.getTotalSpending());
    }

    @Test
    public void hasPerson() {
        Activity lunch = TypicalActivities.LUNCH;

        // Person exists -> Return true
        assertTrue(lunch.hasPerson(TypicalPersons.BENSON.getPrimaryKey()));

        // Person doesn't exist -> return false
        assertFalse(lunch.hasPerson(TypicalPersons.ALICE.getPrimaryKey()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Activity lunch = TypicalActivities.LUNCH;
        Activity lunchCopy = new ActivityBuilder(lunch).build();
        assertTrue(lunch.equals(lunchCopy));

        // same object -> returns true
        assertTrue(lunch.equals(lunch));

        // null -> returns false
        assertFalse(lunch.equals(null));

        // different type -> returns false
        assertFalse(lunch.equals(5));

        // different activity -> returns false
        assertFalse(lunch.equals(TypicalActivities.BREAKFAST));

        // different title -> returns false
        Activity editedLunch = new ActivityBuilder(lunch).withTitle("Better Lunch").build();
        assertFalse(lunch.equals(editedLunch));

        // different participants -> returns false
        editedLunch = new ActivityBuilder(lunch).addPerson(TypicalPersons.ALICE).build();
        assertFalse(lunch.equals(editedLunch));

        // different expenses -> returns false
        int id = lunch.getParticipantIds().get(0);
        editedLunch = new ActivityBuilder(lunch).addExpense(new Expense(id, new Amount(0), "")).build();
        assertFalse(lunch.equals(editedLunch));
    }

    @Test
    public void hashCode_sameActivityValues_hashCodeIsCorrect() {
        Activity breakfast = TypicalActivities.BREAKFAST;
        Activity breakfastCopy = new ActivityBuilder(breakfast).build();

        // activities with same values -> returns true
        assertEquals(breakfast.hashCode(), breakfastCopy.hashCode());
    }
}
