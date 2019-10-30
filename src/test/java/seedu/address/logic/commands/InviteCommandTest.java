package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.PersonBuilder;

public class InviteCommandTest {

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InviteCommand(null));
    }

    @Test
    public void execute_emptyListOfString_throwsCommandException() {
        Model model = new ModelManager();
        List<String> peopleToInvite = new ArrayList<>();
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);

        assertThrows(CommandException.class, () -> inviteCommand.execute(model));

    }

    @Test
    public void execute_notViewActivityContext_throwsCommandException() {
        Model model = new ModelManager();
        List<String> peopleToInvite = new ArrayList<>();
        peopleToInvite.add("Benny");
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);

        assertThrows(CommandException.class, () -> inviteCommand.execute(model));
    }

    @Test
    public void execute_viewActivityContext_inviteSuccessful() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BENSON);
        Activity activity = new ActivityBuilder().build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToInvite = new ArrayList<>();
        peopleToInvite.add(ALICE.getName().toString());
        List<Integer> idsToInvite = new ArrayList<>();
        idsToInvite.add(ALICE.getPrimaryKey());
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());

        peopleToInvite.add(BENSON.getName().fullName);
        idsToInvite.add(BENSON.getPrimaryKey());
        inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(2, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());


    }

    @Test
    public void execute_viewActivityDuplicateEntries_inviteOne() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(ALICE); // Alice Pauline
        model.addPerson(BENSON);
        Activity activity = new ActivityBuilder().build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToInvite = new ArrayList<>();
        List<Integer> idsToInvite = new ArrayList<>();
        peopleToInvite.add(ALICE.getName().toString());
        peopleToInvite.add(ALICE.getName().toString());
        idsToInvite.add(ALICE.getPrimaryKey());
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());

        peopleToInvite.add(BENSON.getName().toString());
        peopleToInvite.add(BENSON.getName().toString());
        idsToInvite.add(BENSON.getPrimaryKey());
        inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(2, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());
    }

    @Test
    public void execute_viewActivityContextDuplicatePerson_inviteUnsuccessful() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BENSON);
        Activity activity = new ActivityBuilder().addPerson(ALICE).build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToInvite = new ArrayList<>();
        List<Integer> idsToInvite = new ArrayList<>();
        peopleToInvite.add(ALICE.getName().fullName);
        idsToInvite.add(ALICE.getPrimaryKey());
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());

        peopleToInvite.add(BENSON.getName().fullName);
        idsToInvite.add(BENSON.getPrimaryKey());
        inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(2, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());

    }

    @Test
    public void execute_viewActivityContextMissingPerson_inviteUnsuccessful() throws CommandException {
        Model model = new ModelManager();
        Activity activity = new ActivityBuilder().build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToInvite = new ArrayList<>();
        peopleToInvite.add(ALICE.getName().fullName);
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(0, activity.getParticipantIds().size());

        peopleToInvite.add(BENSON.getName().fullName);
        inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(0, activity.getParticipantIds().size());


    }

    @Test
    public void execute_viewActivityPersonSubstringOfAnother_inviteExactMatchSuccessful() throws CommandException {
        Model model = new ModelManager();
        Person yeoh = new PersonBuilder().withName("YEOH").build();
        Person alexYeoh = new PersonBuilder().withName("Alex Yeoh").build();
        model.addPerson(yeoh);
        model.addPerson(alexYeoh);
        Activity activity = new ActivityBuilder().build();
        model.addActivity(activity);
        model.setContext(new Context(activity));
        List<String> peopleToInvite = new ArrayList<>();
        List<Integer> idsToInvite = new ArrayList<>();
        peopleToInvite.add("yeoh");
        idsToInvite.add(yeoh.getPrimaryKey());
        InviteCommand inviteCommand = new InviteCommand(peopleToInvite);
        inviteCommand.execute(model);

        assertEquals(1, activity.getParticipantIds().size());
        assertEquals(idsToInvite, activity.getParticipantIds());

    }

}
