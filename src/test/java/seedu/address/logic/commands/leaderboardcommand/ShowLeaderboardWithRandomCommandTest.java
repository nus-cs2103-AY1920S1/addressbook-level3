package seedu.address.logic.commands.leaderboardcommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.Comparators;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalTeams;

/**
 * Tests whether the {@link ShowLeaderboardWithRandomCommand} works as expected with regards to
 * different scenarios and is capable of handling errors appropriately.
 */
class ShowLeaderboardWithRandomCommandTest {

    private Model model;
    private Model expectedModel;
    private Model emptyModel = new ModelManagerStub();
    private ArrayList<Comparator<Team>> comparators = new ArrayList<>();

    @BeforeEach
    public void setUp() throws AlfredException {
        model = new ModelManagerStub();
        expectedModel = new ModelManagerStub();
        for (Team t : TypicalTeams.getTypicalTeams()) {
            model.addTeam(t);
            expectedModel.addTeam(t);
        }
    }

    @Test
    void execute_nonEmptyTeamList_commandSuccess() {
        // Non-empty team list and no comparators.
        assertCommandSuccess(new ShowLeaderboardWithRandomCommand(comparators), model,
                ShowLeaderboardWithRandomCommand.MESSAGE_SUCCESS, expectedModel);

        // Non-empty team list with comparators.
        comparators.add(Comparators.rankByIdAscending());
        comparators.add(Comparators.rankByParticipantsDescending());
        assertCommandSuccess(new ShowLeaderboardWithRandomCommand(comparators), model,
                ShowLeaderboardWithRandomCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_emptyTeamList_commandFailure() {
        assertCommandFailure(new ShowLeaderboardWithRandomCommand(comparators), emptyModel,
                LeaderboardCommand.MESSAGE_NO_TEAM);
    }
}
