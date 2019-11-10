package com.dukeacademy.logic.commands.bookmark;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.testutil.MockQuestionsLogic;

class DeleteBookmarkCommandFactoryTest {

    @Test
    void testGetCommandWord() {
        MockQuestionsLogic questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        DeleteBookmarkCommandFactory factory = new DeleteBookmarkCommandFactory(questionsLogic);
        assertEquals("deletebookmark", factory.getCommandWord());
    }

    @Test
    void testGetCommand() throws InvalidCommandArgumentsException {
        MockQuestionsLogic questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        DeleteBookmarkCommandFactory factory = new DeleteBookmarkCommandFactory(questionsLogic);
        assertTrue(factory.getCommand("1") instanceof DeleteBookmarkCommand);

        String expectedMessage = "Invalid input. Please call the deletebookmark command in this format: "
                + "<deletebookmark [id]> , where id is the positive integer beside the question title.";
        assertThrows(InvalidCommandArgumentsException.class, expectedMessage, () ->
                factory.getCommand("testerString"));
    }
}
