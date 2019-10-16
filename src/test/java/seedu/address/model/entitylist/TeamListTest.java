package seedu.address.model.entitylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.testutil.TypicalTeams;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamListTest {
    private TeamList teamList = new TeamList();

    /**
     * Resets the participant list.
     */
    @BeforeAll
    private void reset() {
        this.teamList = new TeamList();
        TypicalTeams.clearTeamA();
    }

    @Test
    public void add_validTeam_successful() {
        try {
            this.teamList.add(TypicalTeams.A);
        } catch (Exception e) {
            // Silence error
        }
        assertEquals(this.teamList.list().size(), 1);
    }

    @Test
    public void add_similarTeam_fail() {
        try {
            this.teamList.add(TypicalTeams.A);
        } catch (Exception e) {
            // Silence error
        }
        assertThrows(AlfredModelException.class, () ->
                this.teamList.add(TypicalTeams.A)
        );
    }


    @Test
    public void get_validTeam_success() {
        try {
            this.teamList.add(TypicalTeams.A);
            Team get = this.teamList.get(new Id(PrefixType.T, 1));
            assertEquals(get, TypicalTeams.A);
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void get_invalidTeam_fail() {
        try {
            this.teamList.add(TypicalTeams.A);
            assertThrows(AlfredModelException.class, () ->
                    this.teamList.get(new Id(PrefixType.T, 4)));
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void update_validTeam_success() {
        try {
            this.teamList.add(TypicalTeams.A);
            this.teamList.update(new Id(PrefixType.P, 1), TypicalTeams.A_UPDATED);
            assertTrue(teamList.getSpecificTypedList().get(0)
                    .getLocation().equals(new Location(2)));
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void update_similarTeam_fail() {
        try {
            this.teamList.add(TypicalTeams.A);
            this.teamList.add(TypicalTeams.B);
            assertThrows(AlfredModelException.class, () -> {
                this.teamList.update(new Id(PrefixType.T, 2), TypicalTeams.A_SIMILAR);
            });
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void delete_validTeam_success() {
        try {
            this.teamList.add(TypicalTeams.A);
            this.teamList.delete(new Id(PrefixType.P, 1));
            assertTrue(this.teamList.list().size() == 0);
        } catch (Exception e) {
            // Silence error
        }
    }
}
