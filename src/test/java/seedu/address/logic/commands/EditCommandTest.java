package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ACTIVITY2;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.ActivityBook;
import seedu.address.model.AddressBook;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.Person;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.EditActivityDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());

    @Test
    public void execute_allFieldsSpecifiedContactViewContext_success() {
        Person editedPerson = new PersonBuilder().withName("a name").build();
        EditPersonDescriptor pd = new EditPersonDescriptorBuilder(editedPerson).build();

        // Also checks that EditActivityDescriptor information is ignored because it is a contact view context
        EditCommand editCommand = new EditCommand(pd, DESC_ACTIVITY2);

        Person personToEdit = model.getFilteredPersonList().get(0);
        model.setContext(new Context(personToEdit));

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new InternalState(), getTypicalActivityBook());
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

        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();

        // Also checks that EditActivityDescriptor information is ignored because it is a contact view context
        EditCommand editCommand = new EditCommand(pd, DESC_ACTIVITY2);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new InternalState(), getTypicalActivityBook());

        expectedModel.setPerson(lastPerson, editedPerson);
        Context newContext = new Context(editedPerson);
        expectedModel.setContext(newContext);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, newContext);
    }

    @Test
    public void execute_allFieldsSpecifiedActivityViewContext_success() { // currently only 1 field
        int last = model.getFilteredActivityList().size();
        Activity lastActivity = model.getFilteredActivityList().get(last - 1);
        model.setContext(new Context(lastActivity));

        ActivityBuilder activityInList = new ActivityBuilder(lastActivity);
        Activity editedActivity = activityInList.withTitle(VALID_ACTIVITY_TITLE2).build();

        EditActivityDescriptor ad = new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE2).build();

        // Also checks that EditPersonDescriptor information is ignored because it is a activity view context
        EditCommand editCommand = new EditCommand(DESC_AMY, ad);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), new InternalState(), new ActivityBook(model.getActivityBook()));

        expectedModel.setActivity(lastActivity, editedActivity);
        Context newContext = new Context(editedActivity);
        expectedModel.setContext(newContext);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, newContext);
    }

    @Test
    public void execute_noFieldSpecifiedContactViewContext_failure() {
        // Also checks that EditActivityDescriptor information is ignored because it is a contact view context
        EditCommand editCommand = new EditCommand(new EditPersonDescriptor(), DESC_ACTIVITY2);
        Person editedPerson = model.getFilteredPersonList().get(0);
        model.setContext(new Context(editedPerson));

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_noFieldSpecifiedActivityViewContext_failure() {
        // Also checks that EditPersonDescriptor information is ignored because it is an activity view context
        EditCommand editCommand = new EditCommand(DESC_AMY, new EditActivityDescriptor());
        Activity editedActivity = model.getFilteredActivityList().get(0);
        model.setContext(new Context(editedActivity));

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_wrongContext_failure() {
        model.setContext(Context.newListActivityContext());
        EditPersonDescriptor pd = new EditPersonDescriptorBuilder(new PersonBuilder().build()).build();
        EditCommand editCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_WRONG_CONTEXT);
    }

    @Test
    public void execute_duplicatePersonContactViewContext_failure() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.setContext(new Context(firstPerson));
        EditPersonDescriptor pd = new EditPersonDescriptorBuilder(firstPerson).build();

        // Also checks that EditActivityDescriptor information is ignored because it is a contact view context
        EditCommand editCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateNameContactViewContext_failure() {
        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        model.setContext(new Context(firstPerson));
        Person personInList = model.getAddressBook().getPersonList().get(1);
        EditPersonDescriptor pd = new EditPersonDescriptor();
        pd.setName(personInList.getName());

        // Also checks that EditActivityDescriptor information is ignored because it is a contact view context
        EditCommand editCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(DESC_AMY, DESC_ACTIVITY2);

        // same values -> returns true
        EditPersonDescriptor pdCopy = new EditPersonDescriptor(DESC_AMY);
        EditActivityDescriptor adCopy = new EditActivityDescriptor(DESC_ACTIVITY2);
        EditCommand commandWithSameValues = new EditCommand(pdCopy, adCopy);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB, DESC_ACTIVITY2)));
        EditActivityDescriptor ad = new EditActivityDescriptor();
        ad.setTitle(new Title(DESC_ACTIVITY2.getTitle().get() + "e"));
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB, ad)));
    }
}
