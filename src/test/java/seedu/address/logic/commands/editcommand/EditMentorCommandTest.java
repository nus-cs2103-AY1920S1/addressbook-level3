package seedu.address.logic.commands.editcommand;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIds.ID_SECOND_MENTOR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entitylist.MentorList;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.EditMentorDescriptorBuilder;
import seedu.address.testutil.MentorBuilder;
import seedu.address.testutil.TypicalMentors;

public class EditMentorCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManagerStub(); // create an empty model
    }

    @Test
    public void execute_allFieldsSpecifiedMentorList_success() throws AlfredException {
        Mentor mentorToEdit = new MentorBuilder().build();
        model.addMentor(mentorToEdit);
        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().build();
        EditMentorCommand editMentorCommand = new EditMentorCommand(
                mentorToEdit.getId(),
                descriptor
        );
        String expectedMessage = String.format(
                EditMentorCommand.MESSAGE_EDIT_MENTOR_SUCCESS,
                mentorToEdit
        );
        Model expectedModel = new ModelManagerStub();
        expectedModel.addMentor(mentorToEdit);

        assertCommandSuccess(editMentorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedMentorList_success() throws AlfredException {
        Mentor mentorToEdit = new MentorBuilder().build();
        model.addMentor(mentorToEdit);

        MentorBuilder mentorInList = new MentorBuilder(mentorToEdit);
        Mentor editedMentor = mentorInList.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).build();

        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).build();
        EditMentorCommand editMentorCommand = new EditMentorCommand(
                mentorToEdit.getId(),
                descriptor
        );

        String expectedMessage = String.format(
                EditMentorCommand.MESSAGE_EDIT_MENTOR_SUCCESS,
                editedMentor
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addMentor(editedMentor);

        assertCommandSuccess(editMentorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedMentorList_success() throws AlfredException {
        Mentor editedMentor = new MentorBuilder().build();
        model.addMentor(editedMentor);
        EditMentorCommand editMentorCommand = new EditMentorCommand(
                editedMentor.getId(),
                new EditMentorDescriptor()
        );

        String expectedMessage = String.format(
                EditMentorCommand.MESSAGE_EDIT_MENTOR_SUCCESS,
                editedMentor
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addMentor(editedMentor);

        assertCommandSuccess(editMentorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMentor_failure() throws AlfredException {
        Mentor firstPerson = TypicalMentors.A;
        Mentor secondPerson = TypicalMentors.B;
        model.addMentor(firstPerson);
        model.addMentor(secondPerson);
        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder(firstPerson).build();
        EditMentorCommand editMentorCommand = new EditMentorCommand(secondPerson.getId(), descriptor);

        assertCommandFailure(editMentorCommand, model, MentorList.SIMILAR_MENTOR_MSG);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws AlfredException {
        Mentor validMentor = new MentorBuilder().build();
        model.addMentor(validMentor);
        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditMentorCommand editMentorCommand = new EditMentorCommand(
                ID_SECOND_MENTOR,
                descriptor
        );

        assertCommandFailure(
                editMentorCommand,
                model,
                EditMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX
        );
    }

}
