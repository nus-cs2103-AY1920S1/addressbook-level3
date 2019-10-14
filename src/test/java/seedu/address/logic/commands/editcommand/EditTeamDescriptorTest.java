package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BRUCE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditTeamCommand.EditTeamDescriptor;
import seedu.address.testutil.EditTeamDescriptorBuilder;

//import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_TYPE_BRUCE;

public class EditTeamDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTeamDescriptor descriptorWithSameValues = new EditTeamDescriptor(TEAM_DESC_ALFRED);
        assertTrue(TEAM_DESC_ALFRED.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TEAM_DESC_ALFRED.equals(TEAM_DESC_ALFRED));

        // null -> returns false
        assertFalse(TEAM_DESC_ALFRED.equals(null));

        // different types -> returns false
        assertFalse(TEAM_DESC_ALFRED.equals(5));

        // different values -> returns false
        assertFalse(TEAM_DESC_ALFRED.equals(TEAM_DESC_BRUCE));

        // different name -> returns false
        EditTeamDescriptor editedAlfred = new EditTeamDescriptorBuilder(TEAM_DESC_ALFRED)
                .withName(VALID_NAME_BRUCE).build();
        assertFalse(TEAM_DESC_ALFRED.equals(editedAlfred));

        // different subject -> returns false
        editedAlfred = new EditTeamDescriptorBuilder(TEAM_DESC_ALFRED).withSubject(VALID_SUBJECT_BRUCE).build();
        assertFalse(TEAM_DESC_ALFRED.equals(editedAlfred));

        // different score -> returns false
        editedAlfred = new EditTeamDescriptorBuilder(TEAM_DESC_ALFRED).withScore(VALID_SCORE_BRUCE).build();
        assertFalse(TEAM_DESC_ALFRED.equals(editedAlfred));

        // different project name -> returns false
        editedAlfred = new EditTeamDescriptorBuilder(TEAM_DESC_ALFRED)
                .withProjectName(VALID_PROJECT_NAME_BRUCE).build();
        assertFalse(TEAM_DESC_ALFRED.equals(editedAlfred));

        // TODO: When more project type enums are added
        // different project type -> returns false
        //editedAlfred = new EditTeamDescriptorBuilder(TEAM_DESC_ALFRED)
        //        .withProjectType(VALID_PROJECT_TYPE_BRUCE).build();
        //assertFalse(TEAM_DESC_ALFRED.equals(editedAlfred));

        // different location -> returns false
        editedAlfred = new EditTeamDescriptorBuilder(TEAM_DESC_ALFRED)
                .withLocation(VALID_LOCATION_BRUCE).build();
        assertFalse(TEAM_DESC_ALFRED.equals(editedAlfred));
    }

}
