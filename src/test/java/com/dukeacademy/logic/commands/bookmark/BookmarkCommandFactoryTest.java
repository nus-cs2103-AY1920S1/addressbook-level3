package com.dukeacademy.logic.commands.bookmark;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.testutil.MockQuestionsLogic;

class BookmarkCommandFactoryTest {

    @Test
    void testGetCommandWord() {
        MockQuestionsLogic questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        BookmarkCommandFactory factory = new BookmarkCommandFactory(questionsLogic);
        assertEquals("bookmark", factory.getCommandWord());
    }

    @Test
    void testGetCommand() throws InvalidCommandArgumentsException {
        MockQuestionsLogic questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        BookmarkCommandFactory factory = new BookmarkCommandFactory(questionsLogic);
        assertTrue(factory.getCommand("1") instanceof BookmarkCommand);

        String expectedMessage = "Invalid input. Please call the bookmark command in this format: <bookmark [id]> , "
                + "where id is the positive integer beside the question title.";
        assertThrows(InvalidCommandArgumentsException.class,
                expectedMessage, () -> factory.getCommand("testerString"));
    }
}
