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
import static seedu.address.testutil.TypicalVehicles.getTypicalIncidentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.VehicleType;

/**
 * Contains integration tests (interaction with the Model) for {@code FindVehiclesCommand}.
 */
public class FindVehiclesCommandTest {
    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalIncidentManager(), new UserPrefs());

    @Test
    public void equals() {
        DistrictKeywordsPredicate firstPredicate =
                new DistrictKeywordsPredicate(List.of(new District(1)));
        DistrictKeywordsPredicate secondPredicate =
                new DistrictKeywordsPredicate(List.of(new District(2)));

        FindVehiclesCommand findFirstCommand = new FindVehiclesCommand(firstPredicate);
        FindVehiclesCommand findSecondCommand = new FindVehiclesCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindVehiclesCommand findFirstCommandCopy = new FindVehiclesCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different district -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
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
    public void execute_singleKeyword_multipleVehiclesFound() {
        String expectedMessage = String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, 2);
        VTypeKeywordsPredicate vTypePredicate = prepareVTypePredicate("Ambulance");
        Command command = new FindVehiclesCommand(vTypePredicate);
        expectedModel.updateFilteredVehicleList(vTypePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(V1, V2), model.getFilteredVehicleList());

        expectedMessage = String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, 3);
        VNumKeywordsPredicate vNumPredicate = prepareVNumPredicate("2");
        command = new FindVehiclesCommand(vNumPredicate);
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
