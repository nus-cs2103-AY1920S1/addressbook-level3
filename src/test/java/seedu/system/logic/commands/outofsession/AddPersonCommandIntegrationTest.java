package seedu.system.logic.commands.outofsession;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.testutil.TypicalCompetitions.getTypicalCompetitionData;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;
import seedu.system.model.person.Person;
import seedu.system.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddPersonCommand}.
 */
public class AddPersonCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPersonData(), getTypicalCompetitionData(), new Data(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel =
            new ModelManager(
                model.getPersons(),
                model.getCompetitions(),
                model.getParticipations(),
                new UserPrefs()
            );
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddPersonCommand(validPerson), model,
                String.format(AddPersonCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getPersons().getListOfElements().get(0);
        assertCommandFailure(new AddPersonCommand(personInList), model, AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
