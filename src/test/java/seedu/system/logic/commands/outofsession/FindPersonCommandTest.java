package seedu.system.logic.commands.outofsession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.system.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.testutil.TypicalCompetitions.getTypicalCompetitionData;
import static seedu.system.testutil.TypicalPersons.CARL;
import static seedu.system.testutil.TypicalPersons.ELLE;
import static seedu.system.testutil.TypicalPersons.FIONA;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.NameContainsKeywordsPredicate;
import seedu.system.model.person.Person;
import seedu.system.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {

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
    private Model expectedModel =
        new ModelManager(model.getPersons(), model.getCompetitions(), model.getParticipations(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPersonCommand findFirstCommand = new FindPersonCommand(firstPredicate);
        FindPersonCommand findSecondCommand = new FindPersonCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
