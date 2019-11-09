package seedu.address.logic.commands.editcommand;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIds.ID_SECOND_TEAM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.editcommand.EditTeamCommand.EditTeamDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.TeamList;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.EditTeamDescriptorBuilder;
import seedu.address.testutil.TeamBuilder;
import seedu.address.testutil.TypicalTeams;

public class EditTeamCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManagerStub(); // create an empty model
    }

    @Test
    public void execute_allFieldsSpecifiedTeamList_success() throws AlfredException {
        Team teamToEdit = new TeamBuilder().build();
        model.addTeam(teamToEdit);
        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withName("Justice League").build();
        EditTeamCommand editTeamCommand = new EditTeamCommand(
                teamToEdit.getId(),
                descriptor
        );

        Model expectedModel = new ModelManagerStub();
        Team expectedTeam = new TeamBuilder().withName("Justice League").build();
        expectedModel.addTeam(expectedTeam);
        String expectedMessage = String.format(
                EditTeamCommand.MESSAGE_EDIT_TEAM_SUCCESS,
                expectedTeam
        );

        assertCommandSuccess(editTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedTeamList_success() throws AlfredException {
        Team teamToEdit = new TeamBuilder().build();
        model.addTeam(teamToEdit);

        TeamBuilder teamInList = new TeamBuilder(teamToEdit);
        Team editedTeam = teamInList.withName(VALID_NAME_BRUCE).withProjectName(VALID_PROJECT_NAME_BRUCE).build();

        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withName(VALID_NAME_BRUCE)
                .withProjectName(VALID_PROJECT_NAME_BRUCE).build();
        EditTeamCommand editTeamCommand = new EditTeamCommand(
                teamToEdit.getId(),
                descriptor
        );

        String expectedMessage = String.format(
                EditTeamCommand.MESSAGE_EDIT_TEAM_SUCCESS,
                editedTeam
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(editedTeam);

        assertCommandSuccess(editTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedTeamList_failure() throws AlfredException {
        Team editedTeam = new TeamBuilder().build();
        model.addTeam(editedTeam);
        EditTeamCommand editTeamCommand = new EditTeamCommand(
                editedTeam.getId(),
                new EditTeamDescriptor()
        );
        String expectedMessage = String.format(
                EditTeamCommand.MESSAGE_NO_FIELD_TO_CHANGE,
                editedTeam.getId()
        );

        assertCommandFailure(editTeamCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateTeam_failure() throws AlfredException {
        Team firstPerson = TypicalTeams.A;
        Team secondPerson = TypicalTeams.B;
        model.addTeam(firstPerson);
        model.addTeam(secondPerson);
        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder(firstPerson).build();
        EditTeamCommand editTeamCommand = new EditTeamCommand(secondPerson.getId(), descriptor);

        assertCommandFailure(editTeamCommand, model, TeamList.SIMILAR_TEAM_MSG);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws AlfredException {
        Team validTeam = new TeamBuilder().build();
        model.addTeam(validTeam);
        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withName(VALID_NAME_BRUCE).build();
        EditTeamCommand editTeamCommand = new EditTeamCommand(
                ID_SECOND_TEAM,
                descriptor
        );

        assertCommandFailure(
                editTeamCommand,
                model,
                EditTeamCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX
        );
    }

}
