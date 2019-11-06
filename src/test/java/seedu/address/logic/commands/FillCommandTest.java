package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalIncidentManager;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FillCommandTest {

    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());

}
