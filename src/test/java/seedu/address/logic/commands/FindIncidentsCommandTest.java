package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NO_VEHICLES_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalVehicles.V1;
import static seedu.address.testutil.TypicalVehicles.V2;
import static seedu.address.testutil.TypicalVehicles.V3;
import static seedu.address.testutil.TypicalVehicles.V4;
import static seedu.address.testutil.TypicalVehicles.V5;
import static seedu.address.testutil.TypicalVehicles.getTypicalIncidentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.incident.DescriptionKeywordsPredicate;
import seedu.address.model.incident.IdKeywordsPredicate;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.NameKeywordsPredicate;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.VehicleType;

/**
 * Contains integration tests (interaction with the Model) for {@code FindIncidentsCommand}.
 */
public class FindIncidentsCommandTest {
    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalIncidentManager(), new UserPrefs());

    @Test
    public void equals() {
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

        // same values -> returns true
        FindIncidentsCommand findFirstCommandCopy = new FindIncidentsCommand(firstDescPredicateArr);
        assertTrue(findFirstDescCommand.equals(findFirstCommandCopy));

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
    public void execute_zeroKeywords_noVehiclesFound() {
        String expectedMessage = MESSAGE_NO_VEHICLES_FOUND;
        DistrictKeywordsPredicate predicate = prepareDistrictPredicate(" ");
        FindVehiclesCommand command = new FindVehiclesCommand(predicate);
        expectedModel.updateFilteredVehicleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredVehicleList());
    }

    @Test
    public void execute_singleVTypeKeyword_multipleVehiclesFound() {
        String expectedMessage = String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, 2);
        VTypeKeywordsPredicate vTypePredicate = prepareVTypePredicate("Ambulance");
        Command command = new FindVehiclesCommand(vTypePredicate);
        expectedModel.updateFilteredVehicleList(vTypePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(V1, V2), model.getFilteredVehicleList());

        expectedMessage = String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, 3);
        vTypePredicate = prepareVTypePredicate("Patrol Car");
        command = new FindVehiclesCommand(vTypePredicate);
        expectedModel.updateFilteredVehicleList(vTypePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(V3, V4, V5), model.getFilteredVehicleList());
    }

    @Test
    public void execute_singleVNumKeyword_multipleVehiclesFound() {

        String expectedMessage = String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, 3);
        VNumKeywordsPredicate vNumPredicate = prepareVNumPredicate("2");
        Command command = new FindVehiclesCommand(vNumPredicate);
        expectedModel.updateFilteredVehicleList(vNumPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(V1, V2, V3), model.getFilteredVehicleList());
    }

    @Test
    public void execute_multipleKeywords_multipleVehiclesFound() {
        String expectedMessage = String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, 2);
        DistrictKeywordsPredicate dPredicate = prepareDistrictPredicate("1 2");
        FindVehiclesCommand command = new FindVehiclesCommand(dPredicate);
        expectedModel.updateFilteredVehicleList(dPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(V1, V4), model.getFilteredVehicleList());
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
