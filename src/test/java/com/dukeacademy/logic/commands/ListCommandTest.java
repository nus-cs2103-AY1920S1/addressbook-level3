package com.dukeacademy.logic.commands;

import com.dukeacademy.model.Model;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    //    @BeforeEach
    //    public void setUp() {
    //        model = new ModelManager(TypicalQuestions.getTypicalQuestionBank(), new UserPrefs());
    //        expectedModel = new ModelManager(model.getStandardQuestionBank(), new UserPrefs());
    //    }
    //
    //    @Test
    //    public void execute_listIsNotFiltered_showsSameList() {
    //        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_listIsFiltered_showsEverything() {
    //        showQuestionAtIndex(model, TypicalIndexes.INDEX_FIRST_QUESTION);
    //        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
}
