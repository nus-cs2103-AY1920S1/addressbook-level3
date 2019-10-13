package seedu.ezwatchlist.logic.commands;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

<<<<<<< HEAD:src/test/java/seedu/address/logic/commands/AddCommandIntegrationTest.java
import seedu.ezwatchlist.logic.commands.AddCommand;
=======
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/commands/AddCommandIntegrationTest.java
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.person.Person;
<<<<<<< HEAD:src/test/java/seedu/address/logic/commands/AddCommandIntegrationTest.java
import seedu.address.testutil.PersonBuilder;
=======
import seedu.ezwatchlist.testutil.PersonBuilder;
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/commands/AddCommandIntegrationTest.java

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
