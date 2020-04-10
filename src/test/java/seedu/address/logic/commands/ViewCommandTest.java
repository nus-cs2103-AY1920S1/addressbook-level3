package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.history.HistoryManager;
import seedu.address.testutil.FeatureBuilder;
import seedu.address.ui.feature.Feature;


public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(), new TrainingManager(),
            new UserPrefs(), new HistoryManager());
    private ModelManager expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
            new TrainingManager(), new UserPrefs(), new HistoryManager());

    @Test
    public void execute_validFeature_success() {
        Feature feature = new FeatureBuilder().build();
        ViewCommand viewCommand = new ViewCommand(feature);
        String expectedMessage = viewCommand.MESSAGE_SUCCESS_CALENDAR;

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFeature_failure() {
        assertThrows(IllegalArgumentException.class, (
                ) -> new FeatureBuilder().withName("test").build());
    }
}
