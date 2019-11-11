package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

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
        expectedModel.saveAddressBookState();

        assertCommandSuccess(new AddCommand(validPerson), model,
            String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePersonWithSameFields_throwsCommandExceptionWithoutMerge() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        AddCommand addCommand = new AddCommand(personInList);
        assertCommandFailure(new AddCommand(personInList), model,
            addCommand.generateExceptionMessageWithoutMergePrompt(personInList));
    }

    @Test
    public void execute_duplicatePersonWithDifferentFields_throwsCommandExceptionWithoutMerge() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        String personInListNric = personInList.getNric().nric;
        Person personWithSameNric = new PersonBuilder().withNric(personInListNric).build();
        AddCommand addCommand = new AddCommand(personWithSameNric);
        assertCommandFailure(new AddCommand(personWithSameNric), model,
                addCommand.generateExceptionMessageWithMergePrompt(personInList));
    }

}
