package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.model.util.EngagementComparator;

public class SortCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
    }

    @Test
    public void execute_listIsSortedAlphabetical_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("ALPHABETICAL"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("ALPHABETICAL")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedAlphabeticalDescend_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("ALPHABETICAL_REVERSE"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("ALPHABETICAL_REVERSE")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedStartTime_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("START_TIME"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("START_TIME")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedStartTimeDescend_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("START_TIME_REVERSE"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("START_TIME_REVERSE")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedEndTime_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("END_TIME"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("END_TIME")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedEndTimeDescend_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("END_TIME_REVERSE"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("END_TIME_REVERSE")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedPriority_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("PRIORITY"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("PRIORITY")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedPriorityDescend_showsSortedList() {
        expectedModel.setComparator(EngagementComparator.getComparator("PRIORITY_REVERSE"));
        expectedModel.updateSortedEngagementList();
        assertCommandSuccess(new SortCommand(EngagementComparator.getComparator("PRIORITY_REVERSE")),
                model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
