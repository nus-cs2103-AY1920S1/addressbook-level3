package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;

import org.junit.jupiter.api.Test;

import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.saveEngagementList();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        expectedModel.setHistoryManager(new EngagementList());
        expectedModel.saveEngagementList();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
