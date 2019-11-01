package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.ActivityBook;
import seedu.address.model.AddressBook;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());

    @Test
    public void execute_allFieldsSpecifiedContactViewContext_success() {
        Person editedPerson = new PersonBuilder().withName("a name").build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(descriptor);

        Person personToEdit = model.getFilteredPersonList().get(0);
        model.setContext(new Context(personToEdit));

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new InternalState(), new ActivityBook());
        expectedModel.setPerson(personToEdit, editedPerson);
        Context newContext = new Context(editedPerson);
        expectedModel.setContext(newContext);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, newContext);
    }

    @Test
    public void execute_someFieldsSpecifiedContactViewContext_success() {
        int last = model.getFilteredPersonList().size();
        Person lastPerson = model.getFilteredPersonList().get(last - 1);
        model.setContext(new Context(lastPerson));

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new InternalState(), new ActivityBook());

        expectedModel.setPerson(lastPerson, editedPerson);
        Context newContext = new Context(editedPerson);
        expectedModel.setContext(newContext);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, newContext);
    }

    @Test
    public void execute_noFieldSpecifiedContactViewContext_success() {
        EditCommand editCommand = new EditCommand(new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(0);
        model.setContext(new Context(editedPerson));

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new InternalState(), new ActivityBook());
        Context newContext = new Context(editedPerson);
        expectedModel.setContext(newContext);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, newContext);
    }

    @Test
    public void execute_wrongContext_failure() {
        model.setContext(Context.newListActivityContext());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(new PersonBuilder().build()).build();
        EditCommand editCommand = new EditCommand(descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_WRONG_CONTEXT);
    }

    @Test
    public void execute_duplicatePersonContactViewContext_failure() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.setContext(new Context(firstPerson));
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateNameContactViewContext_failure() {
        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        model.setContext(new Context(firstPerson));
        Person personInList = model.getAddressBook().getPersonList().get(1);
        EditPersonDescriptor desc = new EditPersonDescriptor();
        desc.setName(personInList.getName());
        EditCommand editCommand = new EditCommand(desc);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB)));
    }
}
