package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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

        a.addExpense(one);
        a.addExpense(two);
        a.addExpense(three);
        a.addExpense(four);

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

    public void getParticipantIds() {
        Activity lunch = TypicalActivities.LUNCH;
        ArrayList<Integer> expectedIds = new ArrayList<Integer>();
        expectedIds.add(TypicalPersons.BENSON.getPrimaryKey());
        expectedIds.add(TypicalPersons.CARL.getPrimaryKey());

        assertEquals(lunch.getParticipantIds(), expectedIds);
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

        //TODO: Different expenses -> returns false;
    }

}
