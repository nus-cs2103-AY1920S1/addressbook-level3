package com.dukeacademy.logic.commands;

import static com.dukeacademy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.dukeacademy.logic.commands.CommandTestUtil.showQuestionAtIndex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dukeacademy.model.Model;
import com.dukeacademy.model.ModelManager;
import com.dukeacademy.model.UserPrefs;
import com.dukeacademy.testutil.TypicalIndexes;
import com.dukeacademy.testutil.TypicalQuestions;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalQuestions.getTypicalQuestionBank(), new UserPrefs());
        expectedModel = new ModelManager(model.getQuestionBank(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showQuestionAtIndex(model, TypicalIndexes.INDEX_FIRST_QUESTION);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
