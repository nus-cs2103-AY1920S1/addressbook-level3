package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.SampleEntity.VALID_CUSTOMER;

import org.junit.jupiter.api.Test;

import seedu.address.model.CustomerManager;
import seedu.address.model.DriverManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.id.IdManager;
import seedu.address.model.task.TaskManager;
import seedu.address.storage.CentralManager;

public class ClearCommandTest {

    @Test
    public void execute_emptyCentralManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCentralManager_success() {
        //populate a centralManager
        CustomerManager customerManager = new CustomerManager();
        customerManager.addPerson(VALID_CUSTOMER);
        CentralManager populatedCentralManager = new CentralManager(customerManager, new DriverManager(),
                new TaskManager(), new IdManager());

        Model model = new ModelManager(populatedCentralManager, new UserPrefs());
        model.resetCentralManager();

        Model expectedModel = new ModelManager(new CentralManager(), new UserPrefs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
