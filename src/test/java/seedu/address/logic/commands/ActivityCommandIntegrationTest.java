package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_WARNING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ActivityBook;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.Person;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class ActivityCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                TypicalPersons.getSameSurnameAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
    }

    @Test
    public void execute_newActivityWithParticipant_success() {
        Person person = TypicalPersons.ALICE;
        String stringTitle = "Test Activity";
        Title title = new Title(stringTitle);
        Activity validActivity = new ActivityBuilder().withTitle(stringTitle).addPerson(person).build();
        String searchTerm = "Alice";
        String successMessage = String.format(
                ActivityCommand.MESSAGE_SUCCESS,
                validActivity,
                person.getName(),
                "");
        ArrayList<String> participants = new ArrayList<String>();
        participants.add(searchTerm);

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
        Context newContext = new Context(validActivity);
        expectedModel.addActivity(validActivity);
        expectedModel.setContext(newContext);

        assertCommandSuccess(new ActivityCommand(title, participants), model,
                successMessage, expectedModel, newContext);
    }

    @Test
    public void execute_newActivityWithMultipleMatches_success() {
        String stringTitle = "Test Activity";
        Title title = new Title(stringTitle);
        Activity validActivity = new ActivityBuilder().withTitle(stringTitle).build();
        String searchTerm = "Pauline";
        String warningMessage = String.format(
                ActivityCommand.WARNING_SEARCH_RESULTS,
                searchTerm,
                2);
        String successMessage = String.format(
                ActivityCommand.MESSAGE_SUCCESS
                + MESSAGE_WARNING,
                validActivity,
                "",
                warningMessage);
        ArrayList<String> participants = new ArrayList<String>();
        participants.add(searchTerm);

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
        Context newContext = new Context(validActivity);
        expectedModel.addActivity(validActivity);
        expectedModel.setContext(newContext);

        assertCommandSuccess(new ActivityCommand(title, participants), model,
                successMessage, expectedModel, newContext);
    }

    @Test
    public void execute_newActivityWithZeroMatches_success() {
        String stringTitle = "Test Activity";
        Title title = new Title(stringTitle);
        Activity validActivity = new ActivityBuilder().withTitle(stringTitle).build();
        String searchTerm = "No one lol";
        String warningMessage = String.format(
                ActivityCommand.WARNING_SEARCH_RESULTS,
                searchTerm,
                0);
        String successMessage = String.format(
                ActivityCommand.MESSAGE_SUCCESS
                + MESSAGE_WARNING,
                validActivity,
                "",
                warningMessage);
        ArrayList<String> participants = new ArrayList<String>();
        participants.add(searchTerm);

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
        Context newContext = new Context(validActivity);
        expectedModel.addActivity(validActivity);
        expectedModel.setContext(newContext);

        assertCommandSuccess(new ActivityCommand(title, participants), model,
                successMessage, expectedModel, newContext);
    }
}
