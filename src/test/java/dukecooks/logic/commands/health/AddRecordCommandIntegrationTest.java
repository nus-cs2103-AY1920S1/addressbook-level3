package dukecooks.logic.commands.health;

import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.health.components.Record;

/**
 * Contains integration tests (interaction with the Model) for {@code AddRecordsCommand}.
 */
public class AddRecordCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
    }

    @Test
    public void execute_duplicateRecords_throwsCommandException() {
        Record recordsInList = model.getHealthRecords().getHealthRecordsList().get(0);
        CommandTestUtil.assertRecordCommandFailure(new AddRecordCommand(recordsInList), model,
                AddRecordCommand.MESSAGE_DUPLICATE_RECORD);
    }

}
