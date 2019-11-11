package seedu.system.logic.commands.outofsession;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.system.testutil.TypicalCompetitions.getTypicalCompetitionData;
import static seedu.system.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPersonCommand.
 */
public class ListPersonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        Data<Person> samplePersonData = getTypicalPersonData();
        Data<Competition> sampleCompetitionData = getTypicalCompetitionData();
        Data<Participation> sampleParticipationData =
            new Data(SampleDataUtil.getSampleParticipationData(samplePersonData, sampleCompetitionData));

        model =
            new ModelManager(
                getTypicalPersonData(),
                getTypicalCompetitionData(),
                sampleParticipationData,
                new UserPrefs()
            );
        expectedModel =
            new ModelManager(model.getPersons(), model.getCompetitions(), model.getParticipations(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPersonCommand(), model, ListPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListPersonCommand(), model, ListPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
