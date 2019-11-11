package seedu.system.logic.commands.outofsession;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.testutil.TypicalCompetitions.getTypicalCompetitionData;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import org.junit.jupiter.api.Test;

import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model =
            new ModelManager(
                getTypicalPersonData(),
                getTypicalCompetitionData(),
                new Data(),
                new UserPrefs()
            );
        Model expectedModel = new ModelManager(
            getTypicalPersonData(),
            getTypicalCompetitionData(),
            new Data(),
            new UserPrefs()
        );
        expectedModel.setPersons(new Data());
        expectedModel.setCompetitions(new Data());
        expectedModel.setParticipations(new Data());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
