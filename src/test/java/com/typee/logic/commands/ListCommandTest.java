package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;
import static com.typee.testutil.TypicalIndexes.INDEX_FIRST_ENGAGEMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private ListCommand listCommand;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        listCommand = new ListCommand();
        model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showEngagementAtIndex(model, INDEX_FIRST_ENGAGEMENT);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand otherListCommand = new ListCommand();
        assertEquals(listCommand, listCommand);
        assertEquals(listCommand, otherListCommand);
    }

}
