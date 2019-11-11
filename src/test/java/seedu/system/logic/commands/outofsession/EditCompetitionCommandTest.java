package seedu.system.logic.commands.outofsession;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.testutil.TypicalCompetitions.getTypicalCompetitionData;
import static seedu.system.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import org.junit.jupiter.api.Test;

import seedu.system.commons.core.index.Index;
import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.model.util.SampleDataUtil;
import seedu.system.testutil.CompetitionBuilder;
import seedu.system.testutil.EditCompetitionDescriptorBuilder;

public class EditCompetitionCommandTest {
    private Data<Person> samplePersonData = getTypicalPersonData();
    private Data<Competition> sampleCompetitionData = getTypicalCompetitionData();
    private Data<Participation> sampleParticipationData =
            new Data(SampleDataUtil.getSampleParticipationData(samplePersonData, sampleCompetitionData));

    private Model model =
            new ModelManager(
                    getTypicalPersonData(),
                    getTypicalCompetitionData(),
                    sampleParticipationData,
                    new UserPrefs()
            );
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Competition editedCompetition = new CompetitionBuilder().build();
        EditCompetitionCommand.EditCompetitionDescriptor descriptor =
                new EditCompetitionDescriptorBuilder(editedCompetition).build();
        EditCompetitionCommand editCompCommand = new EditCompetitionCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCompetitionCommand.MESSAGE_EDIT_COMPETITION_SUCCESS,
                editedCompetition);

        Model expectedModel = new ModelManager(new Data(model.getPersons()), new Data(model.getCompetitions()),
                model.getParticipations(), new UserPrefs());
        expectedModel.setCompetition(model.getFilteredCompetitionList().get(0), editedCompetition);

        assertCommandSuccess(editCompCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastComp = Index.fromOneBased(model.getFilteredCompetitionList().size());
        Competition lastComp = model.getFilteredCompetitionList().get(indexLastComp.getZeroBased());

        CompetitionBuilder compInList = new CompetitionBuilder(lastComp);
        Competition editedComp = compInList.withName("NUS 2022").withStartDate("12/01/2019")
                .withEndDate("12/01/2019").build();

        EditCompetitionCommand.EditCompetitionDescriptor descriptor =
                new EditCompetitionDescriptorBuilder().withName("NUS 2022")
                .withStartDate("12/01/2019").withEndDate("12/01/2019").build();
        EditCompetitionCommand editCompCommand = new EditCompetitionCommand(indexLastComp, descriptor);

        String expectedMessage = String.format(EditCompetitionCommand.MESSAGE_EDIT_COMPETITION_SUCCESS, editedComp);

        Model expectedModel = new ModelManager(new Data(model.getPersons()), new Data(model.getCompetitions()),
                model.getParticipations(), new UserPrefs());
        expectedModel.setCompetition(lastComp, editedComp);

        assertCommandSuccess(editCompCommand, model, expectedMessage, expectedModel);
    }



}
