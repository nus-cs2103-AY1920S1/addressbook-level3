package seedu.address.logic.commands.editcommand;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PARTICIPANT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.editcommand.EditParticipantCommand.EditParticipantDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.EditParticipantDescriptorBuilder;
import seedu.address.testutil.ParticipantBuilder;
import seedu.address.testutil.TypicalParticipants;

public class EditParticipantCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManagerStub(); // create an empty model
    }

    @Test
    public void execute_allFieldsSpecifiedParticipantList_success() throws AlfredException {
        Participant participantToEdit = new ParticipantBuilder().build();
        model.addParticipant(participantToEdit);
        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().build();
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                participantToEdit.getId(),
                descriptor
        );
        String expectedMessage = String.format(
                EditParticipantCommand.MESSAGE_EDIT_PARTICIPANT_SUCCESS,
                participantToEdit
        );
        Model expectedModel = new ModelManagerStub();
        expectedModel.addParticipant(participantToEdit);

        assertCommandSuccess(editParticipantCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedParticipantList_success() throws AlfredException {
        Participant participantToEdit = new ParticipantBuilder().build();
        model.addParticipant(participantToEdit);

        ParticipantBuilder participantInList = new ParticipantBuilder(participantToEdit);
        Participant editedParticipant = participantInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                participantToEdit.getId(),
                descriptor
        );

        String expectedMessage = String.format(
                EditParticipantCommand.MESSAGE_EDIT_PARTICIPANT_SUCCESS,
                editedParticipant
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addParticipant(editedParticipant);

        assertCommandSuccess(editParticipantCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedParticipantList_success() throws AlfredException {
        Participant editedParticipant = new ParticipantBuilder().build();
        model.addParticipant(editedParticipant);
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                editedParticipant.getId(),
                new EditParticipantDescriptor()
        );

        String expectedMessage = String.format(
                EditParticipantCommand.MESSAGE_EDIT_PARTICIPANT_SUCCESS,
                editedParticipant
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addParticipant(editedParticipant);

        assertCommandSuccess(editParticipantCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateParticipant_failure() throws AlfredException {
        Participant firstPerson = TypicalParticipants.A;
        Participant secondPerson = TypicalParticipants.B;
        model.addParticipant(firstPerson);
        model.addParticipant(secondPerson);
        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder(firstPerson).build();
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(secondPerson.getId(), descriptor);

        assertCommandFailure(editParticipantCommand, model, ParticipantList.SIMILAR_PARTICIPANT_MSG);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws AlfredException {
        Participant validParticipant = new ParticipantBuilder().build();
        model.addParticipant(validParticipant);
        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditParticipantCommand editParticipantCommand = new EditParticipantCommand(
                ID_SECOND_PARTICIPANT,
                descriptor
        );

        assertCommandFailure(
                editParticipantCommand,
                model,
                EditParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX
        );
    }

}
