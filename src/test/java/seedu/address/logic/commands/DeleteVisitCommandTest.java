package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalVisits;

public class DeleteVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteVisitCommandUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withVisitList(TypicalVisits.getLongTypicalVisitList(firstPerson.getName().toString())).build();

        DeleteVisitCommand deleteVisitCommand = new DeleteVisitCommand(INDEX_SECOND_PERSON,
                3);

        String expectedMessage = String.format(DeleteVisitCommand.MESSAGE_DELETE_VISIT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteVisitCommand, model, expectedMessage, expectedModel);
    }
}
