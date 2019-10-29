package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.testutil.ActivityBuilder;

public class DisinviteCommandTest {

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DisinviteCommand(null));
    }


    @Test
    public void execute_emptyListOfString_throwsCommandException() {
        Model model = new ModelManager();
        List<String> peopleToInvite = new ArrayList<>();
        DisinviteCommand disinviteCommand = new DisinviteCommand(peopleToInvite);

        assertThrows(CommandException.class, () -> disinviteCommand.execute(model));
    }

    @Test
    public void execute_notViewActivityContext_throwsCommandException() {
        Model model = new ModelManager();
        List<String> peopleToInvite = new ArrayList<>();
        peopleToInvite.add("Benny");
        DisinviteCommand disinviteCommand = new DisinviteCommand(peopleToInvite);

        assertThrows(CommandException.class, () -> disinviteCommand.execute(model));
    }

    @Test
    public void execute_viewActivityContext_disinviteSuccessful() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BENSON);
        Activity activity = new ActivityBuilder().addPerson(ALICE).addPerson(BENSON).build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToDisinvite = new ArrayList<>();
        peopleToDisinvite.add(ALICE.getName().toString());
        List<Integer> ids = new ArrayList<>();
        ids.add(BENSON.getPrimaryKey());
        DisinviteCommand disinviteCommand = new DisinviteCommand(peopleToDisinvite);
        disinviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(ids, activity.getParticipantIds());

        peopleToDisinvite.add(BENSON.getName().fullName);
        ids.clear();
        disinviteCommand = new DisinviteCommand(peopleToDisinvite);
        disinviteCommand.execute(model);

        assertEquals(0, activity.getParticipantIds().size());
        assertEquals(ids, activity.getParticipantIds());
    }

    @Test
    public void execute_viewActivityContextMissingPerson_disinviteUnsuccessful() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(BENSON);
        model.addPerson(CARL);
        Activity activity = new ActivityBuilder().addPerson(BENSON).build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToDisinvite = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        ids.add(BENSON.getPrimaryKey());
        peopleToDisinvite.add(ALICE.getName().fullName);
        DisinviteCommand inviteCommand = new DisinviteCommand(peopleToDisinvite);
        inviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(ids, activity.getParticipantIds());


        peopleToDisinvite.add(CARL.getName().fullName);
        inviteCommand = new DisinviteCommand(peopleToDisinvite);
        inviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(ids, activity.getParticipantIds());
    }

    @Test
    public void execute_viewActivityInvolvedExpense_disinviteUnsuccessful() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(BENSON);
        model.addPerson(ALICE);
        Amount amount = new Amount(10.0);

        //Benson pays for Alice, Alice owes Benson money
        Expense expense = new Expense(BENSON.getPrimaryKey(), amount, "breakfast", ALICE.getPrimaryKey());
        Activity activity = new ActivityBuilder().addPerson(BENSON).addPerson(ALICE).addExpense(expense).build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToDisinvite = new ArrayList<>();
        peopleToDisinvite.add(BENSON.getName().fullName);
        peopleToDisinvite.add(ALICE.getName().fullName);
        List<Integer> ids = new ArrayList<>();
        ids.add(BENSON.getPrimaryKey());
        ids.add(ALICE.getPrimaryKey());
        DisinviteCommand disinviteCommand = new DisinviteCommand(peopleToDisinvite);
        disinviteCommand.execute(model);

        assertEquals(2, activity.getParticipantIds().size());
        assertEquals(ids, activity.getParticipantIds());
    }

    @Test
    public void execute_viewActivityNotInvolvedExpense_disinviteSuccessful() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(BENSON);
        model.addPerson(ALICE);
        model.addPerson(CARL);
        Amount amount = new Amount(10.0);

        //Benson pays for Alice, Alice owes Benson money, Carl is not involved in expense
        Expense expense = new Expense(BENSON.getPrimaryKey(), amount, "breakfast", ALICE.getPrimaryKey());
        Activity activity = new ActivityBuilder()
                .addPerson(BENSON)
                .addPerson(ALICE)
                .addPerson(CARL)
                .addExpense(expense)
                .build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToDisinvite = new ArrayList<>();
        peopleToDisinvite.add(CARL.getName().fullName);
        List<Integer> ids = new ArrayList<>();
        ids.add(BENSON.getPrimaryKey());
        ids.add(ALICE.getPrimaryKey());
        DisinviteCommand disinviteCommand = new DisinviteCommand(peopleToDisinvite);
        disinviteCommand.execute(model);

        assertEquals(2, activity.getParticipantIds().size());
        assertEquals(ids, activity.getParticipantIds());
    }
}
