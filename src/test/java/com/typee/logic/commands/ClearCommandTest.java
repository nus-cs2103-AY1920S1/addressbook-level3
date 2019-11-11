package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;

public class ClearCommandTest {

    private ClearCommand clearCommand;

    @BeforeEach
    public void setUp() {
        clearCommand = new ClearCommand();
    }

    @Test
    public void execute_emptyEngagementList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.saveEngagementList();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEngagementList_success() {
        Model model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        expectedModel.setHistoryManager(new EngagementList());
        expectedModel.saveEngagementList();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ClearCommand otherClearCommand = new ClearCommand();
        assertEquals(clearCommand, clearCommand);
        assertEquals(clearCommand, otherClearCommand);
    }

}
