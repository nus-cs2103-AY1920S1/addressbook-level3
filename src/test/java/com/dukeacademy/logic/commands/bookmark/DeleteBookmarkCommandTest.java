package com.dukeacademy.logic.commands.bookmark;

import static com.dukeacademy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.testutil.MockQuestionsLogic;

class DeleteBookmarkCommandTest {

    @Test
    void testExecute() throws CommandException {
        MockQuestionsLogic questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        DeleteBookmarkCommand deleteBookmarkCommand = new DeleteBookmarkCommand(-1, questionsLogic);
        String expectedMessage = "No question with id  -1 found.";
        assertThrows(CommandException.class, expectedMessage, () -> deleteBookmarkCommand.execute());
    }
}
