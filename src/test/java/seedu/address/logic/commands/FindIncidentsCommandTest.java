package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_SELF;
import static seedu.address.testutil.TypicalIncidents.*;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.incident.*;
import seedu.address.model.person.LoginCredentialsPredicate;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.VehicleType;

/**
 * Contains integration tests (interaction with the Model) for {@code FindIncidentsCommand}.
 */
public class FindIncidentsCommandTest {
    //@@author Yoshi275

    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
    private LoginCredentialsPredicate firstPredicate = new LoginCredentialsPredicate(
            new Username(VALID_USERNAME_AMY), new Password(VALID_PASSWORD_AMY));

    @Test
    public void equals()
    {
        DescriptionKeywordsPredicate firstDescPredicate =
                new DescriptionKeywordsPredicate(List.of("fire"));
        DescriptionKeywordsPredicate secondDescPredicate =
                new DescriptionKeywordsPredicate(List.of("arson"));
        NameKeywordsPredicate firstOpNamePredicate =
                new NameKeywordsPredicate(List.of("bill"), true);
        IdKeywordsPredicate firstIncidentIdPredicate =
                new IdKeywordsPredicate("1220190001");

        List<Predicate<Incident>> firstDescPredicateArr = new ArrayList<>();
        firstDescPredicateArr.add(firstDescPredicate);
        List<Predicate<Incident>> secondDescPredicateArr = new ArrayList<>();
        secondDescPredicateArr.add(secondDescPredicate);
        List<Predicate<Incident>> firstOpNamePredicateArr = new ArrayList<>();
        firstOpNamePredicateArr.add(firstOpNamePredicate);
        List<Predicate<Incident>> firstIncidentIdPredicateArr = new ArrayList<>();
        firstIncidentIdPredicateArr.add(firstIncidentIdPredicate);

        FindIncidentsCommand findFirstDescCommand = new FindIncidentsCommand(firstDescPredicateArr);
        FindIncidentsCommand findSecondDescCommand = new FindIncidentsCommand(secondDescPredicateArr);
        FindIncidentsCommand findFirstOpNameCommand = new FindIncidentsCommand(firstOpNamePredicateArr);
        FindIncidentsCommand findFirstIdCommand = new FindIncidentsCommand(firstIncidentIdPredicateArr);

        // same object -> returns true
        assertTrue(findFirstDescCommand.equals(findFirstDescCommand));
        assertTrue(findFirstOpNameCommand.equals(findFirstOpNameCommand));
        assertTrue(findFirstIdCommand.equals(findFirstIdCommand));

        // same values -> returns true
        FindIncidentsCommand findFirstDescCommandCopy = new FindIncidentsCommand(firstDescPredicateArr);
        assertTrue(findFirstDescCommand.equals(findFirstDescCommandCopy));

        FindIncidentsCommand findFirstOpNameCommandCopy = new FindIncidentsCommand(firstOpNamePredicateArr);
        assertTrue(findFirstOpNameCommand.equals(findFirstOpNameCommandCopy));

        FindIncidentsCommand findFirstIdCommandCopy = new FindIncidentsCommand(firstIncidentIdPredicateArr);
        assertTrue(findFirstIdCommand.equals(findFirstIdCommandCopy));

        // different types of entries -> returns false
        assertFalse(findFirstDescCommand.equals(1));

        // different types of predicates -> returns false
        assertFalse(findFirstDescCommand.equals(findFirstOpNameCommand));
        assertFalse(findFirstOpNameCommand.equals(findFirstIdCommand));
        assertFalse(findFirstIdCommand.equals(findFirstDescCommand));

        // different description predicate -> returns false
        assertFalse(findFirstDescCommand.equals(findSecondDescCommand));

        // null -> returns false
        assertFalse(findFirstDescCommand.equals(null));
    }

