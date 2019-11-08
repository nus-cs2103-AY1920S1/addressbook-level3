package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TutorAid;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class SetPictureCommandTest {

    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void execute_validPictureSpecifiedUnfilteredList_success() {
        Person personToSetPicture = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        PersonBuilder personInList = new PersonBuilder(personToSetPicture);

        Person personWithPictureSet = personInList.withPicture(VALID_PICTURE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personToSetPicture)
                .withPicture(VALID_PICTURE_BOB).build();

        SetPictureCommand setPictureCommand = new SetPictureCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(SetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, personWithPictureSet);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.setPerson(personToSetPicture, personWithPictureSet);
        expectedModel.commitTutorAid();
        assertCommandSuccess(setPictureCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        SetPictureCommand setPictureCommand = new SetPictureCommand(INDEX_FIRST, new EditPersonDescriptor());
        Person setPicturePerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(SetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, setPicturePerson);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.commitTutorAid();
        assertCommandSuccess(setPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person personWithPictureSet = new PersonBuilder(personInFilteredList).withPicture(VALID_PICTURE_AMY).build();
        SetPictureCommand setPictureCommand = new SetPictureCommand(INDEX_FIRST,
                new EditPersonDescriptorBuilder().withPicture(VALID_PICTURE_AMY).build());

        String expectedMessage = String.format(SetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, personWithPictureSet);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithPictureSet);
        expectedModel.commitTutorAid();
        assertCommandSuccess(setPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPicture(VALID_PICTURE_AMY).build();
        SetPictureCommand setPictureCommand = new SetPictureCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(setPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorAid().getPersonList().size());

        SetPictureCommand setPictureCommand = new SetPictureCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withPicture(VALID_PICTURE_AMY).build());

        assertCommandFailure(setPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final SetPictureCommand standardCommand = new SetPictureCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        SetPictureCommand commandWithSameValues = new SetPictureCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetPictureCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new SetPictureCommand(INDEX_FIRST, DESC_BOB)));
    }

}