    @Test
    public void execute_singleIdKeyword_noIncidentFound() {
        String expectedMessage = MESSAGE_NO_INCIDENTS_FOUND;
        IdKeywordsPredicate idKeywordsPredicate = prepareIdPredicate("123456789");
        Command command = new FindIncidentsCommand(Arrays.asList(idKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(idKeywordsPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleIdKeyword_singleIncidentFound() {
        String expectedMessage = MESSAGE_SINGLE_INCIDENT_LISTED;
        IdKeywordsPredicate idKeywordsPredicate = prepareIdPredicate("0120130001");
        Command command = new FindIncidentsCommand(Arrays.asList(idKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(idKeywordsPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(secondIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleNameKeyword_noIncidentFound() {
        String expectedMessage = MESSAGE_NO_INCIDENTS_FOUND;
        NameKeywordsPredicate nameKeywordsPredicate = prepareNamePredicate("george", false);
        Command command = new FindIncidentsCommand(Arrays.asList(nameKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(nameKeywordsPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleNameKeyword_singleIncidentFound() {
        String expectedMessage = MESSAGE_SINGLE_INCIDENT_LISTED;
        NameKeywordsPredicate nameKeywordsPredicate = prepareNamePredicate("kurz", false);
        Command command = new FindIncidentsCommand(Arrays.asList(nameKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(nameKeywordsPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(fifthIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleNameKeyword_multipleIncidentsFound() {
        String expectedMessage = String.format(MESSAGE_INCIDENTS_LISTED_OVERVIEW, 2);
        NameKeywordsPredicate nameKeywordsPredicate = prepareNamePredicate("mEieR", false);
        Command command = new FindIncidentsCommand(Arrays.asList(nameKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(nameKeywordsPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(seventhIncident, thirdIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_multipleNameKeywords_multipleIncidentsFound() {
        String expectedMessage = String.format(MESSAGE_INCIDENTS_LISTED_OVERVIEW, 3);
        NameKeywordsPredicate nameKeywordsPredicate = prepareNamePredicate("KurZ   mEieR", false);
        Command command = new FindIncidentsCommand(Arrays.asList(nameKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(nameKeywordsPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(seventhIncident, fifthIncident, thirdIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleDescriptionKeyword_noIncidentFound() {
        String expectedMessage = MESSAGE_NO_INCIDENTS_FOUND;
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("geriatrician");
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate));
        expectedModel.updateFilteredIncidentList(descriptionPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleDescriptionKeyword_singleIncidentFound() {
        String expectedMessage = MESSAGE_SINGLE_INCIDENT_LISTED;
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("park");
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate));
        expectedModel.updateFilteredIncidentList(descriptionPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(thirdIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_singleDescriptionKeyword_multipleIncidentsFound() {
        String expectedMessage = String.format(MESSAGE_INCIDENTS_LISTED_OVERVIEW, 2);
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("walkway");
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate));
        expectedModel.updateFilteredIncidentList(descriptionPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(seventhIncident, firstIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_multipleDescriptionKeywords_multipleIncidentsFound() {
        String expectedMessage = String.format(MESSAGE_INCIDENTS_LISTED_OVERVIEW, 3);
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("park walkway");
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate));
        expectedModel.updateFilteredIncidentList(descriptionPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(seventhIncident, thirdIncident, firstIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_multipleDescriptionIdKeywords_noIncidentsFound() {
        String expectedMessage = MESSAGE_NO_INCIDENTS_FOUND;
        IdKeywordsPredicate idKeywordsPredicate = prepareIdPredicate("12345678");
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("park walkway");
        Predicate<Incident> combinedPredicate = descriptionPredicate.and(idKeywordsPredicate);
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate, idKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(), model.getFilteredIncidentList());
    }

    @Test
    public void execute_multipleDescriptionIdKeywords_oneIncidentFound() {
        String expectedMessage = MESSAGE_SINGLE_INCIDENT_LISTED;
        IdKeywordsPredicate idKeywordsPredicate = prepareIdPredicate("0920160001");
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("park walkway");
        Predicate<Incident> combinedPredicate = descriptionPredicate.and(idKeywordsPredicate);
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate, idKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(seventhIncident), model.getFilteredIncidentList());
    }

    @Test
    public void execute_multipleDescriptionNameKeywords_multipleIncidentsFound() {
        String expectedMessage = String.format(MESSAGE_INCIDENTS_LISTED_OVERVIEW, 2);
        NameKeywordsPredicate nameKeywordsPredicate = prepareNamePredicate("KurZ   mEieR", false);
        DescriptionKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate("park walkway");
        Predicate<Incident> combinedPredicate = descriptionPredicate.and(nameKeywordsPredicate);
        Command command = new FindIncidentsCommand(Arrays.asList(descriptionPredicate, nameKeywordsPredicate));
        expectedModel.updateFilteredIncidentList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(seventhIncident, thirdIncident), model.getFilteredIncidentList());
    }

    /**
     * Parses {@code userInput} into a {@code DistrictKeywordsPredicate}.
     * @param userInput
     * @return
     */
    private DistrictKeywordsPredicate prepareDistrictPredicate(String userInput) {
        List<String> splittedD = Arrays.asList(userInput.split("\\s+"));
        List<District> districts = new ArrayList<>();
        for (String s: splittedD) {
            districts.add(new District(Integer.valueOf(s)));
        }
        return new DistrictKeywordsPredicate(districts);
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionKeywordsPredicate}.
     * @param userInput
     * @return
     */
    private DescriptionKeywordsPredicate prepareDescriptionPredicate(String userInput) {
        List<String> splittedD = Arrays.asList(userInput.split("\\s+"));
        return new DescriptionKeywordsPredicate(splittedD);
    }

    /**
     * Parses {@code userInput} into a {@code IdKeywordsPredicate}.
     * @param userInput
     * @return
     */
    private IdKeywordsPredicate prepareIdPredicate(String userInput) {
        return new IdKeywordsPredicate(userInput);
    }

    /**
     * Parses {@code userInput} into a {@code NameKeywordsPredicate}.
     * @param userInput
     * @return
     */
    private NameKeywordsPredicate prepareNamePredicate(String userInput, boolean isFullMatch) {
        List<String> splittedN = Arrays.asList(userInput.split("\\s+"));
        return new NameKeywordsPredicate(splittedN, isFullMatch);
    }

    /**
     * Parses {@code userInput} into a {@code VTypeKeywordsPredicate}.
     * @param userInput
     * @return
     */
    private VTypeKeywordsPredicate prepareVTypePredicate(String userInput) {
        VehicleType vType = new VehicleType(userInput.trim());
        return new VTypeKeywordsPredicate(vType);
    }

    /**
     * Parses {@code userInput} into a {@code VTypeKeywordsPredicate}.
     * @param userInput
     * @return
     */
    private VNumKeywordsPredicate prepareVNumPredicate(String userInput) {
        return new VNumKeywordsPredicate(userInput.trim());
    }
}
